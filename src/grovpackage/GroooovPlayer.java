package grovpackage;

import grooveinterfaces.PlayerStates;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.Observable;
import java.util.Observer;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.DefaultListModel;
import javax.swing.JOptionPane;
import javax.swing.Timer;

import javazoom.jlgui.basicplayer.BasicPlayer;
import javazoom.jlgui.basicplayer.BasicPlayerException;
import playerstates.PauseState;
import playerstates.PlayState;
import playerstates.StopState;

import com.mpatric.mp3agic.Mp3File;

public class GroooovPlayer extends BasicPlayer implements Observer {

	private PlayerStates playState = new PlayState(this);
	private PlayerStates pauseState = new PauseState(this);
	private PlayerStates stopState = new StopState(this);

	// Thread in witch duration of song will change
	// private Thread durationThred;
	public static final int MAX_VOLUME = 100;
	public static final int SATRT_VOLUME = MAX_VOLUME / 2;
	public static final int SEEK_STEP = 300000;
	private PlayerStates playerState = stopState;

	private double currentVolume;
	// PlayList
	private PlayList lstSongsList = null;
	private Mp3File currentSong;

	// Manager between player and GUI elements
	private Observer managerGUI;

	// No playing song
	private int playSongIndex = -1;

	// Command to observers depends of state changing
	private String commandToObservers;
	private final int DURATION_TIMER_TIC = 1000; // ms
	// Song duration timer
	private Timer durationTimer;
	// Song duration in ms
	private long songDuration = 0;
	// Current position in song in ms for seek method
	private long currPosMs = 0;

