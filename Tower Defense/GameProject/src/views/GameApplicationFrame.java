package views;


import helpers.Artist_Swing;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.JPanel;

import models.TDMap;
import controllers.GameController;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class GameApplicationFrame extends JFrame {

	// constants
	public static final int PIXELWIDTH=Artist_Swing.PIXELWIDTH;
	public static final int PIXELHEIGHT=Artist_Swing.PIXELHEIGHT;
	public static final String APP_NAME = "Gametime";
	private GameControlPanel controlPanel;
	private MapPanel mapPanel;
	public static final int TIMEOUT = 30;                          		
	GameController gameController;
		
	public  GameApplicationFrame(TDMap tdMap){
		gameController = new GameController(tdMap);
		init();
		gameController.setMainFrame(this);
	}
	
	/**
	 * Initialize the window
	 */
	private void init(){
		
		//set the Frame properties
		this.setLayout(new BoxLayout(this.getContentPane(), BoxLayout.Y_AXIS));
		controlPanel = gameController.getControlPanel();
		mapPanel = gameController.getPlayPanel();
		add(mapPanel);
		add(controlPanel);
		
		setSize(PIXELWIDTH,PIXELHEIGHT);	
		setTitle(APP_NAME);
		//pack();         												
		
		//set the x button as the default close operation
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);					
		setLocationRelativeTo(null);
		setVisible(true);
		
	}



}
