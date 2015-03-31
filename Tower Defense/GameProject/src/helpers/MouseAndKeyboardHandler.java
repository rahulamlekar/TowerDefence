package helpers;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

import javax.swing.*;

import data.MainGameController;
import entities.Critter;
import entities.Tower_IceBeam;
import entities.Tower;
import entities.Point;
public class MouseAndKeyboardHandler implements KeyListener, MouseListener, MouseMotionListener{
	
	private MainGameController gameController;
	private GamePlayPanel panel;
		
	public MouseAndKeyboardHandler(MainGameController gameController){
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
		gameController.towerToPreview(new Point(event.getX(),event.getY()));

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
