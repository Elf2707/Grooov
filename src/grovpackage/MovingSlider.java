package grovpackage;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Observable;
import java.util.Observer;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JSlider;
import javax.swing.Timer;

public class MovingSlider extends JSlider implements Observer {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private long songPositionMs;
	private Timer moveTimer;
	private final int MOVE_SPEED = 1000; // milliseconds

	public MovingSlider() {
		moveTimer = new Timer(MOVE_SPEED, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				songPositionMs -= MOVE_SPEED;
				setValue(getMaximum() - (int) songPositionMs);
			}
		});
	}

	public void init(long songDuarationMs) {
		this.songPositionMs = songDuarationMs;
		setMaximum((int) songDuarationMs);
		setMinimum(0);
		setValue(0);
	}

	public void start() {
		moveTimer.start();
	}

	public void stop() {
		moveTimer.stop();
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
			init(0);
			moveTimer.stop();
			break;
		case "pause":
			moveTimer.stop();
			break;
		case "start":
			init(player.getCurrentSong().getLengthInMilliseconds());
			moveTimer.start();
			break;
		case "resume":
			moveTimer.start();
			break;
		case "seek":
			setValue((int) player.getCurrPosMs());
			songPositionMs = getMaximum() - getValue();
			break;
		}
	}
}
