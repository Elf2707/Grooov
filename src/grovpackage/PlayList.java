/**
 * 
 */
package grovpackage;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.Serializable;
import java.util.logging.Logger;

import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.ListCellRenderer;

import com.mpatric.mp3agic.InvalidDataException;
import com.mpatric.mp3agic.Mp3File;
import com.mpatric.mp3agic.UnsupportedTagException;

/**
 * @author Elf Play List List of Songs
 */
public class PlayList extends JList<Mp3File> implements Serializable {

	private static final long serialVersionUID = 1L;
	private DefaultListModel<Mp3File> mp3ListModel;

	public PlayList() {
		mp3ListModel = new DefaultListModel<Mp3File>();
		setModel(mp3ListModel);
		if (mp3ListModel == null) {
			Logger.getGlobal().fine("Error getting listmodel in PlayList");
		}
	}

	@Override
	public void setCellRenderer(ListCellRenderer<? super Mp3File> cellRenderer) {
		super.setCellRenderer(cellRenderer);
	}

	// Add song to List
	public void addSong(File songFile) throws Exception {
		if (!songFile.exists())
			throw new IOException("PlayList:add:Файл не существует");
		Mp3File mp3File = null;
		try {
			mp3File = new Mp3File(songFile);
			mp3ListModel.addElement(mp3File);
		} catch (UnsupportedTagException | InvalidDataException | IOException e1) {
			e1.printStackTrace();
		}
	}

	/**
	 * Change sons places in PlayList
	 * 
	 * @param int srcSongIndex - first song
	 * @param int destSongIndex - second song
	 */
	public void changeSongPlace(int srcSongIndex, int destSongIndex) {
		Mp3File song = null;
		if (mp3ListModel != null) {
			song = mp3ListModel.get(srcSongIndex);
			mp3ListModel.set(srcSongIndex, mp3ListModel.get(destSongIndex));
			mp3ListModel.set(destSongIndex, song);
		}
	}

	/**
	 * Up song in List to one position
	 */
	public void upSong() {
		if (mp3ListModel != null) {
			int index = getSelectedIndex();
			if (index != 0) {
				changeSongPlace(index, index - 1);
				setSelectedIndex(index - 1);
			}
		}
	}

	/**
	 * Down song in List to one position
	 */
	public void downSong() {
		if (mp3ListModel != null) {
			int index = getSelectedIndex();
			if (index != mp3ListModel.getSize() - 1) {
				changeSongPlace(index, index + 1);
				setSelectedIndex(index + 1);
			}
		}
	}

	// save list
	public void saveList(File saveFile) throws IOException {
		System.out.println(saveFile.getAbsolutePath());
		FileOutputStream fileWriteStream = new FileOutputStream(saveFile);
		String writeString;
		for (int i = 0; i < mp3ListModel.size(); i++) {
			writeString = mp3ListModel.getElementAt(i).getFilename() + "\r\n";
			fileWriteStream.write(writeString.getBytes());
		}
		fileWriteStream.flush();
		fileWriteStream.close();
	}

	public void loadList(File loadFile) {
		BufferedReader inputFile = null;
		try {
			inputFile = new BufferedReader(new FileReader(loadFile));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			System.out.println("Can't load PlayList");
			return;
		}

		mp3ListModel.clear();

		String addFileName = null;
		try {
			addFileName = inputFile.readLine();
		} catch (IOException e1) {
			e1.printStackTrace();
			System.out.println("Error reading PlayListFile");

		}
		while (addFileName != null) {
			try {
				//Test file for exist
				File addMp3File = new File(addFileName);
				if( addMp3File.exists() ){
					mp3ListModel.addElement(new Mp3File(addMp3File));
				}
			} catch (Exception e) {
				e.printStackTrace();
				System.out.println("Error adding songs from loadin PlayList "
						+ loadFile.getAbsolutePath());
			}
			try {
				addFileName = inputFile.readLine();
			} catch (IOException e1) {
				e1.printStackTrace();
				System.out.println("Error reading PlayListFile");

			}
		}
		try {
			inputFile.close();
			// Set selected index to zero if list not emty
			if (!mp3ListModel.isEmpty()) {
				setSelectedIndex(0);
			}
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("Error closing PlayListFile");
		}
	}

	// add songs from array
	public void addSongs(File[] songFiles) throws Exception {
		for (File song : songFiles) {
			try {
				addSong(song);
			} catch (Exception e) {
				e.printStackTrace();
				throw e;
			}
		}
	}

	// delete selected song
	public void delSong() {
		int delIndex = getSelectedIndex();
		if (delIndex == -1) {
			JOptionPane.showMessageDialog(getTopLevelAncestor(),
					"Where is no song selected!!!");
			return;
		}
		mp3ListModel.remove(delIndex);
	}
}
