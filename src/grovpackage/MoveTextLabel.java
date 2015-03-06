package grovpackage;

import java.awt.Font;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JLabel;
import javax.swing.Timer;

/**
 * 
 * @author Elf Label with moving text
 */
public class MoveTextLabel extends JLabel {
	private static final long serialVersionUID = 1L;
	private static final int COUNT_CHAR_IN_LABEL = 90;
	private final int MOVE_SPEED = 500; // milliseconds
	Timer moveTextTimer;
	int countOfCharsInLabel = -1;
	int outStringPosition = 0;
	String movingText;

	/**
	 * @param speed
	 */
	public MoveTextLabel(String movingText) {
		setBounds(new Rectangle(450, 14));
		initText(movingText);
		moveTextTimer = new Timer(MOVE_SPEED, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				change();
			}
		});
	}

	public void calcCharsInLabel() {
		// calculate pixels per char
		Font font = getFont();
		int fontSize = font.getSize();

		Toolkit kit = Toolkit.getDefaultToolkit();
		int screenResolution = kit.getScreenResolution();

		int charResolution = Math.round((screenResolution / 72F) * fontSize);
		int labelWidth = getSize().width;
		countOfCharsInLabel = labelWidth / charResolution;
	}

	public void initText(String movingText) {
		setHorizontalAlignment(LEFT);
		setText(movingText);

		// Preparing moving text add some spaces before
		StringBuilder tmpStr = new StringBuilder();
		for (int i = 0; i <= COUNT_CHAR_IN_LABEL; i++) {
			tmpStr.append(" ");
		}
		tmpStr.append(movingText);
		this.movingText = tmpStr.toString();
		outStringPosition = COUNT_CHAR_IN_LABEL;
	}

	public String change() {
		if (outStringPosition == movingText.length()) {
			outStringPosition = 1;
		} else {
			outStringPosition++;
		}
		String outString = movingText.substring(outStringPosition);
		setText(outString);
		return outString;
	}

	public void start() {
		moveTextTimer.start();
	}

	public void stop() {
		moveTextTimer.stop();
	}
}