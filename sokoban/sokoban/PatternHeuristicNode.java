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
public class PatternHeuristicNode extends AstarNode{
    
     PatternDatabase pdb;
    
    public PatternHeuristicNode(GameState st, Node previousNode, Action lastAction,PatternDatabase pdb) {        
        super(st,previousNode,lastAction);
        this.pdb=pdb;
    }
        
    int getEstimatedDistanceToGoal() {        
        return pdb.getEstimatedDistanceToGoal(((GameState)st).getBlockPositions());                
    }   
}
