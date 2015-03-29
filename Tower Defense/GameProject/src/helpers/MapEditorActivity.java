package helpers;


import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.JPanel;

import data.MainMapController;
import entities.TDMap;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class MapEditorActivity extends JFrame{

	// constants
	public static final int PIXELWIDTH=1280;
	public static final int PIXELHEIGHT=800;
	public static final String APP_NAME = "Map Editor Time";
	public static final int TIMEOUT = 30;
	
	MainMapController mapController;
	private TDMap tdMap;
	
	public MapEditorActivity(){
		tdMap= new TDMap();
		mapController = new MainMapController(tdMap);
		init();
		mapController.setMainFrame(this);
	}
	
	/**
	 * Initialize the window
	 */
	private void init(){
		
		//set the Frame properties
		this.setLayout(new BoxLayout(this.getContentPane(), BoxLayout.Y_AXIS));
		
		add(mapController.getPlayPanel());
		add(mapController.getControlPanel());
		this.tdMap= mapController.getTDMap();
		setSize(PIXELWIDTH,PIXELHEIGHT);	
		setTitle(APP_NAME);
		//pack();         												
		
		//set the x button as the default close operation
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);					
		setLocationRelativeTo(null);
		setVisible(true);
		
	}
	
	



}
