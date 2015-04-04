package views;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;

import models.TDMap;

public class MapControlPanel extends JPanel{

	//public JPanel labelPanel = new JPanel();
	JLabel lblInfo1 = new JLabel("Width = ");
	JLabel lblInfo2 = new JLabel(", Height = ");
	JLabel lblInfo3 = new JLabel(", Start = (");
	JLabel lblInfo4 = new JLabel(",");
	JLabel lblInfo5 = new JLabel(") End = (");
	JLabel lblInfo6 = new JLabel(",");
	JLabel lblInfo7 = new JLabel(")");
	JComboBox widthList, heightList, startWidthIndexes, startHeightIndexes, endWidthIndexes, endHeightIndexes;
	JButton bInitialize = new JButton("Re-Initialize the Map");
	JButton bReturn = new JButton("Main Menu");
	TDMap tdMap;
	
	public MapControlPanel(TDMap map){
        //add the info label to this panel
		this.tdMap= map;
        this.add(lblInfo1);
        this.setWidthIndexes();
        this.add(widthList);
        this.add(lblInfo2);
        this.setHeightIndexes();
        this.add(heightList);
        startWidthIndexes= this.setIndexes(startWidthIndexes, 0, tdMap.getGridWidth()-1);
        startHeightIndexes= this.setIndexes(startHeightIndexes, 0, tdMap.getGridHeight()-1);
        endWidthIndexes= this.setIndexes(endWidthIndexes, 0, tdMap.getGridWidth()-1);
        endHeightIndexes= this.setIndexes(endHeightIndexes, 0, tdMap.getGridHeight()-1);
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
        this.add(bReturn);
	}
	public JButton getInitializeButton(){
		return bInitialize;
	}
	public JButton getReturnButton(){
		return bReturn;
	}
	public JComboBox getWidthIndexes()
	{
		return widthList;
	}
	public void setWidthIndexes()
	{
		String[] widths = new String[TDMap.MAXWIDTH-TDMap.MINWIDTH+1];
		for(int i=TDMap.MINWIDTH, j=0; i<=TDMap.MAXWIDTH; i++, j++)
		{
			widths[j]= Integer.toString(i);
		}
		widthList = new JComboBox(widths);
	}
	public JComboBox getHeightIndexes()
	{
		return heightList;
	}
	public void setHeightIndexes()
	{

		String[] heights = new String[TDMap.MAXHEIGHT-TDMap.MINHEIGHT+1];
		for(int i=TDMap.MINHEIGHT, j=0; i<=TDMap.MAXHEIGHT; i++, j++)
		{
			heights[j]= Integer.toString(i);
		}
		heightList = new JComboBox(heights);
	}
	
	public JComboBox setIndexes(JComboBox box, int x, int y)
	{
		String[] indexes = new String[y-x+1];
		for(int i=x, j=0; i<=y; i++, j++)
		{
			indexes[j]= Integer.toString(i);
		}
		box = new JComboBox(indexes);
		return box;
	}
	
	public void update(int start1To, int start2To, int end1To, int end2To)
	{
		
	}
	
}
