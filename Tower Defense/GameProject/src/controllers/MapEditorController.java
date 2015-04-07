package controllers;

import views.*;

import java.awt.Component;
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
import models.Point;
import models.TDMap;

/**
 *  The main controller for the Map Editor. This class will orchestrate all the
 *  subtleties that are involved with creating a Game Map. The Map Controller
 *  will allow the user to resize the map, set start and end paths, validate the
 *  map and also save the map to a desired ".TDMap" file.
 */
public class MapEditorController extends MapPanel implements ActionListener, MouseListener, IObserverTDMap {

	//declare game specific variables

    /**
     *  The Map Panel. It deals with tiles, that can either be Path or Tower
     *  tiles.
     */
    	protected MapPanel mapPanel;

    /**
     *  The Map Control Panel that is displayed at the bottom of the screen.
     */
    protected MapControlPanel controlPanel;

    /**
     *
     */
    protected MapEditorApplicationFrame activity;

    /**
     *
     */
    protected TDMap tdMap;
	
	//declare frame specific variables
	private Timer timer;
	private JButton bReturn;
	private JButton bInitialize;
	//private JButton bSetStartAndEnd;
	private JButton bSave;
	private JButton bSelectStart;
	private JButton bSelectEnd;
	JFrame mainFrame;
	private boolean selectingStart;
	private boolean selectingEnd;
	private int tileWidth_Pixel, tileHeight_Pixel;
	
    /**
     *
     */ 
	JFileChooser fc = new JFileChooser();
	
    /**
     *
     * @param map   Takes a TDMap object to change it's configuration, and 
     *              (re)initialize it to a different map.
     */
    public MapEditorController(TDMap map)
	{
		//create Field pointer defined in controller
		mapPanel = this;
		controlPanel = new MapControlPanel(map);
		bReturn = this.getControlPanel().getReturnButton();
		bReturn.addActionListener(this);
		bInitialize = this.getControlPanel().getInitializeButton();
		bInitialize.addActionListener(this);
		//bSetStartAndEnd = this.getControlPanel().getSetStartAndEndButton();
		//bSetStartAndEnd.addActionListener(this);
		bSave = this.getControlPanel().getSaveButton();
		bSave.addActionListener(this);
		bSelectStart = this.getControlPanel().getSelectStartButton();
		bSelectStart.addActionListener(this);
		bSelectEnd = this.getControlPanel().getSelectEndButton();
		bSelectEnd.addActionListener(this);
		this.tdMap= map;
		map.addObserver(this);
		//drawableEntities.add(tdMap);
		timer = new Timer(MapEditorApplicationFrame.TIMEOUT,this);
		timer.start();
		mapPanel.addMouseListener(this);
		//bInitialize.doClick();
		
		this.getControlPanel().getWidthIndexes().setSelectedIndex(tdMap.getGridWidth() - TDMap.MINWIDTH);
		this.getControlPanel().getHeightIndexes().setSelectedIndex(tdMap.getGridHeight() - TDMap.MINHEIGHT);
		this.getControlPanel().setStartPointLabel(tdMap.getStart());
		this.getControlPanel().setEndPointLabel(tdMap.getEnd());
		/*
		this.getControlPanel().getStartWidths().setSelectedIndex(tdMap.getStart().getX());
		this.getControlPanel().getStartHeights().setSelectedIndex(tdMap.getStart().getY());
		this.getControlPanel().getEndWidths().setSelectedIndex(tdMap.getEnd().getX());
		this.getControlPanel().getEndHeights().setSelectedIndex(tdMap.getEnd().getY());*/
	}

    /**
     *
     * @param mFrame
     */
    public void setMainFrame(JFrame mFrame){
		mainFrame = mFrame;
	}
	
    /**
     *
     * @return
     */
    public MapControlPanel getControlPanel(){
		return controlPanel;
	}

    /**
     *
     * @return
     */
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
			this.controlPanel.setStartPointLabel(new Point(0,0));
			this.controlPanel.setEndPointLabel(new Point(0,0));
			controlPanel.repaint();
		}
		/*else if(e.getSource() == bSetStartAndEnd)
		{
			tdMap.setStart(Integer.parseInt((String) this.getControlPanel().getStartWidths().getSelectedItem()),
					Integer.parseInt((String) this.getControlPanel().getStartHeights().getSelectedItem()));
			tdMap.setEnd(Integer.parseInt((String) this.getControlPanel().getEndWidths().getSelectedItem()),
					Integer.parseInt((String) this.getControlPanel().getEndHeights().getSelectedItem()));
		}*/
		else if(e.getSource() == bSave)
		{
			int returnVal = fc.showDialog(this, "Save");
			if(returnVal ==JFileChooser.APPROVE_OPTION){
				File file = fc.getSelectedFile();
				
				tdMap.writeMaptoFile(file.getPath() +".TDMap");
			}
		}
		else if(e.getSource() == bSelectStart){
			selectingStart = true;
			setControlPanelEnabled(false);
		}
		else if(e.getSource() == bSelectEnd){
			selectingEnd = true;
			setControlPanelEnabled(false);
		}
		else
			Draw();

	}
	
    /**
     *
     */
    public void Draw() {
		mapPanel.repaint();
	}
	
	@Override
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		tdMap.updateAndDraw(g);
        Toolkit.getDefaultToolkit().sync();

	}
	
    /**
     *
     * @return
     */
    public TDMap getTDMap() {
		return this.tdMap;
	}

    @Override
    public void TDMapUpdated() {
		Draw();
	}

    @Override
    public void TDMapReinitialized() {
		tileWidth_Pixel= tdMap.getTileWidth_pixel();
		tileHeight_Pixel= tdMap.getTileHeight_pixel();
		TDMapUpdated();
	}

    /**
     *  This will update the indexes of the Start(,) and End(,) ComboBoxes to
     *  match with the new initialized size of the map.
     * @param widthOfMap    The new width of the map
     * @param heightOfMap   The new height of the map
     */
   /* public void updateStartAndEnd(int widthOfMap, int heightOfMap){
		controlPanel.updateStartAndEnd(widthOfMap, heightOfMap);
	}*/
	@Override
	public void mouseClicked(MouseEvent e) {		
		
		//first, get the point of the grid where we clicked.
		double XPixels = tdMap.getGridWidth()*tdMap.tileWidth_Pixel;
		double YPixels = tdMap.getGridHeight()*tdMap.tileHeight_Pixel;
		double xRatio = ((double)e.getX())/(XPixels);
		double yRatio = ((double)e.getY())/(YPixels);
		
		int xGridPos = (int) Math.floor(xRatio * tdMap.getGridWidth());
		int yGridPos = (int) Math.floor(yRatio * tdMap.getGridHeight());
		
		if(selectingStart){
			tdMap.setStart(xGridPos, yGridPos);
			this.controlPanel.setStartPointLabel(new Point(xGridPos, yGridPos));
			selectingStart = false;
			setControlPanelEnabled(true);

		}else if(selectingEnd){
			tdMap.setEnd(xGridPos, yGridPos);
			this.controlPanel.setEndPointLabel(new Point(xGridPos, yGridPos));
			selectingEnd = false;
			setControlPanelEnabled(true);
		}else{
			tdMap.toggleGrid(xGridPos, yGridPos);	
		}
		
	}
	private void setControlPanelEnabled(boolean b) {
		
		Component[] components = this.controlPanel.getComponents();
		for(Component c : components){
			c.setEnabled(b);
		}
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