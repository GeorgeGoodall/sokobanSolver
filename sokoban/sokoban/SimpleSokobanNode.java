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
public class SimpleSokobanNode extends AstarNode {

    public SimpleSokobanNode(GameState st, Node previousNode, Action lastAction) {
        super(st, previousNode, lastAction);
    }

    int getEstimatedDistanceToGoal() {
        List<Position> blockPositions = ((GameState) st).getBlockPositions();
        int sum = 0;
        for (Position posBlock : blockPositions) {
            int min = Integer.MAX_VALUE;
            for (Position posGoal : ((GameState) st).getGoalPositions()) {
                min = Math.min(min, posBlock.distance(posGoal));
            }
            sum += min;
        }
        return sum;
    }
}
