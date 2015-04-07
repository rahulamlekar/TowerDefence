package views;

import helpers.Artist_Swing;

import java.awt.Dimension;
import java.awt.Font;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JToggleButton;
import models.Tower_Fire;
import models.Tower_IceBeam;
import models.Tower_Laser;
import models.Tower_SpreadShot;

/**
 *
 * 
 * 
 */
public class GameControlPanel extends JPanel  {
	private final int buttonSize = 90;

    /**
     *
     */
	private static final String SPREADTEXT = "A Spread-beam tower. Shoots multiple enemies with a slightly less powerful beam.";
	private static final String LASERTEXT = "A Laser-beam tower. Average stats.";
	private static final String ICETEXT = "An Ice-beam tower. Slows enemies who are shot by it.";
	private static final String FIRETEXT = "A Fire-beam tower. Lights critters on fire and damages them over time.";
    public static final int CONTROLPANELHEIGHT = 150;
	//we have our two control panels.
	JPanel towerControlPanel = new JPanel();
	JPanel generalControlPanel = new JPanel();
	//our tower control panel will have all info pertaining to our tower
	//this includes tower buttons, upgrading, selling, etc
	JButton bSellTower = new JButton("Sell");
	JButton bUpgradeTower = new JButton("Upgrade");
	JLabel lblTowerInfo = new JLabel("No tower selected");
	JLabel lblBuildTowerPrompt = new JLabel("| Build:  ");
	ButtonGroup towerGroup = new ButtonGroup();
	JToggleButton bSpread = new JToggleButton("Spread (" + Tower_SpreadShot.getBuyPrice() + ")");
	JToggleButton bFire = new JToggleButton("Fire (" + Tower_Fire.getBuyPrice() + ")");
	JToggleButton bIceBeam = new JToggleButton("Ice (" + Tower_IceBeam.getBuyPrice() + ")");
	JToggleButton bLaser = new JToggleButton("Laser (" + Tower_Laser.getBuyPrice() + ")");
	JToggleButton bNone = new JToggleButton("None");
	JComboBox<String> cbStrategy = new JComboBox<String>();
	
	//our general control panel will have general info (lives, money)
	//this also includes main menu button, pausing, speeding up game, etc.
	JButton bStartWave = new JButton("Start Wave 1");
	JLabel lblInfo = new JLabel("| Lives = " + ", Money = " + ", Wavenumber =  |");
	JButton bPause = new JButton("Pause");
	JButton bReturn = new JButton("Main Menu");
	JSlider jsSpeed = new JSlider(JSlider.HORIZONTAL, 1, 5, 1);
	JButton bCritInfo = new JButton("Critter Info");

    /**
     *
     */
    public GameControlPanel(){
		//add our buttons to a group so only one can be selected at once.
		towerGroup.add(bSpread);
		towerGroup.add(bFire);
		towerGroup.add(bIceBeam);
		towerGroup.add(bLaser);
		towerGroup.add(bNone);
		bUpgradeTower.setPreferredSize(new Dimension(buttonSize+20,20));
		bSellTower.setPreferredSize(new Dimension(buttonSize, 20));
		bSpread.setPreferredSize(new Dimension(buttonSize+10, 20));
		bFire.setPreferredSize(new Dimension(buttonSize, 20));
		bIceBeam.setPreferredSize(new Dimension(buttonSize-10, 20));
		bLaser.setPreferredSize(new Dimension(buttonSize+10,20));
		bNone.setPreferredSize(new Dimension(buttonSize-40, 20));
		bSpread.setName("Spread");
		bFire.setName("Fire");
		bIceBeam.setName("IceBeam");
		bLaser.setName("Laser");
		bNone.setName("None");
		bSpread.setToolTipText(SPREADTEXT);
		bLaser.setToolTipText(LASERTEXT);
		bIceBeam.setToolTipText(ICETEXT);
		bFire.setToolTipText(FIRETEXT);
		//add initial strategies.
		cbStrategy.addItem("Closest");
		cbStrategy.addItem("Farthest");
		cbStrategy.addItem("Fastest");
		cbStrategy.addItem("Weakest");
		cbStrategy.addItem("Strongest");
		Font oldFont = lblInfo.getFont();
		lblInfo.setFont(new Font(oldFont.getFontName(), Font.BOLD, oldFont.getSize()));
		//format the slider
		jsSpeed.setMajorTickSpacing(1);
		jsSpeed.setPaintTicks(true);
		jsSpeed.setPaintLabels(true);
		towerControlPanel.add(lblTowerInfo);
		towerControlPanel.add(cbStrategy);
		towerControlPanel.add(bUpgradeTower);
		towerControlPanel.add(bSellTower);
		towerControlPanel.add(lblBuildTowerPrompt);
		towerControlPanel.add(bSpread);
		towerControlPanel.add(bFire);
		towerControlPanel.add(bIceBeam);
		towerControlPanel.add(bLaser);
		towerControlPanel.add(bSpread);
		towerControlPanel.add(bNone);
        //add everything to this panel.
		generalControlPanel.add(bCritInfo);
		generalControlPanel.add(bStartWave);
		generalControlPanel.add(lblInfo);
		generalControlPanel.add(jsSpeed);
		generalControlPanel.add(bPause);
		generalControlPanel.add(bReturn);
        
		//add our two panels to the main panel
		this.add(towerControlPanel);
		this.add(generalControlPanel);
		this.setSize(Artist_Swing.PIXELWIDTH, CONTROLPANELHEIGHT);
		this.validate();
	}

    /**
     *
     * @return
     */
    public JButton getSellButton(){
		return this.bSellTower;
	}
	
    /**
     *
     * @return
     */
    public JButton getUpgradeButton(){
		return this.bUpgradeTower;
	}
    public JButton getCritterInfoButton(){
    	return this.bCritInfo;
    }
    /**
     *
     * @return
     */
    public static int getControlPanelHeight(){
		return CONTROLPANELHEIGHT;
	}

    /**
     *
     * @return
     */
    public JLabel getInfoLabel(){
		return lblInfo;
	}

    /**
     *
     * @param text
     */
    public void setTowerInfoLabelText(String text){
		lblTowerInfo.setText(text);
	}

    /**
     *
     * @param text
     */
    public void setInfoLabelText(String text){
		lblInfo.setText(text);
	}
	
    /**
     *
     * @return
     */
    public JButton getPauseButton(){
		return bPause;
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
    public JButton getStartWaveButton(){
		return bStartWave;
	}

    /**
     *
     * @return
     */
    public JToggleButton getSpreadButton(){
		return bSpread;
	}

    /**
     *
     * @return
     */
    public JToggleButton getFireButton(){
		return bFire;
	}

    /**
     *
     * @return
     */
    public JToggleButton getIceButton(){
		return bIceBeam;
	}

    /**
     *
     * @return
     */
    public JToggleButton getLaserButton(){
		return bLaser;
	}

    /**
     *
     * @return
     */
    public JToggleButton getNoneButton(){
		return bNone;
	}

    /**
     *
     * @return
     */
    public JSlider getSpeedSlider(){
		return jsSpeed;
	}

    /**
     *
     * @return
     */
    public JComboBox<String> getCBStrategy(){
		return cbStrategy;
	}


}
