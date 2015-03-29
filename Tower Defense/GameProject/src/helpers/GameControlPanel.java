package helpers;

import java.awt.Dimension;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.ImageIcon;
import javax.swing.JToggleButton;
public class GameControlPanel extends JPanel  {
	private final int buttonSize = 20;
	//public JPanel labelPanel = new JPanel();
	JButton bStartWave = new JButton("Start Wave 1");
	JLabel lblInfo = new JLabel("Lives = " + ", Money = " + ", Wavenumber = ");
	JButton bPause = new JButton("Pause");
	JButton bReturn = new JButton("Main Menu");
	/*JButton bBomb = new JButton("B");
	JButton bFire = new JButton("F");
	JButton bIceBeam = new JButton("I");
	JButton bLaser = new JButton("L");*/
	ButtonGroup towerGroup = new ButtonGroup();
	JToggleButton bSpread = new JToggleButton("S");
	JToggleButton bFire = new JToggleButton("F");
	JToggleButton bIceBeam = new JToggleButton("I");
	JToggleButton bLaser = new JToggleButton("L");
	
	public GameControlPanel(){
		towerGroup.add(bSpread);
		towerGroup.add(bFire);
		towerGroup.add(bIceBeam);
		towerGroup.add(bLaser);
		
        //add the info label to this panel
		this.add(bSpread);
		this.add(bFire);
		this.add(bIceBeam);
		this.add(bLaser);
		this.add(bStartWave);
        this.add(lblInfo);
        this.add(bPause);
        this.add(bReturn);
        
		bSpread.setPreferredSize(new Dimension(buttonSize, buttonSize));
		bFire.setPreferredSize(new Dimension(buttonSize, buttonSize));
		bIceBeam.setPreferredSize(new Dimension(buttonSize, buttonSize));
		bLaser.setPreferredSize(new Dimension(buttonSize,buttonSize));
		bSpread.setName("Spread");
		bFire.setName("Fire");
		bIceBeam.setName("IceBeam");
		bLaser.setName("Laser");
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
	public JButton getStartWaveButton(){
		return bStartWave;
	}
	public JToggleButton getSpreadButton(){
		return bSpread;
	}
	public JToggleButton getFireButton(){
		return bFire;
	}
	public JToggleButton getIceButton(){
		return bIceBeam;
	}
	public JToggleButton getLaserButton(){
		return bLaser;
	}


}
