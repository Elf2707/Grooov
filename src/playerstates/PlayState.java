package playerstates;

import com.mpatric.mp3agic.Mp3File;

import grooveinterfaces.PlayerStates;
import grovpackage.GroooovPlayer;
import javazoom.jlgui.basicplayer.BasicPlayerException;

/**
 * Play state of player
 * 
 * @author Elf
 *
 */
public class PlayState implements PlayerStates {
	GroooovPlayer soundPlayer;

	public PlayState(GroooovPlayer soundPlayer) {
		this.soundPlayer = soundPlayer;
	}

	@Override
	public void play() {
		soundPlayer.setCurrentSong();
		play(soundPlayer.getCurrentSong().getFilename());
	}

	@Override
	public void resume() {
		return;
	}

	@Override
	public void stop() throws BasicPlayerException {
		soundPlayer.stop();
		soundPlayer.setPlayerState(soundPlayer.getStopState());
		soundPlayer.setCommandToObservers("stop");
	}

	@Override
	public void pause() throws BasicPlayerException {
		soundPlayer.pause();
		soundPlayer.setPlayerState(soundPlayer.getPauseState());
		soundPlayer.setCommandToObservers("pause");
	}

	@Override
	public void updateGUIElements() {
		return;
	}

	@Override
	public void play(Mp3File song) {
		play(song.getFilename());
	}

	@Override
	public void play(String songName) {
		soundPlayer.openFile(songName);
		try {
			soundPlayer.play();
		} catch (BasicPlayerException e) {
			e.printStackTrace();
		}
		soundPlayer.setCurrentSong();
	}

	@Override
	public String getCommandToObservers() {
		// TODO Auto-generated method stub
		return null;
	}

}
