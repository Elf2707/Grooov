package grovpackage;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.LookAndFeel;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.plaf.metal.MetalLookAndFeel;
import javax.swing.plaf.nimbus.NimbusLookAndFeel;

import com.sun.java.swing.plaf.motif.MotifLookAndFeel;
import com.sun.java.swing.plaf.windows.WindowsClassicLookAndFeel;
import com.sun.java.swing.plaf.windows.WindowsLookAndFeel;

public class SkinChangeDialog extends JDialog {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final JPanel contentPanel = new JPanel();
	private final ButtonGroup chooseLookButtonGroup = new ButtonGroup();
	private LookAndFeel oldLookAndFeel;

	/**
	 * Create the dialog.
	 */
	public SkinChangeDialog(JFrame pearent) {
		super(pearent, "Change Grooooove Skins Window", true);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);

		setBounds(100, 100, 450, 300);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));

		getContentPane().add(contentPanel, BorderLayout.CENTER);

		JRadioButton rdbtnWindowsLook = new JRadioButton("WindowsLook");
		rdbtnWindowsLook.setActionCommand("windowslook");
		JRadioButton rdbtnMetalLook = new JRadioButton("MetalLook");
		rdbtnMetalLook.setActionCommand("metallook");
		JRadioButton rdbtnNimbusLook = new JRadioButton("NibusLook");
		rdbtnNimbusLook.setActionCommand("nimbus");
		JRadioButton rdbtnMotifLook = new JRadioButton("MotifLook");
		rdbtnMotifLook.setActionCommand("motiflook");
		JRadioButton rdbtnWindowsclassicLook = new JRadioButton(
				"WindowsClassicLook");
		rdbtnWindowsclassicLook.setActionCommand("windowsclassicklook");
		chooseLookButtonGroup.add(rdbtnWindowsLook);
		chooseLookButtonGroup.add(rdbtnMetalLook);
		chooseLookButtonGroup.add(rdbtnNimbusLook);
		chooseLookButtonGroup.add(rdbtnWindowsclassicLook);
		chooseLookButtonGroup.add(rdbtnMotifLook);

		ActionListener buttonsCationListener = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				oldLookAndFeel = UIManager.getLookAndFeel();
				LookAndFeel newLookAndFeel;
				String command = e.getActionCommand();
				switch (command) {
				case "windowslook":
					newLookAndFeel = new WindowsLookAndFeel();
					break;
				case "metallook":
					newLookAndFeel = new MetalLookAndFeel();
					break;
				case "nimbus":
					newLookAndFeel = new NimbusLookAndFeel();
					break;
				case "motiflook":
					newLookAndFeel = new MotifLookAndFeel();
					break;
				case "windowsclassicklook":
					newLookAndFeel = new WindowsClassicLookAndFeel();
					break;
				default:
					newLookAndFeel = oldLookAndFeel;
					break;
				}

				try {
					UIManager.setLookAndFeel(newLookAndFeel);
					SwingUtilities.updateComponentTreeUI((JFrame) getParent());
				} catch (UnsupportedLookAndFeelException e1) {
					e1.printStackTrace();
				}
			}
		};

		rdbtnWindowsLook.addActionListener(buttonsCationListener);
		rdbtnMetalLook.addActionListener(buttonsCationListener);
		rdbtnNimbusLook.addActionListener(buttonsCationListener);
		rdbtnWindowsclassicLook.addActionListener(buttonsCationListener);
		rdbtnWindowsLook.addActionListener(buttonsCationListener);

		contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
		contentPanel.add(rdbtnWindowsLook);
		contentPanel.add(rdbtnMetalLook);
		contentPanel.add(rdbtnNimbusLook);
		contentPanel.add(rdbtnWindowsclassicLook);
		contentPanel.add(rdbtnMotifLook);

		Border titled = BorderFactory.createTitledBorder(
				BorderFactory.createEtchedBorder(), "Choose skin");
		contentPanel.setBorder(titled);

		JPanel buttonPane = new JPanel();
		buttonPane.setLayout(new FlowLayout(FlowLayout.CENTER));
		getContentPane().add(buttonPane, BorderLayout.SOUTH);
		{
			JButton okButton = new JButton("OK");
			okButton.setActionCommand("OK");
			okButton.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					setVisible(false);
				}
			});
			buttonPane.add(okButton);
			getRootPane().setDefaultButton(okButton);
		}
		{
			JButton cancelButton = new JButton("Cancel");
			cancelButton.setActionCommand("Cancel");
			cancelButton.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					try {
						UIManager.setLookAndFeel(oldLookAndFeel);
						SwingUtilities
								.updateComponentTreeUI((JFrame) getParent());
					} catch (UnsupportedLookAndFeelException e1) {
						e1.printStackTrace();
					}
					setVisible(false);

				}
			});
			buttonPane.add(cancelButton);
		}
	}
}
