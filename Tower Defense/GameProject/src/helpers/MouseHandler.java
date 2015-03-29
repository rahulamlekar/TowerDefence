package helpers;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

import javax.swing.*;

import data.MainGameController;
import entities.Critter;
import entities.IceBeamTower;
import entities.Tower;
import entities.Point;
public class MouseHandler implements MouseListener{
	
	private MainGameController gameController;
	private GamePlayPanel panel;
		
	public MouseHandler(MainGameController gameController){
		this.gameController = gameController;
		panel = gameController.getPlayPanel();
		
	}

	
	
	public void mouseClicked(MouseEvent event){
		
		gameController.addTower(new Point(event.getX(),event.getY()));
		//System.out.println(event.getX()+ " " + event.getY());
	}
	
	public void mousePressed(MouseEvent event){}
	
	public void mouseReleased(MouseEvent event){}
	
	public void mouseEntered(MouseEvent event){}
	
	public void mouseExited(MouseEvent event){}
	
	//edit
}
