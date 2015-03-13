package grovpackage;

import java.awt.Font;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Observable;
import java.util.Observer;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JLabel;
import javax.swing.Timer;

/**
 * 
 * @author Elf Label with moving text
 */
public class MoveTextLabel extends JLabel implements Observer {
	private static final long serialVersionUID = 1L;
	private static final int COUNT_CHAR_IN_LABEL = 90;
	private final int MOVE_SPEED = 500; // milliseconds
	private Timer moveTextTimer;
	@SuppressWarnings("unused")
	private int countOfCharsInLabel = -1;
	private int outStringPosition = 0;
	private String movingText;

	/**
	 * 
	 * @param movingText - label text
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

	@Override
	public void update(Observable o, Object source) {
		if (!(source instanceof GroooovPlayer)) {
			Logger.getGlobal().log(Level.SEVERE,
					"Error while managing player with GUI elements");
			return;
		}
		GroooovPlayer player = (GroooovPlayer) source;
		String command = player.getCommandToObservers();

		switch (command.toLowerCase()) {
		case "stop":
			initText("Welcome to the Grooooov!!!!");
			moveTextTimer.stop();
			break;
		case "pause":
			moveTextTimer.stop();
			break;
		case "start":
			initText(player.getCurrentSong().getFilename());
			moveTextTimer.start();
			break;
		case "resume":
			moveTextTimer.start();
			break;
		}
	}
}
