package helpers;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.KeyListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JLabel;

public class GamePlayPanel extends JPanel {

        
	public GamePlayPanel(){
		//set panel properties
		setBackground(Color.BLACK);
        setPreferredSize(new Dimension(GameActivity.PIXELWIDTH , GameActivity.PIXELHEIGHT- GameControlPanelGeneral.getControlPanelHeight()));
        setDoubleBuffered(true);
        setVisible(true);
        
        setFocusable(true);
        requestFocus();

        
	}

	

}
