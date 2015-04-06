package views;


import helpers.Artist_Swing;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.JPanel;

import controllers.MapEditorController;
import models.TDMap;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 *  This initializes the various parts of the Map Editor, in terms of the frame.
 * @author Yash Gupta
 */
public class MapEditorApplicationFrame extends JFrame{

	// constants

    /**
     *  The width of the window of the Map Editor.
     */
    	public static final int PIXELWIDTH=1280;

    /**
     *  The height of the window of the Map Editor.
     */
    public static final int PIXELHEIGHT=800;

    /**
     *
     */
    public static final String APP_NAME = "Map Editor Time";

    /**
     *
     */
    public static final int TIMEOUT = 30;
	private MapControlPanel controlPanel;
	private MapPanel mapPanel;
	
	MapEditorController mapController;
	private TDMap tdMap;
	
    /**
     *
     */
    public MapEditorApplicationFrame(TDMap tdMap){
		this.tdMap= tdMap;
		mapController = new MapEditorController(tdMap);
		init();
		mapController.setMainFrame(this);
	}
	
	/**
	 * Initialize the window
	 */
	private void init(){
		
		//set the Frame properties
		this.setLayout(new BoxLayout(this.getContentPane(), BoxLayout.Y_AXIS));
		mapPanel = mapController.getPlayPanel();
		controlPanel = mapController.getControlPanel();
		//mapPanel.setSize(Artist_Swing.PIXELWIDTH, Artist_Swing.PANELHEIGHT);
		add(mapPanel);
		add(controlPanel);
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