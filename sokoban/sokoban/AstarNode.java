/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sokoban;

/**
 *
 * @author steven
 */
abstract class AstarNode extends Node implements Comparable<AstarNode>{

    public AstarNode(State st, Node previousNode, Action lastAction) {        
        super(st,previousNode,lastAction);
    }
    
    public int compareTo(AstarNode n){
        int score = cost + getEstimatedDistanceToGoal();
        int scoreN = n.cost + n.getEstimatedDistanceToGoal();        
            return score - scoreN;
    }        
            
    abstract int getEstimatedDistanceToGoal();       

}