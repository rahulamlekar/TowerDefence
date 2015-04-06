package controllers;

import views.*;

import java.awt.Graphics;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.Timer;

import views.MapPanel;
import models.IObserverTDMap;
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
	private JButton bSetStartAndEnd;
	private JButton bSave;
	JFrame mainFrame;
	private int tileWidth_Pixel, tileHeight_Pixel;
	
	JFileChooser fc = new JFileChooser();
	
	public MapEditorController(TDMap map)
	{
		//create Field pointer defined in controller
		mapPanel = this;
		controlPanel = new MapControlPanel(map);
		bReturn = this.getControlPanel().getReturnButton();
		bReturn.addActionListener(this);
		bInitialize = this.getControlPanel().getInitializeButton();
		bInitialize.addActionListener(this);
		bSetStartAndEnd = this.getControlPanel().getSetStartAndEndButton();
		bSetStartAndEnd.addActionListener(this);
		bSave = this.getControlPanel().getSaveButton();
		bSave.addActionListener(this);
		this.tdMap= map;
		map.refresh();
		map.addObserver(this);
		//drawableEntities.add(tdMap);
		timer = new Timer(MapEditorApplicationFrame.TIMEOUT,this);
		timer.start();
		mapPanel.addMouseListener(this);
		bInitialize.doClick();
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
			int widthOfMap= Integer.parseInt((String) this.getControlPanel().getWidthIndexes().getSelectedItem());
			int heightOfMap= Integer.parseInt((String) this.getControlPanel().getHeightIndexes().getSelectedItem());
			tdMap.reinitialize(widthOfMap, heightOfMap,"Generic");
			updateStartAndEnd(widthOfMap, heightOfMap);
			controlPanel.repaint();
		}
		else if(e.getSource() == bSetStartAndEnd)
		{
			tdMap.setStart(Integer.parseInt((String) this.getControlPanel().getStartWidths().getSelectedItem()),
					Integer.parseInt((String) this.getControlPanel().getStartHeights().getSelectedItem()));
			tdMap.setEnd(Integer.parseInt((String) this.getControlPanel().getEndWidths().getSelectedItem()),
					Integer.parseInt((String) this.getControlPanel().getEndHeights().getSelectedItem()));
		}
		else if(e.getSource() == bSave)
		{
			int returnVal = fc.showDialog(this, "Save");
			if(returnVal ==JFileChooser.APPROVE_OPTION){
				File file = fc.getSelectedFile();
				
				tdMap.writeMaptoFile(file.getPath() +".TDMap");
			}
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
	}
	public void updateStartAndEnd(int widthOfMap, int heightOfMap){
		controlPanel.updateStartAndEnd(widthOfMap, heightOfMap);
	}
	@Override
	public void mouseClicked(MouseEvent e) {		
		
		//first, get the point of the grid where we clicked.
		double XPixels = tdMap.getGridWidth()*tdMap.tileWidth_Pixel;
		double YPixels = tdMap.getGridHeight()*tdMap.tileHeight_Pixel;
		double xRatio = ((double)e.getX())/(XPixels);
		double yRatio = ((double)e.getY())/(YPixels);
		
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