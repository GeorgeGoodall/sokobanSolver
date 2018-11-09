/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sokoban;

/**
 *
 * @author steven
 */
public class Position {

        int x;
        int y;

        public Position(int x, int y) {
            this.x = x;
            this.y = y;
        }

        public int distance(Position pos) {
            return Math.abs(x - pos.x) + Math.abs(y - pos.y);
        }
        
        public int getX(){
            return x;
        }
        
        public int getY(){
            return y;
        }
 
        public String toString(){
            return "(" + x +"," +y+")";
        }
}

