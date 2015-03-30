package helpers;

import java.awt.Dimension;
import java.awt.Font;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.ImageIcon;
import javax.swing.JSlider;
import javax.swing.JToggleButton;
public class GameControlPanel extends JPanel  {
	private final int buttonSize = 90;
	//public JPanel labelPanel = new JPanel();
	JButton bStartWave = new JButton("Start Wave 1");
	JLabel lblInfo = new JLabel("| Lives = " + ", Money = " + ", Wavenumber =  |");
	//JLabel lblStatus = new JLabel("| Status info will show up here");
	JLabel lblTowerInfo = new JLabel("Tower Type:");
	JButton bPause = new JButton("Pause");
	JButton bReturn = new JButton("Main Menu");
	JSlider jsSpeed = new JSlider(JSlider.HORIZONTAL, 1, 5, 1);
	ButtonGroup towerGroup = new ButtonGroup();
	JToggleButton bSpread = new JToggleButton("Spread beam");
	JToggleButton bFire = new JToggleButton("Fire beam");
	JToggleButton bIceBeam = new JToggleButton("Ice beam");
	JToggleButton bLaser = new JToggleButton("Laser beam");
	
	public GameControlPanel(){
		//add our buttons to a group so only one can be selected at once.
		towerGroup.add(bSpread);
		towerGroup.add(bFire);
		towerGroup.add(bIceBeam);
		towerGroup.add(bLaser);
		Font oldFont = lblInfo.getFont();
		lblInfo.setFont(new Font(oldFont.getFontName(), Font.BOLD, oldFont.getSize()));
		//format the slider
		jsSpeed.setMajorTickSpacing(1);
		jsSpeed.setPaintTicks(true);
		jsSpeed.setPaintLabels(true);
		
        //add everything to this panel.
		this.add(bStartWave);
		this.add(lblTowerInfo);
		this.add(bSpread);
		this.add(bFire);
		this.add(bIceBeam);
		this.add(bLaser);
        this.add(lblInfo);
        this.add(jsSpeed);
        this.add(bPause);
        this.add(bReturn);
        
		bSpread.setPreferredSize(new Dimension(buttonSize, 20));
		bFire.setPreferredSize(new Dimension(buttonSize, 20));
		bIceBeam.setPreferredSize(new Dimension(buttonSize, 20));
		bLaser.setPreferredSize(new Dimension(buttonSize,20));
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
	public JSlider getSpeedSlider(){
		return jsSpeed;
	}


}
