package views;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.filechooser.FileNameExtensionFilter;

import models.TDMap;

/**
 *  This class refers to the Main Menu of the Tower Defense game.
 *  This JFrame will be the place where the player gets to decide if he wants to
 *  load an existing map to play on, choose one of the pre-existing maps, or
 *  to create a new game map according to his preference.
 * 
 */
public class MenuApplicationFrame extends JFrame implements ActionListener{

    /**
     *  The width of the window of the main menu.
     */
    public static final int PIXELWIDTH=500;

    /**
     *  The height of the window of the main menu.
     */
    public static final int PIXELHEIGHT=200;

    /**
     *
     */
    public static final String APP_NAME = "Main Menu";

    /**
     *
     */
    public static final int TIMEOUT = 30 ;

	final JFileChooser fc = new JFileChooser();
	JPanel mainPanel = new JPanel();
	JButton bPlay = new JButton("Play a game");
	JButton bCreateMap = new JButton("Create a map");
	JButton bQuit = new JButton("Quit");
	JButton bLoadMap = new JButton("Load a map");
	JButton bDefault = new JButton("Default");
	TDMap mapToLoad;
	JLabel lblMapToLoad = new JLabel("MAP: Default");

	
    /**
     *  Default Constructor to initialize the Main Menu to the stereotypical menu.
     */
    public MenuApplicationFrame(){
		init();
		bPlay.addActionListener(this);
		bCreateMap.addActionListener(this);
		bQuit.addActionListener(this);
		bLoadMap.addActionListener(this);
		bDefault.addActionListener(this);
		mapToLoad = new TDMap("res/DIRTMAP1.TDMap"); //set default map
		FileNameExtensionFilter filter = new FileNameExtensionFilter("Map Files", "TDMap");
		fc.setFileFilter(filter);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
			if(e.getSource() == bPlay){
				this.dispose();
				new GameApplicationFrame(mapToLoad);
			}else if(e.getSource() == bCreateMap){
				this.dispose();
				new MapEditorApplicationFrame(mapToLoad);
			}else if(e.getSource() == bQuit){
				System.exit(0);
			}else if(e.getSource() == bLoadMap){
				int returnVal = fc.showOpenDialog(this);
		        if (returnVal == JFileChooser.APPROVE_OPTION) {
		            String filePath = fc.getSelectedFile().getPath();
		            mapToLoad = new TDMap(filePath);
		            this.setMapName(filePath);
		            bDefault.setEnabled(true);
		        } else {
		        	
		        }
			}else if(e.getSource() == bDefault){
				this.setMapName("Default");
				mapToLoad = new TDMap("res/Try1.TDMap");
				bDefault.setEnabled(false);
			}
	}
		
	private void init(){
		//set panel properties
		//mainPanel.setBackground(Color.BLACK);
		mainPanel.setPreferredSize(new Dimension(GameApplicationFrame.PIXELWIDTH, GameApplicationFrame.PIXELHEIGHT));
		mainPanel.setDoubleBuffered(true);
		mainPanel.setVisible(true);
		mainPanel.setFocusable(true);
        mainPanel.requestFocus();
        mainPanel.add(bPlay);
        mainPanel.add(bCreateMap);
        mainPanel.add(bLoadMap);
        mainPanel.add(bQuit);
        mainPanel.add(lblMapToLoad);
        mainPanel.add(bDefault);
		//set the Frame properties
		this.setLayout(new BoxLayout(this.getContentPane(), BoxLayout.Y_AXIS));
		this.add(mainPanel);
		this.setResizable(false);
		setSize(PIXELWIDTH,PIXELHEIGHT);	
		setTitle(APP_NAME);
		//pack();         												
		
		//set the x button as the default close operation
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);					
		setLocationRelativeTo(null);
		setVisible(true);
		bDefault.setEnabled(false);
		
	}

    /**
     *
     * @param name
     */
    public void setMapName(String name){
		this.lblMapToLoad.setText("Map: " + name);
		this.repaint();
	}

}
