package data;

import helpers.GameActivity;
import helpers.GameControlPanelGeneral;
import helpers.GamePlayPanel;
import helpers.MapControlPanel;
import helpers.MapEditorActivity;

import java.awt.Graphics;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;

import entities.Critter;
import entities.DrawableEntity;
import entities.Tower_IceBeam;
import entities.Tower_Laser;
import entities.Subject;
import entities.TDMap;
import entities.Tower;

public class MainMapController extends GamePlayPanel implements ActionListener {

	//declare game specific variables
	protected GamePlayPanel mapPanel;
	protected MapControlPanel controlPanel;
	protected MapEditorActivity activity;
	protected TDMap tdMap;
	
	//declare frame specific variables
	private Timer timer;
	private JButton bReturn;
	private JButton bInitialize;
	JFrame mainFrame;
	
	public MainMapController(TDMap map)
	{
		//create Field pointer defined in controller
		mapPanel = this;
		controlPanel = new MapControlPanel();
		bReturn = this.getControlPanel().getReturnButton();
		bReturn.addActionListener(this);
		bInitialize = this.getControlPanel().getInitializeButton();
		bInitialize.addActionListener(this);
		
		this.tdMap= map;
		timer = new Timer(MapEditorActivity.TIMEOUT,this);
		timer.start();
	}
	public void setMainFrame(JFrame mFrame){
		mainFrame = mFrame;
	}
	
	public MapControlPanel getControlPanel(){
		return controlPanel;
	}
	public GamePlayPanel getPlayPanel(){
		return mapPanel;
	}
	
	
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == bReturn){
			
		}
		else if(e.getSource() == bInitialize)
		{
			this.tdMap= new TDMap(Integer.parseInt((String) this.getControlPanel().getWidthIndexes().getSelectedItem()),
					Integer.parseInt((String) this.getControlPanel().getHeightIndexes().getSelectedItem()),"Generic");
		}
		else
			Draw();

	}
	
	public void Draw() {
		mapPanel.repaint();
	}
	
	@Override
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		this.tdMap.updateAndDraw(g);
        Toolkit.getDefaultToolkit().sync();
	}
	
	public TDMap getTDMap() {
		return this.tdMap;
	}
	
}
