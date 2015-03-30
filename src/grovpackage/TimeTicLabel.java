package grovpackage;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Observable;
import java.util.Observer;
import java.util.TimeZone;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JLabel;
import javax.swing.Timer;

public class TimeTicLabel extends JLabel implements Observer {
	/**
	 * Label with tick time hh:mm:ss
	 */
	private static final long serialVersionUID = 1L;
	private final int MOVE_SPEED = 500; // milliseconds
	private Timer changeTimer;
	private long labelTimeMs;
	private SimpleDateFormat outFormat = new SimpleDateFormat("HH:mm:ss");

	public TimeTicLabel(long initlabelTimeMs) {
		initText(initlabelTimeMs);
		changeTimer = new Timer(MOVE_SPEED, new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				labelTimeMs -= MOVE_SPEED;
				setText(outFormat.format(new Date(labelTimeMs)));
			}
		});
	}

	public void start() {
		changeTimer.start();
	}

	public void stop() {
		changeTimer.stop();
	}

	public void initText(long labelTimeMs) {
		long timeZonOffset = TimeZone.getDefault().getRawOffset();
		this.labelTimeMs = labelTimeMs - timeZonOffset;
		setText(outFormat.format(new Date(this.labelTimeMs)));
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
			initText(0);
			changeTimer.stop();
			break;
		case "pause":
			changeTimer.stop();
			break;
		case "start":
			initText(player.getCurrentSong().getLengthInMilliseconds());
			changeTimer.start();
			break;
		case "resume":
			changeTimer.start();
			break;
		case "seek":
			long position = player.getCurrentSong().getLengthInMilliseconds()
					- player.getCurrPosMs();
			initText(position);
			break;
		}
	}
}
