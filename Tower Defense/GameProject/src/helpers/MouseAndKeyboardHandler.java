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
public class MouseAndKeyboardHandler extends Helper implements KeyListener, MouseListener, MouseMotionListener{
	
	private GameController gameController;
	private MapPanel panel;
		
	public MouseAndKeyboardHandler(GameController gameController){
		this.gameController = gameController;
		panel = gameController.getPlayPanel();
		
	}

	public void mouseClicked(MouseEvent event){
		if(SwingUtilities.isRightMouseButton(event)){
			gameController.reactToRightClick(new Point(event.getX(), event.getY()));
		}else{
			gameController.reactToLeftClick(new Point(event.getX(),event.getY()));
		//System.out.println(event.getX()+ " " + event.getY());
		}
	}
	
	public void mousePressed(MouseEvent event){}
	
	public void mouseReleased(MouseEvent event){}
	
	public void mouseEntered(MouseEvent event){
		
	}
		
	public void mouseExited(MouseEvent event){}
	
	//edit
	
	public void mouseMoved(MouseEvent event){
		gameController.reactToMouseMove(new Point(event.getX(),event.getY()));

	}
	
	public void mouseDragged(MouseEvent event){}

	@Override
	public void keyTyped(KeyEvent e) {
		gameController.reactToKeypress(e);

	}

	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}
}
