package grooveinterfaces;

import com.mpatric.mp3agic.Mp3File;

import javazoom.jlgui.basicplayer.BasicPlayerException;

public interface PlayerStates {
	public void play();
	public void play(Mp3File song);
	public void play(String songName);
	public void resume();
	public void stop() throws BasicPlayerException;
	public void pause() throws BasicPlayerException;
	public void updateGUIElements();
	public String getCommandToObservers();
}
