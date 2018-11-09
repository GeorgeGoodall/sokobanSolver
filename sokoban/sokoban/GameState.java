/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sokoban;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import sokoban.Action;
import sokoban.SokobanAction;

/**
 *
 * @author steven
 */
public class GameState implements State {

    private char[][] types;
    private int maxY;
    private int maxX;
    int playerX;
    int playerY;
    List<Position> goalPositions;

    
    /**
     *  Constructors and initialisation methods
     */
    public GameState(String filename) throws Exception {
        BufferedReader in = new BufferedReader(new FileReader(filename));
        String line = in.readLine();
        List<char[]> l = new ArrayList();
        while (line != null) {
            l.add(line.toCharArray());
            line = in.readLine();
        }
        types = l.toArray(new char[0][0]);
        maxX = types.length;
        maxY = types[0].length;
        for (int i = 1; i < types.length; i++) {
            if (types[i].length != maxY) {
                throw new Exception();
            }
        }
        in.close();
        findPlayer();
        findGoalPositions();
    }

    public GameState(char[][] types, int playerX, int playerY, List<Position> goalPositions) {
        this.types = types;
        this.playerX = playerX;
        this.playerY = playerY;
        this.goalPositions = goalPositions;
        maxX = types.length;
        maxY = types[0].length;
    }

    public GameState (GameState st){
        types = new char [st.maxX][st.maxY];
        for(int i=0;i<st.maxX ;i++){
            for(int j=0;j<st.maxY ;j++){
                types[i][j]=st.types[i][j];
            }
        }                
        playerX = st.playerX;
        playerY = st.playerY;
        goalPositions = st.goalPositions;
        maxX = types.length;
        maxY = types[0].length;
    }
    
    private void findPlayer() {
        boolean found = false;
        for (int i = 0; i < maxX && !found; i++) {
            for (int j = 0; j < maxY && !found; j++) {
                if (types[i][j] == 'p' || types[i][j] == 'P') {
                    playerX = i;
                    playerY = j;
                    found = true;
                }
            }
        }
    }

    private void findGoalPositions() {
        goalPositions = new ArrayList();
        for (int i = 0; i < maxX; i++) {
            for (int j = 0; j < maxY; j++) {
                if (types[i][j] == 'g' || types[i][j] == 'P' || types[i][j] == 'B') {
                    goalPositions.add(new Position(i, j));
                }
            }
        }
    }
    
    /**
     *  Getters
     */
    
    public List<Position> getGoalPositions() {
        return goalPositions;
    }
    
    public List<Position> getBlockPositions() {
        List<Position> res = new ArrayList();
        for (int i = 0; i < maxX; i++) {
            for (int j = 0; j < maxY; j++) {
                if (types[i][j] == 'b' || types[i][j] == 'B') {
                    res.add(new Position(i, j));
                }
            }
        }
        return res;
    }
     
      
    public int getMaxY() {
        return maxY;
    }

    public int getMaxX() {
        return maxX;
    }

    public char getType(int x, int y) {
        return types[x][y];
    }
    
     public boolean isWall(int i,int j){
        return types[i][j] == 'w';
    }
          
    public char [][] getTypes(){
        return types;
    }
    
    public boolean isGoalPosition(Position pos) {
        return types[pos.x][pos.y] == 'g' || types[pos.x][pos.y] == 'B' || types[pos.x][pos.y] == 'P';
    }

    public boolean isBlockPosition(Position pos) {
        return types[pos.x][pos.y] == 'b' || types[pos.x][pos.y] == 'B';
    }
    
    public boolean isSolved() {
        for (int i = 0; i < maxX; i++) {
            for (int j = 0; j < maxY; j++) {
                if (types[i][j] == 'b') { //some block is not in a goal position
                    return false;
                }
            }
        }
        return true;
    }
    
    
    /**
     *  This method changes the location of the player, and replaces the current blocks by a single block.
     *  This is needed for generating instances of a relaxed version of the game.
     */
    public void setRelaxedState(int newBlockX, int newBlockY, int newPlayerX, int newPlayerY) throws Exception{
       // Remove current player
        if(types[playerX][playerY]=='p')
            types[playerX][playerY]='.';
        else if(types[playerX][playerY]=='P')
            types[playerX][playerY]='g';
        else
            throw new Exception();
        
        //Remove current blocks
        for (int i = 0; i < maxX; i++) {
            for (int j = 0; j < maxY; j++) {
                if (types[i][j] == 'b'){
                    types[i][j]='.';
                }
                else if (types[i][j] == 'B'){
                    types[i][j]='g';
                }
            }
        }
        
        //add new player
        if(types[newPlayerX][newPlayerY]=='.'){
            types[newPlayerX][newPlayerY]='p';
        }
        else if(types[newPlayerX][newPlayerY]=='g'){
            types[newPlayerX][newPlayerY]='P';
        }
        else
            throw new Exception();
        
        playerX =newPlayerX;
        playerY =newPlayerY;
        //add new block        
             if(types[newBlockX][newBlockY]=='.'){
                 types[newBlockX][newBlockY]='b';
             }
             else if(types[newBlockX][newBlockY]=='g'){
                 types[newBlockX][newBlockY]='B';
             }
             else
                 throw new Exception();        
    }
    
    /**
     *  Methods for executing actions, and for determining which actions are legal
     */
    
    public GameState moveLeft() {
        return move(playerX, playerY, playerX, playerY - 1, playerX, playerY - 2);
    }

    public GameState moveRight() {
        return move(playerX, playerY, playerX, playerY + 1, playerX, playerY + 2);
    }

    public GameState moveUp() {
        return move(playerX, playerY, playerX - 1, playerY, playerX - 2, playerY);
    }

