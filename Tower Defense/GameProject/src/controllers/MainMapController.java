package controllers;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;

import views.GameApplicationFrame;
import views.GameControlPanel;
import views.GamePlayPanel;
import views.MapControlPanel;
import views.MapEditorActivity;
import models.Critter;
import models.DrawableEntity;
import models.Subject;
import models.TDMap;
import models.Tower;
import models.Tower_IceBeam;
import models.Tower_Laser;

public class MainMapController extends GamePlayPanel implements ActionListener {

	//declare game specific variables
	protected GamePlayPanel mapPanel;
	protected MapControlPanel controlPanel;
	protected MapEditorActivity activity;
	
	//declare frame specific variables
	private TDMap tdMap;
	private JButton bReturn;
	JFrame mainFrame;
	
	public MainMapController()
	{
		//create Field pointer defined in controller
		mapPanel = this;
		controlPanel = new MapControlPanel();
		bReturn = this.getControlPanel().getReturnButton();
		bReturn.addActionListener(this);
		
		//get the map
		tdMap= new TDMap();
		
		
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
		Draw();
	}
	private void Draw() {
		mapPanel.repaint();
	}
	
}
