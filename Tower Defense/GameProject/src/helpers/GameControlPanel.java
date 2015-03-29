package helpers;

import java.awt.Dimension;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.ImageIcon;
public class GameControlPanel extends JPanel  {
	private final int buttonSize = 20;
	//public JPanel labelPanel = new JPanel();
	JButton bStartWave = new JButton("Start Wave 1");
	JLabel lblInfo = new JLabel("Lives = " + ", Money = " + ", Wavenumber = ");
	JButton bPause = new JButton("Pause");
	JButton bReturn = new JButton("Main Menu");
	JButton bBomb = new JButton("BombTowerIcon.png");
	JButton bFire = new JButton("F");
	JButton bIceBeam = new JButton("I");
	JButton bLaser = new JButton("L");
	
	public GameControlPanel(){

        //add the info label to this panel
		this.add(bBomb);
		this.add(bFire);
		this.add(bIceBeam);
		this.add(bLaser);
		this.add(bStartWave);
        this.add(lblInfo);
        this.add(bPause);
        this.add(bReturn);
        
		bBomb.setPreferredSize(new Dimension(buttonSize, buttonSize));
		bFire.setPreferredSize(new Dimension(buttonSize, buttonSize));
		bIceBeam.setPreferredSize(new Dimension(buttonSize, buttonSize));
		bLaser.setPreferredSize(new Dimension(buttonSize,buttonSize));
		bBomb.setName("Bomb");
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
	public JButton getBombButton(){
		return bBomb;
	}
	public JButton getFireButton(){
		return bFire;
	}
	public JButton getIceButton(){
		return bIceBeam;
	}
	public JButton getLaserButton(){
		return bLaser;
	}


}
