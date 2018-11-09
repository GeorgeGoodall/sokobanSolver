/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sokoban;

import java.util.List;

/**
 *
 * @author steven
 */
public class PatternDatabase {

	int [][] relitivePlayerPositions = {{-1,0},{0,1},{1,0},{0,-1}};
	GameState st; 
    int[][] huristic;

    public PatternDatabase(GameState st) {
        this.st = st;
        huristic = new int[st.getMaxX()][st.getMaxY()]; 

        int nodesExpanded = 0;
        
        
        // for each block position
        for(int blocki = 0; blocki < st.getMaxX(); blocki++)
        {
        	for(int blockj = 0; blockj < st.getMaxY(); blockj++)
        	{
        		// set the huristic for this position to 10000 
        		int cost = 0;
        		GameState relaxedState = new GameState(st);
        		huristic[blocki][blockj] = 10000;
        		
        		// for each tile adjacent to the current block position
        		for(int playerPosition = 0; playerPosition < 4; playerPosition++)
    	        {
    	        	// get the actual position of this adjacent tile for the player position
    			 	Position player = new Position(blocki + relitivePlayerPositions[playerPosition][0],blockj + relitivePlayerPositions[playerPosition][1]);
    			 
	        		// if the block or player is on a wall or the player is out of bounds continue
	        		if((player.getX() < 0 || player.getX() >= st.getMaxX() || player.getY() < 0 || player.getY() >= st.getMaxY())){
	        			continue;
	        		}
	        		else if((st.getType(player.getX(), player.getY()) == 'w') || (st.getType(blocki, blockj) == 'w')){
	        			continue;
	        		}
	        		
	        		// make a new relaxed state with the block position and the player position
	        		// have the regular simplesokobanAstar player solve this relaxed game
	        		// save the lowest score out of the 4 relaxed states for this block position, if no solution is found the score remains at 10000
	        		try {
	        			relaxedState.setRelaxedState(blocki, blockj, player.getX(), player.getY());
	        			SimpleSokobanAstarPlayer relaxedplayer = new SimpleSokobanAstarPlayer(relaxedState,false);
	        			List<State> path = relaxedplayer.findPathToGoal();
	        			cost = path.size();		
	        			if(cost < huristic[blocki][blockj]){
	        				huristic[blocki][blockj] = cost;	
	        			}
					} catch (Exception e) {
						// if here its because there is no path to the goal
						//System.out.println("couldn't find solution for relaxed state, player = ("+player.getX()+","+player.getY()+") block = ("+blocki+","+blockj+") | Error: " + e.toString());
						//e.printStackTrace();
					}	
    	        }
        	}
        }


        // GameDisplay class extended to add class that draws huristics to new window, with lighter colours indicatiting a lower huristic value.
        // HuristicDisplay display = new HuristicDisplay(st,huristic);    
    }

    public int getEstimatedDistanceToGoal(List<Position> positions) {
        int sum = 0;

        // sum all huristic values for the block positions
        for (Position posBlock : positions) {
	       	try {
	          	sum += huristic[posBlock.getX()][posBlock.getY()];	 
	       	}
	       	catch(java.lang.NullPointerException e) {
	       		System.out.println("Error: " + e);
	       	} 	 
        }
        return sum;
   }
}

