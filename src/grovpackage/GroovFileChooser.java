package grovpackage;

import java.awt.Component;
import java.awt.HeadlessException;
import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;

public class GroovFileChooser extends JFileChooser {

	
	/**
	 * For adding songs and open save lists
	 */
	private static final long serialVersionUID = 1L;
	private final static String BASE_DIR = "D:\\Elf\\Music\\";
	private final static String[] SONG_EXTENSIONS = { "mp3" };
	private final static String[] PLAYLIST_EXTENSIONS = { "m3u" };
	private final static String SONG_DESCR = "Songs in mp3 format";
	private final static String PLAYLIST_DESCR = "PlayList file";

	// add song true if false when it signed i want to add or save playlist
	private boolean openSong;

	public GroovFileChooser(boolean openSong) {
		super();
		this.openSong = openSong;
		this.setAcceptAllFileFilterUsed(false);
		this.setCurrentDirectory(new File(BASE_DIR));
	}

	@Override
	public File getSelectedFile() {
		File selectedFile = super.getSelectedFile();
		if( getFileFilter() instanceof FileFilter ){
			String[] extensions = ((FileNameExtensionFilter) getFileFilter()).getExtensions();
			for( String ext:extensions){
				if( selectedFile.getAbsolutePath().toUpperCase().endsWith("." + ext.toUpperCase()) ){
					return selectedFile;
				}
			}
			return new File(selectedFile.getAbsolutePath() + "." + ((extensions.length > 0)?extensions[0]:""));
		}else{
			return selectedFile;
		}
	}

	@Override
	public int showOpenDialog(Component parent) throws HeadlessException {
		if (openSong) {
			super.setMultiSelectionEnabled(true);
			super.setFileFilter(new FileNameExtensionFilter(SONG_DESCR,
					SONG_EXTENSIONS));
		} else {
			super.setMultiSelectionEnabled(false);
			super.setFileFilter(new FileNameExtensionFilter(PLAYLIST_DESCR,
					PLAYLIST_EXTENSIONS));
		}
		return super.showOpenDialog(parent);
	}

	@Override
	public int showSaveDialog(Component parent) throws HeadlessException {
		super.setMultiSelectionEnabled(false);
		super.setFileFilter(new FileNameExtensionFilter(PLAYLIST_DESCR,
				PLAYLIST_EXTENSIONS));
		return super.showSaveDialog(parent);
	}

}
