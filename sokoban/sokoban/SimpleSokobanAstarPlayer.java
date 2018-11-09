/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sokoban;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Set;


/**
 *
 * @author steven
 */
public class SimpleSokobanAstarPlayer {

    GameState state;
    GameDisplay display;
    PriorityQueue<AstarNode> frontier;
    Set<State> closed;
    static int totalNodes = 0;
    static int totalNodesAdded = 0;
    boolean displaySolution;
    
    public SimpleSokobanAstarPlayer(GameState state) {
        this(state,true);
    }
    
    public SimpleSokobanAstarPlayer(GameState state, boolean displaySolution) {
        this.state = state;
        if(displaySolution)
            display = new GameDisplay(state);
        frontier = new PriorityQueue();
        closed = new HashSet();
        frontier.add(new SimpleSokobanNode(state, null, null));
        this.displaySolution=displaySolution;
    }

    public void showSolution() {
        if(displaySolution){
        long t1 = System.currentTimeMillis();
        List<State> path = findPathToGoal();
        long t2 = System.currentTimeMillis();
        System.out.println("Time: " + (t2-t1));
        System.out.println("Nodes expanded: " + totalNodes);
        System.out.println("Nodes added: " + totalNodesAdded);
         System.out.println("Length of solution: " + (path.size()-1));
        for (State st : path) {            
            display.updateState((GameState) st);
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        }
    }

    public List<State> findPathToGoal() {
        while (!frontier.isEmpty()) {
            Node n = frontier.poll();             
            if (!closed.contains(n.getState())) {
                totalNodes++;
                closed.add(n.getState());
                if (n.getState().isSolved()) {
                    return n.getPath();
                }
                else {
                    List<Action> actions = n.getState().getLegalActions();
                    for (Action action : actions) {
                        State child = n.getState().doAction(action);
                        if (!closed.contains(child)) {
                            totalNodesAdded++;
                            frontier.add(new SimpleSokobanNode((GameState)child, n, action));                            
                        }
                    }
                }
            }
        }

        if(displaySolution)
            System.out.println("No solution found.");
        return null;
    }
   
}
