package helpers;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

import javax.swing.*;

import views.MapPanel;
import models.Critter;
import models.Point;
import models.Tower;
import models.Tower_IceBeam;
import controllers.GameController;

/**
 *
 * 
 */
public class MouseAndKeyboardHandler extends Helper implements MouseListener, MouseMotionListener{
	
	//we need to know the gameController that we are helping
	private GameController gameController;
		
    /**
     *
     * @param gameController
     */
    public MouseAndKeyboardHandler(GameController gameController){
    	//we set the gameController
		this.gameController = gameController;
	}
    //on mouse click, we alert the game controller
	public void mouseClicked(MouseEvent event){
		//we also want to let the game controller know if it was left or right
		if(SwingUtilities.isRightMouseButton(event)){
			gameController.reactToRightClick(new Point(event.getX(), event.getY()));
		}else{
			gameController.reactToLeftClick(new Point(event.getX(),event.getY()));
		}
	}
	
	public void mousePressed(MouseEvent event){}
	
	public void mouseReleased(MouseEvent event){}
	
	public void mouseEntered(MouseEvent event){
		
	}
		
	public void mouseExited(MouseEvent event){}
	
	//edit
	
	public void mouseMoved(MouseEvent event){
		//we want to let the game controller know if the mouse is moved
		gameController.reactToMouseMove(new Point(event.getX(),event.getY()));

	}
	
	public void mouseDragged(MouseEvent event){}

}
