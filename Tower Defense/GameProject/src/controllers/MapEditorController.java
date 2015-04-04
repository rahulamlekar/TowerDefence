package controllers;

import views.*;

import java.awt.Container;
import java.awt.Graphics;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;

import views.MapPanel;
import models.DrawableEntity;
import models.IObserverTDMap;
import models.MapTile;
import models.Point;
import models.TDMap;

public class MapEditorController extends MapPanel implements ActionListener, MouseListener, IObserverTDMap {

	//declare game specific variables
	protected MapPanel mapPanel;
	protected MapControlPanel controlPanel;
	protected MapEditorApplicationFrame activity;
	protected TDMap tdMap;
	
	//declare frame specific variables
	private Timer timer;
	private JButton bReturn;
	private JButton bInitialize;
	JFrame mainFrame;
	private int tileWidth_Pixel, tileHeight_Pixel;
	
	
	public MapEditorController(TDMap map)
	{
		//create Field pointer defined in controller
		mapPanel = this;
		controlPanel = new MapControlPanel(map);
		bReturn = this.getControlPanel().getReturnButton();
		bReturn.addActionListener(this);
		bInitialize = this.getControlPanel().getInitializeButton();
		bInitialize.addActionListener(this);
		this.tdMap= map;
		map.refresh();
		map.addObserver(this);
		//drawableEntities.add(tdMap);
		timer = new Timer(MapEditorApplicationFrame.TIMEOUT,this);
		timer.start();
		mapPanel.addMouseListener(this);
	}
	public void setMainFrame(JFrame mFrame){
		mainFrame = mFrame;
	}
	
	public MapControlPanel getControlPanel(){
		return controlPanel;
	}
	public MapPanel getPlayPanel(){
		return mapPanel;
	}
	
	
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == bReturn){
			mainFrame.dispose();
			new MenuApplicationFrame();
		}
		else if(e.getSource() == bInitialize)
		{
			tdMap.reinitialize(Integer.parseInt((String) this.getControlPanel().getWidthIndexes().getSelectedItem()),
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
		tdMap.updateAndDraw(g);
        Toolkit.getDefaultToolkit().sync();

	}
	
	public TDMap getTDMap() {
		return this.tdMap;
	}
	public void TDMapUpdated() {
		Draw();
	}

	public void TDMapReinitialized() {
		tileWidth_Pixel= tdMap.getTileWidth_pixel();
		tileHeight_Pixel= tdMap.getTileHeight_pixel();
		TDMapUpdated();
		updateLabelsAndComboBoxes();
	}
	public void updateLabelsAndComboBoxes(){
		//this.controlPanel.cbHeight.setSelectedItem(10);
	}
	@Override
	public void mouseClicked(MouseEvent e) {		
		double xRatio = ((double)e.getX())/((double)tdMap.getPixelWidth());
		double yRatio = ((double)e.getY())/((double)tdMap.getPixelHeight());
		int xGridPos = (int) Math.floor(xRatio * tdMap.getGridWidth());
		int yGridPos = (int) Math.floor(yRatio * tdMap.getGridHeight());
		tdMap.toggleGrid(xGridPos, yGridPos);
		
	}
	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	
}