    public GameState moveDown() {
        return move(playerX, playerY, playerX + 1, playerY, playerX + 2, playerY);
    }

    public GameState move(int x, int y, int x1, int y1, int x2, int y2) {
        if (x1 < 0 || y1 < 0 || x1 >= maxX || y1 >= maxY || types[x1][y1] == 'w') {
            return null;
        }
        else {
            char[][] newState = new char[maxX][maxY];
            for (int i = 0; i < maxX; i++) {
                for (int j = 0; j < maxY; j++) {
                    newState[i][j] = types[i][j];
                }
            }
            if (types[x][y] == 'p') {
                newState[x][y] = '.';
            }
            else if (types[x][y] == 'P') {
                newState[x][y] = 'g';
            }
            if (types[x1][y1] == '.' || types[x1][y1] == 'g') {

                if (types[x1][y1] == '.') {
                    newState[x1][y1] = 'p';
                }
                else if (types[x1][y1] == 'g') {
                    newState[x1][y1] = 'P';
                }
            }
            else if (types[x1][y1] == 'b' || types[x1][y1] == 'B') {
                if (x2 < 0 || y2 < 0 || x2 >= maxX || y2 >= maxY || types[x2][y2] == 'w'
                        || types[x2][y2] == 'b' || types[x2][y2] == 'B') {
                    return null;
                }
                if (types[x1][y1] == 'B') {
                    newState[x1][y1] = 'P';
                }
                else if (types[x1][y1] == 'b') {
                    newState[x1][y1] = 'p';
                }
                if (types[x2][y2] == 'g') {
                    newState[x2][y2] = 'B';
                }
                else if (types[x2][y2] == '.') {
                    newState[x2][y2] = 'b';
                }
            }
            return new GameState(newState, x1, y1, goalPositions);
        }
    }

    public boolean moveLeftLegal() {
        return moveLeftLegal(playerX, playerY);
    }

    public boolean moveRightLegal() {
        return moveRightLegal(playerX, playerY);
    }

    public boolean moveDownLegal() {
        return moveDownLegal(playerX, playerY);
    }

    public boolean moveUpLegal() {
        return moveUpLegal(playerX, playerY);
    }

    public boolean moveLeftLegal(int x, int y) {
        return moveLegal(x, y, x, y - 1, x, y - 2);
    }

    public boolean moveRightLegal(int x, int y) {
        return moveLegal(x, y, x, y + 1, x, y + 2);
    }

    public boolean moveUpLegal(int x, int y) {
        return moveLegal(x, y, x - 1, y, x - 2, y);
    }

    public boolean moveDownLegal(int x, int y) {
        return moveLegal(x, y, x + 1, y, x + 2, y);
    }

    public boolean moveLegal(int x, int y, int x1, int y1, int x2, int y2) {
        if (x1 < 0 || y1 < 0 || x1 >= maxX || y1 >= maxY || types[x1][y1] == 'w') {
            return false;
        }
        else if ((types[x1][y1] == 'b' || types[x1][y1] == 'B')
                && (x2 < 0 || y2 < 0 || x2 >= maxX || y2 >= maxY || types[x2][y2] == 'w'
                || types[x2][y2] == 'b' || types[x2][y2] == 'B')) {
            return false;
        }
        return true;
    }

 

    public List<Action> getLegalActions() {
        List<Action> res = new ArrayList();
        if (moveLeftLegal()) {
            res.add(SokobanAction.leftAction);
        }
        if (moveRightLegal()) {
            res.add(SokobanAction.rightAction);
        }
        if (moveUpLegal()) {
            res.add(SokobanAction.upAction);
        }
        if (moveDownLegal()) {
            res.add(SokobanAction.downAction);
        }
        return res;
    }

   

    public boolean isLegal(Action action) {
        if (!(action instanceof SokobanAction)) {
            return false;
        }
        SokobanAction sa = (SokobanAction) action;
        if (sa.isLeft()) {
            return moveLeftLegal();
        }
        else if (sa.isRight()) {
            return moveRightLegal();
        }
        else if (sa.isDown()) {
            return moveDownLegal();
        }
        else if (sa.isUp()) {
            return moveUpLegal();
        }
        return false;
    }

    public State doAction(Action action) {
        if (!(action instanceof SokobanAction)) {
            return null;
        }
        SokobanAction sa = (SokobanAction) action;
        if (sa.isLeft()) {
            return moveLeft();
        }
        else if (sa.isRight()) {
            return moveRight();
        }
        else if (sa.isDown()) {
            return moveDown();
        }
        else if (sa.isUp()) {
            return moveUp();
        }
        return null;
    }
    
   
    /**
     *  Some methods implementing basic functionality
     */

    public void printState() {
        for (int i = 0; i < types.length; i++) {
            for (int j = 0; j < types[0].length; j++) {
                System.out.print(types[i][j]);
            }
            System.out.println();
        }
    }
    

    public boolean equals(Object o) {
        if (!(o instanceof GameState)) {
            return false;
        }
        GameState gs = (GameState) o;
        if (gs.maxX != maxX || gs.maxY != maxY || playerX != gs.playerX || playerY != gs.playerY) {
            return false;
        }
        for (int x = 0; x < maxX; x++) {
            for (int y = 0; y < maxY; y++) {
                if (types[x][y] != gs.types[x][y]) {
                    return false;
                }
            }
        }
        return true;
    }

    public int hashCode() {
        List<Position> blockPositions = getBlockPositions();
        int res = playerX + 17 * playerY;
        for (Position pos : blockPositions) {
            res += 29 * pos.x + 47 * pos.y;
        }
        return res;
    }
    
    public String toString(){
         List<Position> blockPositions = getBlockPositions();
         return "{player: (" + playerX + ","+playerY+"); blocks: " +blockPositions+"}"; 
    }
}
