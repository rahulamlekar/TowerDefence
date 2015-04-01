package views;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import models.TDMap;

public class MainMenuActivity extends JFrame implements ActionListener{
	public static final int PIXELWIDTH=300;
	public static final int PIXELHEIGHT=300;
	public static final String APP_NAME = "Main Menu";
	public static final int TIMEOUT = 30 ;
	JPanel mainPanel = new JPanel();
	JButton bPlay = new JButton("Play a game");
	JButton bCreateMap = new JButton("Create a map");
	JButton bQuit = new JButton("Quit");
	TDMap map;
	//MainMenuActivity activity;
	
	public MainMenuActivity(){
		init();
		bPlay.addActionListener(this);
		bCreateMap.addActionListener(this);
		bQuit.addActionListener(this);
		map = new TDMap("res/DIRTMAP1.TDMap"); //set default map
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
			if(e.getSource() == bPlay){
				this.dispose();
				new GameApplicationFrame(map);
			}else if(e.getSource() == bCreateMap){
				this.dispose();
				new MapEditorActivity();
			}else if(e.getSource() == bQuit){
				System.exit(0);
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
