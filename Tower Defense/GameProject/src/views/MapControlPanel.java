package views;

import helpers.Artist_Swing;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import models.TDMap;

/**
 *
 * @author Yash Gupta
 */
public class MapControlPanel extends JPanel{

	//public JPanel labelPanel = new JPanel();
	JLabel lblInfo1 = new JLabel("Width = ");
	JLabel lblInfo2 = new JLabel(", Height = ");
	JLabel lblInfo3 = new JLabel(", Start = (");
	JLabel lblInfo4 = new JLabel(",");
	JLabel lblInfo5 = new JLabel(") End = (");
	JLabel lblInfo6 = new JLabel(",");
	JLabel lblInfo7 = new JLabel(")");
	//JLabel lblInfo8 = new JLabel(".TDMap");
	//JTextField textField;
	JComboBox widthList, heightList, startWidthIndexes, startHeightIndexes, endWidthIndexes, endHeightIndexes;
	JButton bInitialize = new JButton("Re-Initialize the Map");
	JButton bSetStartAndEnd = new JButton("Set Start & End");
	JButton bReturn = new JButton("Main Menu");
	JButton bSave = new JButton("Save Map");
	TDMap tdMap;
	
    /**
     *
     * @param map
     */
    public MapControlPanel(TDMap map){
        //add the info label to this panel
		this.tdMap= map;
        this.add(lblInfo1);
        this.setWidthIndexes();
        this.add(widthList);
        this.add(lblInfo2);
        this.setHeightIndexes();
        this.add(heightList);
        startWidthIndexes= this.setIndexes(0, tdMap.getGridWidth()-1);
        startHeightIndexes= this.setIndexes(0, tdMap.getGridHeight()-1);
        endWidthIndexes= this.setIndexes(0, tdMap.getGridWidth()-1);
        endHeightIndexes= this.setIndexes(0, tdMap.getGridHeight()-1);
        this.add(lblInfo3);
        this.add(startWidthIndexes);
        this.add(lblInfo4);
        this.add(startHeightIndexes);
        this.add(lblInfo5);
        this.add(endWidthIndexes);
        this.add(lblInfo6);
        this.add(endHeightIndexes);
        this.add(lblInfo7);
        this.add(bInitialize);
        this.add(bSetStartAndEnd);
        //textField= new JTextField(10);
        //textField.setEditable(true);
        //this.add(textField);
        //this.add(lblInfo8);
        this.add(bSave);
        this.add(bReturn);
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
    public JButton getSetStartAndEndButton() {
		return bSetStartAndEnd;
	}

    /**
     *
     * @return
     */
    public JButton getSaveButton()
	{
		return bSave;
	}
	//public JTextField getTextField()
	//{
	//	return textField;
	//}
	public JComboBox getStartWidths()
	{
		return startWidthIndexes;
	}

    /**
     *
     * @return
     */
    public JComboBox getStartHeights()
	{
		return startHeightIndexes;
	}

    /**
     *
     * @return
     */
    public JComboBox getEndWidths()
	{
		return endWidthIndexes;
	}

    /**
     *
     * @return
     */
    public JComboBox getEndHeights()
	{
		return endHeightIndexes;
	}

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
	}
	
}