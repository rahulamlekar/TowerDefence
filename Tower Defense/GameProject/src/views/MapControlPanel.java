package views;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;

import models.Point;
import models.TDMap;

/**
 *
 * 
 * 
 */
public class MapControlPanel extends JPanel{

	JLabel lblWidthLabel = new JLabel("Width = ");
	JLabel lblHeightLabel = new JLabel("Height = ");
	JLabel lblStartLabel = new JLabel("Start = (,)");
	JLabel lblEndLabel = new JLabel("End = (,)");
	JLabel lblStatus = new JLabel("Status:");
	JLabel lblFormatLabel = new JLabel(" | ");
	JComboBox widthList;
	JComboBox heightList;
	JButton bInitialize = new JButton("Re-Initialize the Map");

	JButton bReturn = new JButton("Main Menu");
	JButton bSave = new JButton("Save Map");
	TDMap tdMap;
	JButton bSelectStart = new JButton("Select start");
	JButton bSelectEnd = new JButton("Select end");
	
    /**
     *
     * @param map
     */
    public MapControlPanel(TDMap map){
        //add the info label to this panel
		this.tdMap= map;
        this.add(lblWidthLabel);
        this.setWidthIndexes();
        this.add(widthList);
        this.add(lblHeightLabel);
        this.setHeightIndexes();
        this.add(heightList);
        this.add(bInitialize);
        this.add(lblStartLabel);
        this.add(bSelectStart);
        this.add(lblEndLabel);
        this.add(bSelectEnd);
        this.add(lblFormatLabel);

        this.add(bSave);
        this.add(bReturn);
        this.add(lblStatus);

	}
    public void setStatusText(String text){
    	lblStatus.setText(text);
    }
    /**
     *
     * @return
     */
    public JButton getInitializeButton(){
		return bInitialize;
	}

    /**
     *
     * @return
     */
    public JButton getReturnButton(){
		return bReturn;
	}

    /**
     *
     * @return
     */
   /* public JButton getSetStartAndEndButton() {
		return bSetStartAndEnd;
	}*/

    /**
     *
     * @return
     */
    public JButton getSaveButton()
	{
		return bSave;
	}
    public JButton getSelectStartButton(){
    	return bSelectStart;
    }
    public JButton getSelectEndButton(){
    	return bSelectEnd;
    }
	//public JTextField getTextField()
	//{
	//	return textField;
	//}

    /**
     *
     * @return
     */
    //	public JComboBox getStartWidths()
	//{
	//	return startWidthIndexes;
	//}
    public void setStartPointLabel(Point p){
    	lblStartLabel.setText("Start (" + p.getX() + ", " + p.getY() + ")");
    }
    public void setEndPointLabel(Point p){
    	lblEndLabel.setText("End (" + p.getX() + ", " + p.getY() + ")");
    }
    /**
     *
     * @return
     */
   /* public JComboBox getStartHeights()
	{
		return startHeightIndexes;
	}*/

    /**
     *
     * @return
     */
   /* public JComboBox getEndWidths()
	{
		return endWidthIndexes;
	}*/

    /**
     *
     * @return
     */
   /* public JComboBox getEndHeights()
	{
		return endHeightIndexes;
	}*/

    /**
     *
     * @return
     */
    public JComboBox getWidthIndexes()
	{
		return widthList;
	}

    /**
     *
     */
    public void setWidthIndexes()
	{
		String[] widths = new String[TDMap.MAXWIDTH-TDMap.MINWIDTH+1];
		for(int i=TDMap.MINWIDTH, j=0; i<=TDMap.MAXWIDTH; i++, j++)
		{
			//if(Artist_Swing.PIXELWIDTH % i == 0){
				widths[j]= Integer.toString(i);
			//}
		}
		widthList = new JComboBox(widths);
	}

    /**
     *
     * @return
     */
    public JComboBox getHeightIndexes()
	{
		return heightList;
	}

    /**
     *
     */
    public void setHeightIndexes()
	{

		String[] heights = new String[TDMap.MAXHEIGHT-TDMap.MINHEIGHT+1];
		for(int i=TDMap.MINHEIGHT, j=0; i<=TDMap.MAXHEIGHT; i++, j++)
		{
			//if(Artist_Swing.GAMEPIXELHEIGHT % i ==0){
				heights[j]= Integer.toString(i);
			//}
		}
		heightList = new JComboBox(heights);
	}
	
	private JComboBox setIndexes(int x, int y)
	{
		String[] indexes = new String[y-x+1];
		for(int i=x, j=0; i<=y; i++, j++)
		{
			
			indexes[j]= Integer.toString(i);
		}
		JComboBox box = new JComboBox(indexes);
		return box;
	}
	
    /**
     *
     * @param widthOfMap
     * @param heightOfMap
     */
	/*
    public void updateStartAndEnd(int widthOfMap, int heightOfMap)
	{
		startWidthIndexes.removeAllItems();
		for(int i=0; i<widthOfMap; i++){
			startWidthIndexes.addItem(Integer.toString(i));
		}
		startHeightIndexes.removeAllItems();
		for(int i=0; i<heightOfMap; i++){
			startHeightIndexes.addItem(Integer.toString(i));
		}
		endWidthIndexes.removeAllItems();
		for(int i=0; i<widthOfMap; i++){
			endWidthIndexes.addItem(Integer.toString(i));
		}
		endHeightIndexes.removeAllItems();
		for(int i=0; i<heightOfMap; i++){	
			endHeightIndexes.addItem(Integer.toString(i));
		}
		this.repaint();
	}*/
	
}