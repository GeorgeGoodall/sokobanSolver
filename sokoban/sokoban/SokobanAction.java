/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sokoban;

/**
 *
 * @author steven
 */
public class SokobanAction implements Action{

    private static final int LEFT = 0;
    private static final int UP = 1;
    private static final int RIGHT = 2;
    private static final int DOWN = 3;
       
    public static SokobanAction leftAction = new SokobanAction(LEFT);
    public static SokobanAction rightAction = new SokobanAction(RIGHT);
    public static SokobanAction upAction = new SokobanAction(UP);
    public static SokobanAction downAction = new SokobanAction(DOWN);
    
    private int type;
    
    private SokobanAction(int type){
        this.type=type;
    }
    
    public int getCost() {
        return 1;
    }
    
    public boolean isLeft(){
        return type==LEFT;
    }
    
    public boolean isRight(){
        return type==RIGHT;
    }
    
    public boolean isUp(){
        return type==UP;
    }
    
    public boolean isDown(){
        return type==DOWN;
    }
    
    public String toString(){
        if(type==LEFT){
            return "left";
        }
        else if(type==RIGHT){
            return "right";
        }
        else if(type==UP){
            return "up";
        }
        else if(type==DOWN){
            return "down";
        }
        else
            return "unrecognised action";
    }
}
