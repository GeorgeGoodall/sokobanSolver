/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sokoban;

import java.util.*;

/**
 *
 * @author steven
 *
 */
public class Node {

    State st;
    Node previousNode;
    Action lastAction;
    int depth;
    int cost;

    public Node(State st, Node previousNode, Action lastAction) {
        this.st = st;
        this.previousNode = previousNode;
        this.lastAction = lastAction;
        if (previousNode == null) {
            depth = 0;
        } else {
            depth = previousNode.depth + 1;
        }
        if (previousNode == null) {
            cost = 0;
        } else {
            cost = previousNode.getCost() + lastAction.getCost();
        }
    }

    public State getState() {
        return st;
    }

    public int getDepth() {
        return depth;
    }

    public List<State> getPath() {
        List<State> res;
        if (previousNode != null) {
            res = previousNode.getPath();
        } else {
            res = new ArrayList();
        }
        res.add(st);
        return res;
    }

    public List<Action> getActions() {
        List<Action> res;
        if (previousNode != null) {
            res = previousNode.getActions();
            res.add(lastAction);
        } else {
            res = new ArrayList();
        }
        return res;
    }

    public int getCost() {
        return cost;
    }

    public boolean alreadyExplored(State st0) {
        if (previousNode != null && previousNode.alreadyExplored(st0)) {
            return true;
        } else if (st.equals(st0)) {
            return true;
        } else {
            return false;
        }
    }
}
