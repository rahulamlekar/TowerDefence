package views;
import java.awt.Color;
import java.awt.Dimension;
import javax.swing.JPanel;


/**
 *  This class sets the background frame for both the GameApplicationFrame and
 *  the MapEditorApplicationFrame.
 * 
 */
public class MapPanel extends JPanel {
    
    /**
     *
     */
    public MapPanel(){
		
		//set panel properties
		setBackground(Color.BLACK);
        setPreferredSize(new Dimension(GameApplicationFrame.PIXELWIDTH , GameApplicationFrame.PIXELHEIGHT- GameControlPanel.getControlPanelHeight()));
        setDoubleBuffered(true);
        setVisible(true);
        setFocusable(true);
        requestFocus();
	}

}
