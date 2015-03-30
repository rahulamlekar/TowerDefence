package helpers;


import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.JPanel;

import data.MainGameController;
import entities.TDMap;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class GameActivity extends JFrame {

	// constants
	public static final int PIXELWIDTH=Artist_Swing.PIXELWIDTH;
	public static final int PIXELHEIGHT=Artist_Swing.PIXELHEIGHT;
	public static final String APP_NAME = "Gametime";
	
	public static final int TIMEOUT = 30;                          		
	MainGameController gameController;
		
	public  GameActivity(TDMap tdMap){
		gameController = new MainGameController(tdMap);
		init();
		gameController.setMainFrame(this);
	}
	
	/**
	 * Initialize the window
	 */
	private void init(){
		
		//set the Frame properties
		this.setLayout(new BoxLayout(this.getContentPane(), BoxLayout.Y_AXIS));
		
		add(gameController.getPlayPanel());
		add(gameController.getControlPanel());
		
		setSize(PIXELWIDTH,PIXELHEIGHT);	
		setTitle(APP_NAME);
		//pack();         												
		
		//set the x button as the default close operation
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);					
		setLocationRelativeTo(null);
		setVisible(true);
		
	}



}