	public GroooovPlayer(PlayList lstSongsList) {
		super();
		this.lstSongsList = lstSongsList;
		durationTimer = new Timer(DURATION_TIMER_TIC, new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				songDuration -= DURATION_TIMER_TIC;
				if (songDuration <= 0) {
					try {
						playerState.stop();
						playNext();
					} catch (BasicPlayerException e1) {
						e1.printStackTrace();
					}
				}
			}
		});
	}

	public PlayerStates getPlayState() {
		return playState;
	}

	public void setPlayState(PlayerStates playState) {
		this.playState = playState;
	}

	public PlayerStates getPauseState() {
		return pauseState;
	}

	public void setPauseState(PlayerStates pauseState) {
		this.pauseState = pauseState;
	}

	public PlayerStates getStopState() {
		return stopState;
	}

	public void setStopState(PlayerStates stopState) {
		this.stopState = stopState;
	}

	public int getPlaySongIndex() {
		return playSongIndex;
	}

	public void setPlaySongIndex(int playSongIndex) {
		this.playSongIndex = playSongIndex;
	}

	public void setManagerGUI(Observer managerGUI) {
		this.managerGUI = managerGUI;
	}

	public Observer getManagerGUI() {
		return managerGUI;
	}

	public PlayerStates getPlayerState() {
		return playerState;
	}

	public void setPlayerState(PlayerStates newState) {
		playerState = newState;
	}

	public String getCommandToObservers() {
		return commandToObservers;
	}

	public void setCommandToObservers(String commandToObservers) {
		this.commandToObservers = commandToObservers;
		// Command change notifying observers
		managerGUI.update(null, this);
	}

	public void pauseSong() throws BasicPlayerException {
		playerState.pause();
	}

	public void resumeSong() {
		playerState.resume();
	}

	public void openFile(String songName) {
		// Empty PlayList?
		int songsCount = lstSongsList.getModel().getSize();
		if (songsCount == 0) {
			JOptionPane.showMessageDialog(lstSongsList, "No songs in List!!!");
			return;
		}
		int songIndex = lstSongsList.getSelectedIndex();
		if (songIndex == -1) {
			// PlayList not empty, but no songs selected
			songIndex = 0;
			lstSongsList.setSelectedIndex(0);
		}
		File songFile = new File(lstSongsList.getModel()
				.getElementAt(songIndex).getFilename());
		try {
			open(songFile);
			playSongIndex = songIndex;
		} catch (BasicPlayerException e) {
			e.printStackTrace();
		}

	}

	public void openFile(Mp3File mp3Song) {
		openFile(mp3Song.getFilename());
	}

	public void playSong() {
		playerState.play();
	}

	public Mp3File getCurrentSong() {
		return currentSong;
	}

	/**
	 * Set all data about current song take data from list of songs
	 */
	public void setCurrentSong() {
		if ((((DefaultListModel<Mp3File>) lstSongsList.getModel()).isEmpty())) {
			JOptionPane.showMessageDialog(null, "Where is no songs to play!!!");
			playSongIndex = -1;
			currentSong = null;
			songDuration = 0;
			return;
		}
		playSongIndex = lstSongsList.getSelectedIndex();
		if (playSongIndex == -1) {
			// Sets first song in list
			lstSongsList.setSelectedIndex(0);
			playSongIndex = 0;
		}
		currentSong = (((DefaultListModel<Mp3File>) lstSongsList.getModel())
				.getElementAt(playSongIndex));
		songDuration = currentSong.getLengthInMilliseconds();
	}

	public void playSong(File songFile) {
		playerState.play(songFile.getAbsolutePath());
	}

	public void playSong(Mp3File mp3SongFile) {
		playerState.play(mp3SongFile);
	}

	/**
	 * Play next song in List
	 */
	public void playNext() {
		if (playSongIndex == -1) {
			JOptionPane.showMessageDialog(null, "Where is no playing songs!!!");
			return;
		}

		try {
			stopSong();
		} catch (BasicPlayerException e) {
			e.printStackTrace();
		}

		if (playSongIndex == ((DefaultListModel<Mp3File>) lstSongsList
				.getModel()).size() - 1) {
			playSongIndex = 0;
		} else {
			playSongIndex++;
		}
		lstSongsList.setSelectedIndex(playSongIndex);
		playSong(((DefaultListModel<Mp3File>) lstSongsList.getModel())
				.getElementAt(playSongIndex));
	}

	/**
	 * Play previous song
	 */
	public void playPrev() {
		if (playSongIndex == -1) {
			JOptionPane.showMessageDialog(null, "Where is no playing songs!!!");
			return;
		}

		try {
			stopSong();
		} catch (BasicPlayerException e) {
			e.printStackTrace();
		}

		if (playSongIndex == 0) {
			playSongIndex = ((DefaultListModel<Mp3File>) lstSongsList
					.getModel()).size() - 1;
		} else {
			playSongIndex--;
		}
		lstSongsList.setSelectedIndex(playSongIndex);
		playSong(((DefaultListModel<Mp3File>) lstSongsList.getModel())
				.getElementAt(playSongIndex));
	}

	/**
	 * Set volume to the level
	 * 
	 * @param volumeLevel
	 *            - level of volume
	 */
	public void setVolume(int volumeLevel) {
		currentVolume = (double) volumeLevel;
		try {
			super.setGain(currentVolume / MAX_VOLUME);
		} catch (BasicPlayerException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Is player now playing song
	 */
	public boolean isPlaying() {
		return (playerState instanceof PlayState) || (playerState instanceof PauseState);
	}

	public void mute(boolean onMute) {
		if (onMute) {
			setVolume(0);
		} else {
			setVolume((int) currentVolume);
		}
	}

	public void seekSong(long seekPosMs) throws BasicPlayerException {
		int rate = currentSong.getBitrate() / 8; //kbytes per sec
		long realSeek = super.seek((seekPosMs) * rate ); // (millisec/1000)*kbyte
		currPosMs = realSeek / rate;
		commandToObservers = "seek";
		managerGUI.update(null, this);
	}

	public long getCurrPosMs() {
		return currPosMs;
	}

	public void stopSong() throws BasicPlayerException {
		playerState.stop();
	}

	public void startDuarationCalc() {
		durationTimer.start();
	}

	public void stopDuarationCalc() {
		durationTimer.stop();
	}

	@Override
	public void update(Observable o, Object source) {
		if (!(source instanceof GroooovPlayer)) {
			Logger.getGlobal().log(Level.SEVERE,
					"Error in player managin song duration");
			return;
		}
		GroooovPlayer player = (GroooovPlayer) source;
		String command = player.getCommandToObservers();

		switch (command.toLowerCase()) {
		case "stop":
			durationTimer.stop();
			break;
		case "pause":
			durationTimer.stop();
			break;
		case "start":
			durationTimer.start();
			break;
		case "resume":
			durationTimer.start();
			break;
		case "seek":
			songDuration = currentSong.getLengthInMilliseconds() - currPosMs;
			break;
		}
	}

}
