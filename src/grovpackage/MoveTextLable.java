package grovpackage;

import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JLabel;
import javax.swing.Timer;

import dynamiclabel.DynamicLabel;

/**
 * 
 * @author Elf Label with moving text
 */
public class MoveTextLable extends JLabel {

	private static final long serialVersionUID = 1L;
	private long speed; // milliseconds
	Timer moveTextTimer;
	DynamicLabel changer;

	/**
	 * @param speed
	 */
	public MoveTextLable(String moveText, long speed, DynamicLabel changer) {
		this.speed = speed;
		setText(moveText);
		this.changer = changer;
		// calculate pixels per char
					Font font = targetLabel.getFont();
					int fontSize = font.getSize();

					Toolkit kit = Toolkit.getDefaultToolkit();
					int screenResolution = kit.getScreenResolution();

					int charResolution = Math.round((screenResolution / 72F) * fontSize);
					int labelWidth = targetLabel.getSize().width;
					countOfCharsInLabel = labelWidth / charResolution;
					System.out.println(fontSize + "|" + screenResolution + "|" + labelWidth
							+ "|" + countOfCharsInLabel);
	}

	public void initTimer() {
		if( changer != null){
			moveTextTimer = new Timer((int) speed, new ActionListener() {
			    @Override
				public void actionPerformed(ActionEvent e) {
					changer.change(MoveTextLable.this);
				}
			});	
		}
	}

	public void setChanger(DynamicLabel changer) {
		this.changer = changer;
		initTimer();
	}

	public void start() {
		moveTextTimer.start();
	}

	public void stop() {
		moveTextTimer.stop();
	}
}
