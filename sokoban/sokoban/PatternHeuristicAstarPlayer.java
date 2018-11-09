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
public class PatternHeuristicAstarPlayer {

    GameState state;
    GameDisplay display;
    PriorityQueue<AstarNode> frontier;
    Set<State> closed;
    static int totalNodes = 0;
    static int totalNodesAdded = 0;
    PatternDatabase pdb;
    
    public PatternHeuristicAstarPlayer(GameState state) {
        this.state = state;
        display = new GameDisplay(state);
        frontier = new PriorityQueue();
        closed = new HashSet();
        long t1 = System.currentTimeMillis();
        pdb = new PatternDatabase(state);
        long t2 = System.currentTimeMillis();
        System.out.println("Time for creating pattern database: " + (t2-t1));
        frontier.add(new PatternHeuristicNode(state, null, null,pdb));
    }

    public void showSolution() {
        long t1 = System.currentTimeMillis();
        List<State> path = findPathToGoal();
        long t2 = System.currentTimeMillis();
        System.out.println("Time for finding solution: " + (t2-t1));
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
                            frontier.add(new PatternHeuristicNode((GameState)child, n, action,pdb));                            
                        }
                    }
                }
            }
        }

        System.out.println("No solution found.");
        return null;
    }
   
}
