package grovpackage;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.ImageIcon;

import javazoom.jlgui.basicplayer.BasicPlayerException;


/**
 * @author Elf
 *Buttons actions class for the play, pause, stop, prev, next
 *buttons
 */
public class PlayerButtonsAction extends AbstractAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public PlayerButtonsAction( String actionName, GroooovPlayer player, ImageIcon icnLageIcon, String description ){
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
		if(command.equalsIgnoreCase("PLAY")){
		    player.playSong();
		    return;
		}
		
		if(command.equalsIgnoreCase("RESUME")){
			player.resumeSong();
			return;
		}
		
		if( command.equalsIgnoreCase("PAUSE") ){
			try {
				player.pauseSong();
			} catch (BasicPlayerException e1) {
				e1.printStackTrace();
			}
			return;
		}
		
		if( command.equalsIgnoreCase("STOP") ){
			try {
				player.stopSong();
			} catch (BasicPlayerException e1) {
				e1.printStackTrace();
			}
			return;
		}
		
		if( command.equalsIgnoreCase("MUTE")){
			try {
				player.setGain(0);
			} catch (BasicPlayerException e1) {
				e1.printStackTrace();
			}
			return;
		}
		
		if( command.equalsIgnoreCase("FORWARD")){
			try {
				player.seek(0);
			} catch (BasicPlayerException e1) {
				e1.printStackTrace();
			}
			return;
		}
		
		if(command.equalsIgnoreCase("REWIND")){
			try {
				player.seek(0);
			} catch (BasicPlayerException e1) {
				e1.printStackTrace();
			}
		}
		
		if(command.equalsIgnoreCase("NEXT")){
			player.playNext();
			return;
		}
		
		if(command.equalsIgnoreCase("PREVIOUS")){
			player.playPrev();
			return;
		}
	}
	
    private GroooovPlayer player;	
}