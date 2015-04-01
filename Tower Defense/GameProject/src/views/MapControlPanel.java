package views;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;

import models.TDMap;

public class MapControlPanel extends JPanel {

	//public JPanel labelPanel = new JPanel();
	JLabel lblInfo1 = new JLabel("Width = ");
	JLabel lblInfo2 = new JLabel(", Height = ");
	JLabel lblInfo3 = new JLabel(", Start = (,)");
	JLabel lblInfo4 = new JLabel(", End = (,)");
	JComboBox widthList, heightList;
	JButton bInitialize = new JButton("Re-Initialize the Map");
	JButton bReturn = new JButton("Main Menu");
	public MapControlPanel(){
        //add the info label to this panel
        this.add(lblInfo1);
        this.setWidthIndexes();
        this.add(widthList);
        this.add(lblInfo2);
        this.setHeightIndexes();
        this.add(heightList);
        this.add(lblInfo3);
        this.add(lblInfo4);
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
}
