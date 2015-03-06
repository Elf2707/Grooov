package grovpackage;

import java.io.File;
import java.io.IOException;

import javax.swing.DefaultListModel;
import javax.swing.JOptionPane;

import com.mpatric.mp3agic.InvalidDataException;
import com.mpatric.mp3agic.Mp3File;
import com.mpatric.mp3agic.UnsupportedTagException;

import javazoom.jlgui.basicplayer.BasicPlayer;
import javazoom.jlgui.basicplayer.BasicPlayerException;

public class GroooovPlayer extends BasicPlayer {

	// Thread in witch duration of song will change
	// private Thread durationThred;
	public static final int MAX_VOLUME = 100;
	public static final int SATRT_VOLUME = MAX_VOLUME / 2;
	public static final int SEEK_STEP = 300000;
	private playerStateType playerState = playerStateType.STOP;
	private double currentVolume;
	// PlayList
	private PlayList lstSongsList = null;
	Mp3File currentSong;

	// No playing song
	int playSongIndex = -1;

	// player states
	private enum playerStateType {
		PAUSE, PLAY, STOP
	};

	public GroooovPlayer(PlayList lstSongsList) {
		super();
		this.lstSongsList = lstSongsList;
		playerState = playerStateType.STOP;
	}

	@Override
	public void pause() throws BasicPlayerException {
		playerState = playerStateType.PAUSE;
		super.pause();
	}

	@Override
	public void resume() throws BasicPlayerException {
		playerState = playerStateType.PLAY;
		super.resume();
	}

	@Override
	public void play() {
		try {
			// if it resume do resume else get song and play it
			if (playerState == playerStateType.PAUSE) {
				resume();
			} else {
				// Empty PlayList?
				int songIndex = lstSongsList.getSelectedIndex();
				int songsCount = lstSongsList.getModel().getSize();
				if (songsCount == 0) {
					JOptionPane.showMessageDialog(lstSongsList,
							"No songs in List!!!");
					return;
				}
				if (songIndex == -1) {
					// PlayList not empty, but no songs selected
					songIndex = 0;
				}
				File songFile = new File(lstSongsList.getModel()
						.getElementAt(songIndex).getFilename());
				super.stop();
				super.open(songFile);
				super.play();
				playSongIndex = songIndex;
				setCurrentSong(new Mp3File(songFile));
			}
			playerState = playerStateType.PLAY;
		} catch (BasicPlayerException | UnsupportedTagException | InvalidDataException | IOException e) {
			e.printStackTrace();
		}
	}

	public Mp3File getCurrentSong() {
		return currentSong;
	}

	public void setCurrentSong( Mp3File mp3File) {
        currentSong = mp3File;
	}

	public void play(File songFile) {
		try {
			if (playerState == playerStateType.PAUSE) {
				resume();
			} else {
				super.stop();
				open(songFile);
				super.play();
			}
			playSongIndex = lstSongsList.getSelectedIndex();
			playerState = playerStateType.PLAY;
		} catch (BasicPlayerException e) {
			e.printStackTrace();
		}
	}

	public void play(Mp3File mp3SongFile) {
		try {
			if (playerState == playerStateType.PAUSE) {
				resume();
			} else {
				super.stop();
				open(new File(mp3SongFile.getFilename()));
				super.play();
			}
			playSongIndex = lstSongsList.getSelectedIndex();
			playerState = playerStateType.PLAY;
			currentSong = mp3SongFile;
		} catch (BasicPlayerException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Play next song in List
	 */
	public void playNext() {
		if (playSongIndex == -1) {
			JOptionPane.showMessageDialog(null, "Where is no playing songs!!!");
			return;
		}
		if (playSongIndex == ((DefaultListModel<Mp3File>) lstSongsList
				.getModel()).size() - 1) {
			playSongIndex = 0;
		} else {
			playSongIndex++;
		}
		lstSongsList.setSelectedIndex(playSongIndex);
		play(((DefaultListModel<Mp3File>) lstSongsList.getModel())
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
		if (playSongIndex == 0) {
			playSongIndex = ((DefaultListModel<Mp3File>) lstSongsList
					.getModel()).size() - 1;
		} else {
			playSongIndex--;
		}
		lstSongsList.setSelectedIndex(playSongIndex);
		play(((DefaultListModel<Mp3File>) lstSongsList.getModel())
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
	 * Is player now plaing song
	 */
	public boolean isPlaing() {
		return playerState == playerStateType.PLAY;
	}

	public void mute(boolean onMute) {
		if (onMute) {
			setVolume(0);
		} else {
			setVolume((int) currentVolume);
		}
	}

	@Override
	public long seek(long arg0) throws BasicPlayerException {
		// long skipBytes = (long) Math.round(((Integer)
		// audioInfo.get("audio.length.bytes")).intValue() * rate);
		// seek(skipBytes);
		return super.seek(SEEK_STEP);
	}

	@Override
	public void stop() throws BasicPlayerException {
		super.stop();
		playSongIndex = -1;
	}
}
