package grovpackage;

import java.awt.Color;
import java.awt.Component;

import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.ListCellRenderer;

import com.mpatric.mp3agic.Mp3File;

public class PlayListCellRenderer extends JLabel implements ListCellRenderer<Mp3File> {

	@Override
	public Component getListCellRendererComponent(
			JList<? extends Mp3File> list, Mp3File mp3File, int index,
			boolean isSelected, boolean cellHasFocus) {
		// TODO Auto-generated method stub
		setText(mp3File.getFilename());
		if( isSelected ){
			setOpaque(true);
			setBackground(Color.yellow);
			setForeground(Color.blue);
		}else{
			setBackground(Color.white);
			setForeground(Color.black);
		}
		return this;
	}
	/**
	 * �������� ������ 
	 */
	private static final long serialVersionUID = 1L;

}
