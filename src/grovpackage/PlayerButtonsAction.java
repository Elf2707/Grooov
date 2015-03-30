package grovpackage;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.ImageIcon;
import javax.swing.JButton;

import javazoom.jlgui.basicplayer.BasicPlayerException;

/**
 * @author Elf Buttons actions class for the play, pause, stop, prev, next
 *         buttons
 */
public class PlayerButtonsAction extends AbstractAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private GroooovPlayer player;

	public PlayerButtonsAction(String actionName, GroooovPlayer player,
			ImageIcon icnLageIcon, String description) {
		super();
		this.player = player;
		putValue(Action.NAME, actionName);
		putValue(Action.SMALL_ICON, icnLageIcon);
		putValue(Action.LARGE_ICON_KEY, icnLageIcon);
		putValue(Action.SHORT_DESCRIPTION, description);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		
		String command = getValue(Action.NAME).toString();
		switch (command.toUpperCase()) {
		case "PLAY":
			player.playSong();
			break;
		case "RESUME":
			player.resumeSong();
			break;
		case "PAUSE":
			try {
				player.pauseSong();
			} catch (BasicPlayerException e1) {
				e1.printStackTrace();
			}
			break;
		case "STOP":
			try {
				player.stopSong();
			} catch (BasicPlayerException e1) {
				e1.printStackTrace();
			}
			break;
		case "MUTE":
			try {
				player.setGain(0);
			} catch (BasicPlayerException e1) {
				e1.printStackTrace();
			}
			break;
		case "FORWARD":
			break;
		case "REWIND":
			break;

		case "NEXT":
			player.playNext();
			break;

		case "PREVIOUS":
			player.playPrev();
			break;
		}
	}
}