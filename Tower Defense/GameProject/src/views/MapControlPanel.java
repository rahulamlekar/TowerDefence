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
    public JComboBox getWidthIndexes()
	{
		return widthList;
	}

    /**
     *sets all of the indices for the widths and puts them into an existing JCOmbobox
     */
    public void setWidthIndexes()
	{
		String[] widths = new String[TDMap.MAXWIDTH-TDMap.MINWIDTH+1];
		for(int i=TDMap.MINWIDTH, j=0; i<=TDMap.MAXWIDTH; i++, j++)
		{
			widths[j]= Integer.toString(i);
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
     *sets all of the indices for the heights and puts them into an existing JCombobox
     */
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