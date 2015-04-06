package views;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.filechooser.FileNameExtensionFilter;

import models.TDMap;

public class MenuApplicationFrame extends JFrame implements ActionListener{
	public static final int PIXELWIDTH=300;
	public static final int PIXELHEIGHT=300;
	public static final String APP_NAME = "Main Menu";
	public static final int TIMEOUT = 30 ;
	final JFileChooser fc = new JFileChooser();
	JPanel mainPanel = new JPanel();
	JButton bPlay = new JButton("Play a game");
	JButton bCreateMap = new JButton("Create a map");
	JButton bQuit = new JButton("Quit");
	JButton bLoadMap = new JButton("Load a map");
	TDMap mapToLoad;
	//MainMenuActivity activity;
	
	public MenuApplicationFrame(){
		init();
		bPlay.addActionListener(this);
		bCreateMap.addActionListener(this);
		bQuit.addActionListener(this);
		bLoadMap.addActionListener(this);
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
				new MapEditorApplicationFrame();
			}else if(e.getSource() == bQuit){
				System.exit(0);
			}else if(e.getSource() == bLoadMap){
				int returnVal = fc.showOpenDialog(this);
		        if (returnVal == JFileChooser.APPROVE_OPTION) {
		            String filePath = fc.getSelectedFile().getPath();
		            mapToLoad = new TDMap(filePath);
		        } else {
		        	
		        }
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
		
	}

}
