package dynamiclabelimpl;

import java.awt.Font;
import java.awt.Toolkit;

import javax.swing.JLabel;
import javax.swing.SwingConstants;

import dynamiclabel.DynamicLabel;

public class LabelTextMover implements DynamicLabel {

	int countOfCharsInLabel = -1;
	int outStringPosition = 0;

	@Override
	public String change(JLabel targetLabel) {
		if (countOfCharsInLabel == -1) {
			// calculate pixels per char
			Font font = targetLabel.getFont();
			int fontSize = font.getSize();

			Toolkit kit = Toolkit.getDefaultToolkit();
			int screenResolution = kit.getScreenResolution();

			int charResolution = Math.round((screenResolution / 72F) * fontSize);
			int labelWidth = targetLabel.getSize().width;
			countOfCharsInLabel = labelWidth / charResolution;
			System.out.println(fontSize + "|" + screenResolution + "|" + labelWidth
					+ "|" + countOfCharsInLabel);
		}
		
		String outString;
		String labelText = targetLabel.getText();
		if (outStringPosition <= countOfCharsInLabel) {
			// Begin of string moving through label
			outString = labelText.substring(0, outStringPosition);
			targetLabel.setHorizontalAlignment(SwingConstants.LEFT);
		} else if ((labelText.length() - outStringPosition) <= countOfCharsInLabel) {
			// End of string moving through label
			outString = labelText.substring(outStringPosition);
			targetLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		} else {
			// Middle of string moving through label
			outString = labelText.substring(outStringPosition,
					outStringPosition + countOfCharsInLabel);
		}
		if (outStringPosition >= labelText.length()) {
			outStringPosition = 0;
		} else {
			outStringPosition++;
		}
		targetLabel.setText(outString);
		System.out.println(outString);
		return outString;
	}
}
