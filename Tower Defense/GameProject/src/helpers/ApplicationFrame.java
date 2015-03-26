package helpers;


import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.JPanel;

import data.GameController;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * 
 * @author Shabbir
 * This class is the container class for the project
 */
public class ApplicationFrame extends JFrame {

	// constants
	public static final int PIXELWIDTH=1280;
	public static final int PIXELHEIGHT=800;
	public static final String APP_NAME = "Gametime";
	
	public static final int TIMEOUT = 30;                          		
	protected GameController controller = new GameController(); 
		
	public  ApplicationFrame(){
		init();
	}
	
	/**
	 * Initialize the window
	 */
	private void init(){
		
		//set the Frame properties
		add(controller.getField());
		setSize(PIXELWIDTH,PIXELHEIGHT);	
		setTitle(APP_NAME);
		pack();         												
		
		//set the x button as the default close operation
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);					
		setLocationRelativeTo(null);
		setVisible(true);
		
	}



}
