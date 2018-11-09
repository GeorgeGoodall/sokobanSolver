/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sokoban;

import sokoban.GameDisplay;
import sokoban.GameState;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 *
 * @author steven
 */
public class HumanPlayer implements KeyListener{

    GameState state;
    GameDisplay display;
    
    public HumanPlayer(GameState state){
        this.state=state;
        display = new GameDisplay(state);
        display.addKeyListener(this);
    }
    
    public void keyTyped(KeyEvent e) {        
    }

    
    public void keyPressed(KeyEvent e) {        
        GameState newState = null;
        if(e.getKeyCode()==KeyEvent.VK_UP)
            newState = state.moveUp();
        else if(e.getKeyCode()==KeyEvent.VK_DOWN)
            newState = state.moveDown();
        else if(e.getKeyCode()==KeyEvent.VK_RIGHT)
            newState = state.moveRight();
        else if(e.getKeyCode()==KeyEvent.VK_LEFT)
            newState = state.moveLeft();
        if(newState!=null){
            state=newState;
            display.updateState(newState);
        }
    }

    
    public void keyReleased(KeyEvent e) {        
    }
    
}
