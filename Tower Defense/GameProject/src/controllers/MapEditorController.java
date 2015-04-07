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
		if(e.getSource() == bReturn){
			mainFrame.dispose();
			new MenuApplicationFrame();
		}
		else if(e.getSource() == bInitialize)
		{
			int widthOfMap= Integer.parseInt((String) this.getControlPanel().getWidthIndexes().getSelectedItem());
			int heightOfMap= Integer.parseInt((String) this.getControlPanel().getHeightIndexes().getSelectedItem());
			tdMap.reinitialize(widthOfMap, heightOfMap,"Generic");
			this.TDMapReinitialized();
			this.controlPanel.setStartPointLabel(new Point(0,0));
			this.controlPanel.setEndPointLabel(new Point(0,0));
			controlPanel.repaint();
		}
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
		double xRatio = ((double)e.getX())/(XPixels);
		double yRatio = ((double)e.getY())/(YPixels);
		
		int xGridPos = (int) Math.floor(xRatio * tdMap.getGridWidth());
		int yGridPos = (int) Math.floor(yRatio * tdMap.getGridHeight());
		
		if(selectingStart){
			if(goodStartOrEnd(xGridPos, yGridPos)){
				tdMap.setStart(xGridPos, yGridPos);
				this.controlPanel.setStartPointLabel(new Point(xGridPos, yGridPos));
				selectingStart = false;
				setControlPanelEnabled(true);
				this.controlPanel.setStatusText("Start set to " + new Point(xGridPos, yGridPos).toString());
			}else{
				this.controlPanel.setStatusText("Please select valid start point.");
			}
		}else if(selectingEnd){
			if(goodStartOrEnd(xGridPos, yGridPos)){
				tdMap.setEnd(xGridPos, yGridPos);
				this.controlPanel.setEndPointLabel(new Point(xGridPos, yGridPos));
				selectingEnd = false;
				setControlPanelEnabled(true);
				this.controlPanel.setStatusText("End set to " + new Point(xGridPos, yGridPos).toString());
			}else{
				this.controlPanel.setStatusText("Please select valid end point.");
			}
		}else{
			tdMap.toggleGrid(xGridPos, yGridPos);	
	        TDMapUpdated();
		}
		
	}
	private boolean goodStartOrEnd(int xGridPos, int yGridPos) {
		//we return true if any of our coordinates are on the boundaries.
		return (xGridPos == 0 || yGridPos == 0 || xGridPos == tdMap.getGridWidth() -1 || yGridPos == tdMap.getGridHeight() -1);
	}

	private void setControlPanelEnabled(boolean b) {
		
		Component[] components = this.controlPanel.getComponents();
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