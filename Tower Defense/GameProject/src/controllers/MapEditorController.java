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
import javax.swing.JLabel;
import javax.swing.Timer;

import views.MapPanel;
import models.Point;
import models.TDMap;

/**
 *  The main controller for the Map Editor. This class will orchestrate all the
 *  subtleties that are involved with creating a Game Map. The Map Controller
 *  will allow the user to resize the map, set start and end paths, validate the
 *  map and also save the map to a desired ".TDMap" file.
 */
public class MapEditorController extends MapPanel implements ActionListener, MouseListener {

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
	
	private Timer timer;
	private JButton bReturn;
	private JButton bInitialize;
	private JButton bSave;
	private JButton bSelectStart;
	private JButton bSelectEnd;
	JFrame mainFrame;
	private boolean selectingStart;
	private boolean selectingEnd;
	//private int tileWidth_Pixel, tileHeight_Pixel;
	
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
		bSave = this.getControlPanel().getSaveButton();
		bSave.addActionListener(this);
		bSelectStart = this.getControlPanel().getSelectStartButton();
		bSelectStart.addActionListener(this);
		bSelectEnd = this.getControlPanel().getSelectEndButton();
		bSelectEnd.addActionListener(this);
		this.tdMap= map;
		timer = new Timer(MapEditorApplicationFrame.TIMEOUT,this);
		timer.start();
		mapPanel.addMouseListener(this);
		//set the initially selected indices of the map.
		this.getControlPanel().getWidthIndexes().setSelectedIndex(tdMap.getGridWidth() - TDMap.MINWIDTH);
		this.getControlPanel().getHeightIndexes().setSelectedIndex(tdMap.getGridHeight() - TDMap.MINHEIGHT);
		this.getControlPanel().setStartPointLabel(tdMap.getStart());
		this.getControlPanel().setEndPointLabel(tdMap.getEnd());
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
		//bReturn = go back to main menu.
		if(e.getSource() == bReturn){
			mainFrame.dispose();
			new MenuApplicationFrame();
		}
		//bInitialize clears the map paths and reinitializes it to the correct size.
		else if(e.getSource() == bInitialize)
		{
			//set the width & height to be correct
			int widthOfMap= Integer.parseInt((String) this.getControlPanel().getWidthIndexes().getSelectedItem());
			int heightOfMap= Integer.parseInt((String) this.getControlPanel().getHeightIndexes().getSelectedItem());
			//reinitialize the map
			tdMap.reinitialize(widthOfMap, heightOfMap,"Generic");
			this.TDMapReinitialized();
			//set the default start and end points (0 and 0)
			this.controlPanel.setStartPointLabel(new Point(0,0));
			this.controlPanel.setEndPointLabel(new Point(0,0));
			controlPanel.repaint(); //repaint
		}
		//bSave saves the current map
		else if(e.getSource() == bSave)
		{
			//show a save dialog
			int returnVal = fc.showDialog(this, "Save");
			if(returnVal ==JFileChooser.APPROVE_OPTION){ //if they choose to save, save it
				File file = fc.getSelectedFile();
				//we write it to a file
				tdMap.writeMaptoFile(file.getPath() +".TDMap");
			}
		}
		//bSelectStart forces user to select the start point
		else if(e.getSource() == bSelectStart){
			selectingStart = true;
			setControlPanelEnabled(false);
		}
		//bSelectEnd forces user to select the end
		else if(e.getSource() == bSelectEnd){
			selectingEnd = true;
			setControlPanelEnabled(false);
		}
		//otherwise we just want to draw.
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
		//update and draw our map (drawable entity)
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

  //  @Override
    public void TDMapUpdated() {
		Draw();
	}

  //  @Override
    public void TDMapReinitialized() {
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
		//we use ratios to convert the pixel value into grid value
		double xRatio = ((double)e.getX())/(XPixels);
		double yRatio = ((double)e.getY())/(YPixels);
		//we calculate the integer grid position where we clicked
		int xGridPos = (int) Math.floor(xRatio * tdMap.getGridWidth());
		int yGridPos = (int) Math.floor(yRatio * tdMap.getGridHeight());
		
		//if we are selecting the start
		if(selectingStart){
			//if we have a good start or end point, 
			if(goodStartOrEnd(xGridPos, yGridPos)){
				//then we set this to be the start point, and stop selecting the start
				tdMap.setStart(xGridPos, yGridPos);
				this.controlPanel.setStartPointLabel(new Point(xGridPos, yGridPos));
				selectingStart = false;
				setControlPanelEnabled(true);
				this.controlPanel.setStatusText("Start set to " + new Point(xGridPos, yGridPos).toString());
			}else{//otherwise inform them to select a valid point
				this.controlPanel.setStatusText("Please select valid start point.");
			}
		//if we are selecting end,
		}else if(selectingEnd){
			//make sure the point is good
			if(goodStartOrEnd(xGridPos, yGridPos)){
				//set the end point to be the selected point and enable buttons
				tdMap.setEnd(xGridPos, yGridPos);
				this.controlPanel.setEndPointLabel(new Point(xGridPos, yGridPos));
				selectingEnd = false;
				setControlPanelEnabled(true);
				this.controlPanel.setStatusText("End set to " + new Point(xGridPos, yGridPos).toString());
			}else{
				//or inform them that they have to select a good point
				this.controlPanel.setStatusText("Please select valid end point.");
			}
		//if we aren't selecting the start or the end, just toggle the grid.
		}else{
			tdMap.toggleGrid(xGridPos, yGridPos);	
	        TDMapUpdated(); //and update map
		}
		
	}
	/*
	 * Checks to see if we have a good start or end point (lying on one edge)
	 */
	private boolean goodStartOrEnd(int xGridPos, int yGridPos) {
		//we return true if any of our coordinates are on the boundaries.
		return (xGridPos == 0 || yGridPos == 0 || xGridPos == tdMap.getGridWidth() -1 || yGridPos == tdMap.getGridHeight() -1);
	}
	//Sets the control panel to be enabled or diabled (for setting start or end)
	private void setControlPanelEnabled(boolean b) {
		Component[] components = this.controlPanel.getComponents();
		//go through components and disable all of them (except labels)
		for(Component c : components){
			if(c instanceof JLabel ==false){
				c.setEnabled(b);
			}
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