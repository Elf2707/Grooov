package playerstates;

import com.mpatric.mp3agic.Mp3File;

import grooveinterfaces.PlayerStates;
import grovpackage.GroooovPlayer;
import javazoom.jlgui.basicplayer.BasicPlayerException;

public class PauseState implements PlayerStates {
	private GroooovPlayer soundPlayer;

	public PauseState(GroooovPlayer soundPlayer) {
		this.soundPlayer = soundPlayer;
	}

	@Override
	public void play() {
		try {
			soundPlayer.resume();
			soundPlayer.setPlayerState(soundPlayer.getPlayState());
			soundPlayer.setCommandToObservers("resume");
		} catch (BasicPlayerException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void resume() {
		play();
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
	public void play(Mp3File song) {
		play();
	}

	@Override
	public void play(String songName) {
		play();
	}

	@Override
	public String getCommandToObservers() {
		return null;
	}

}
