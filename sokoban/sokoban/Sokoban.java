/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sokoban;

import sokoban.PatternHeuristicAstarPlayer;

/**
 *
 * @author steven
 */
public class Sokoban {
   
    public static void main(String[] args) throws Exception{
        GameState state = new GameState("levels/exampleLargeDifference.txt");
//        HumanPlayer player = new HumanPlayer(state);                
        //SimpleSokobanAstarPlayer player = new SimpleSokobanAstarPlayer(state);
        PatternHeuristicAstarPlayer player = new PatternHeuristicAstarPlayer(state);
        player.showSolution();
    }
        
}
