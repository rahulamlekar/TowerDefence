package helpers;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class GameControlPanel extends JPanel  {

	//public JPanel labelPanel = new JPanel();
	JLabel lblInfo = new JLabel("Lives = " + ", Money = " + ", Wavenumber = ");
	JButton bPause = new JButton("Pause");
	JButton bReturn = new JButton("Main Menu");
	public GameControlPanel(){
        //add the info label to this panel
        this.add(lblInfo);
        this.add(bPause);
        this.add(bReturn);
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
	public JButton getReturnButton(){
		return bReturn;
	}
	

}
