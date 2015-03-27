package helpers;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.KeyListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JLabel;

public class Field extends JPanel {
        public JPanel labelPanel = new JPanel();
        JLabel lblInfo = new JLabel("Lives = " + ", Money = " + ", Wavenumber = ");
        JButton bPause = new JButton("Pause");
        
	public Field(){
		
		//set panel properties
		setBackground(Color.BLACK);
        setPreferredSize(new Dimension(ApplicationFrame.PIXELWIDTH, ApplicationFrame.PIXELHEIGHT));
        setDoubleBuffered(true);
        setVisible(true);
        
        setFocusable(true);
        requestFocus();
        //add the info label to this panel
        labelPanel.add(lblInfo);
        labelPanel.add(bPause);
	}
	public JLabel getInfoLabel(){
		return lblInfo;
	}
	public void setInfoLabelText(String text){
		lblInfo.setText(text);
	}
	public JButton getPauseButton(){
		return bPause;
	}
	

}
