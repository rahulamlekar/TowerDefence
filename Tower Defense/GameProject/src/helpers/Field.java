package helpers;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.KeyListener;

import javax.swing.JPanel;
import javax.swing.JLabel;

public class Field extends JPanel {
        public JPanel pnl = new JPanel();
        JLabel gameOver = new JLabel("GameOver...     Press ENTER to restart");

	
	public Field(){
		
		//set panel properties
		setBackground(Color.BLACK);
        setPreferredSize(new Dimension(ApplicationFrame.PIXELWIDTH, ApplicationFrame.PIXELHEIGHT));
        setDoubleBuffered(true);
        setVisible(true);
        this.setFocusable(true);
        this.requestFocus();

        //add the gameover overlay to this panel
        pnl.add(gameOver);
        add(pnl);
        pnl.setVisible(false);
	}
	

}
