package playerstates;

import com.mpatric.mp3agic.Mp3File;

import grooveinterfaces.PlayerStates;
import grovpackage.GroooovPlayer;
import javazoom.jlgui.basicplayer.BasicPlayerException;

public class StopState implements PlayerStates {
	GroooovPlayer soundPlayer;

	public StopState(GroooovPlayer soundPlayer) {
		this.soundPlayer = soundPlayer;
	}

	@Override
	public void play() {
		soundPlayer.setCurrentSong();
		play(soundPlayer.getCurrentSong().getFilename());
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
		soundPlayer.setPlayerState(soundPlayer.getPlayState());
		soundPlayer.setCommandToObservers("start");
	}

	@Override
	public void resume() {
		return;
	}

	@Override
	public void stop() throws BasicPlayerException {
		return;
	}

	@Override
	public void pause() throws BasicPlayerException {
		return;
	}

	@Override
	public void updateGUIElements() {
		return;
	}

	@Override
	public String getCommandToObservers() {
		return null;
	}

}
