package grovpackage;

import java.awt.BorderLayout;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.beans.EventHandler;
import java.io.File;
import java.io.IOException;
import java.util.logging.*;

import javax.swing.ActionMap;
import javax.swing.ImageIcon;
import javax.swing.InputMap;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JSlider;
import javax.swing.JToggleButton;
import javax.swing.KeyStroke;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.border.EtchedBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import javazoom.jlgui.basicplayer.BasicPlayerException;

class wndMainGroov extends JFrame {

	private static final long serialVersionUID = 1L;
	private int lastVolumePosition = GroooovPlayer.SATRT_VOLUME;
	private GroooovPlayer soundPlayer = null;
	private JFrame mainFrame;
	private final int MOVE_MS_FORWARD = 5000;
	private final int MOVE_MS_REWIND = 5000;

	/**
	 * Create the application.
	 */
	public wndMainGroov() {
		initialize();
		mainFrame = this;
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {

		// main frame
		setTitle("MyGroooove");
		getContentPane().setForeground(
				UIManager.getColor("InternalFrame.activeTitleGradient"));
		getContentPane().setLayout(new BorderLayout(0, 5));

		// List of songs
		PlayList lstListOfSongs = new PlayList();
		lstListOfSongs.setCellRenderer(new PlayListCellRenderer());
		lstListOfSongs.setSelectedIndex(0);

		// Player object
		soundPlayer = new GroooovPlayer(lstListOfSongs);
		JPanel pnlList = new JPanel();
		pnlList.setAlignmentY(0.0f);
		pnlList.setAlignmentX(0.0f);
		pnlList.setSize(new Dimension(450, 300));
		pnlList.setPreferredSize(new Dimension(200, 200));

		getContentPane().add(pnlList);
		GridBagLayout gbl_pnlList = new GridBagLayout();
		gbl_pnlList.columnWidths = new int[] { 48, 30, 30, 30, 30, 30, 0 };
		gbl_pnlList.rowHeights = new int[] { 0, 0, 125, 12, 10 };
		gbl_pnlList.columnWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0,
				0.0, 1.0, 1.0 };
		gbl_pnlList.rowWeights = new double[] { 0.0, 1.0, 1.0, 1.0,
				Double.MIN_VALUE };
		pnlList.setLayout(gbl_pnlList);

		// Panel with play, pause, stop etc buttons
		JPanel pnlPlay = new JPanel();
		getContentPane().add(pnlPlay, BorderLayout.NORTH);
		GridBagLayout gbl_pnlPlay = new GridBagLayout();
		gbl_pnlPlay.columnWidths = new int[] { 48, 48, 48, 48, 48, 48, 48, 48,
				0 };
		gbl_pnlPlay.rowHeights = new int[] { 25, 0, 0 };
		gbl_pnlPlay.columnWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0,
				0.0, 0.0, 0.0, Double.MIN_VALUE };
		gbl_pnlPlay.rowWeights = new double[] { 0.0, 1.0, Double.MIN_VALUE };
		pnlPlay.setLayout(gbl_pnlPlay);

		// hot keys combinations
		InputMap imap = pnlPlay.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
		imap.put(KeyStroke.getKeyStroke("ctrl G"), "soundPlayer.playSong");
		imap.put(KeyStroke.getKeyStroke("ctrl W"), "soundPlayer.pauseSong");
		imap.put(KeyStroke.getKeyStroke("ctrl S"), "soundPlayer.stopSong");
		imap.put(KeyStroke.getKeyStroke("ctrl F"), "soundPlayer.forward");
		imap.put(KeyStroke.getKeyStroke("ctrl B"), "soundPlayer.rewind");
		imap.put(KeyStroke.getKeyStroke("ctrl N"), "soundPlayer.playNext");
		imap.put(KeyStroke.getKeyStroke("ctrl P"), "soundPlayer.playPrev");

		PlayerButtonsAction playAction = new PlayerButtonsAction("Play",
				soundPlayer, new ImageIcon("res\\images\\Play.png"),
				"Play (Ctrl+g)");

		PlayerButtonsAction pauseAction = new PlayerButtonsAction("Pause",
				soundPlayer, new ImageIcon("res\\images\\Pause-icon.png"),
				"Pause (Ctrl+w)");

		PlayerButtonsAction stopAction = new PlayerButtonsAction("Stop",
				soundPlayer, new ImageIcon("res\\images\\Stop-red-icon.png"),
				"Stop (Ctrl+s)");

		PlayerButtonsAction forwardAction = new PlayerButtonsAction("Forward",
				soundPlayer, new ImageIcon("res\\images\\forward.png"),
				"Forward");

		PlayerButtonsAction rewindAction = new PlayerButtonsAction("Rewind",
				soundPlayer, new ImageIcon("res\\images\\rewind.png"),
				"Rewind");

		PlayerButtonsAction nextAction = new PlayerButtonsAction("Next",
				soundPlayer, new ImageIcon("res\\images\\next-icon.png"),
				"Next song (Ctrl+n)");

		PlayerButtonsAction previousAction = new PlayerButtonsAction(
				"Previous", soundPlayer, new ImageIcon(
						"res\\images\\prev-icon.png"), "Previos song (Ctrl+p)");

		ActionMap amap = pnlPlay.getActionMap();
		amap.put("soundPlayer.playSong", playAction);
		amap.put("soundPlayer.pauseSong", pauseAction);
		amap.put("soundPlayer.stopSong", stopAction);
		amap.put("soundPlayer.forward", forwardAction);
		amap.put("soundPlayer.rewind", rewindAction);
		amap.put("soundPlayer.playNext", nextAction);
		amap.put("soundPlayer.playPrev", previousAction);

		JPopupMenu contextMenu = new JPopupMenu();
		contextMenu.add(playAction);
		contextMenu.add(pauseAction);
		contextMenu.add(stopAction);

		lstListOfSongs.setComponentPopupMenu(contextMenu);

		lstListOfSongs.addMouseListener(new MouseAdapter() {

			@Override
			public void mouseClicked(MouseEvent e) {
				if (e.getClickCount() >= 2) {
					int index = lstListOfSongs.locationToIndex(e.getPoint());
					lstListOfSongs.setSelectedIndex(index);
					soundPlayer.playSong();
				}
			}

		});

		JButton btnPlay = new JButton(playAction);
		btnPlay.setText("");
		GridBagConstraints gbc_btnPlay = new GridBagConstraints();
		gbc_btnPlay.weightx = 0.125;
		gbc_btnPlay.anchor = GridBagConstraints.NORTHWEST;
		gbc_btnPlay.insets = new Insets(0, 0, 5, 5);
		gbc_btnPlay.gridx = 0;
		gbc_btnPlay.gridy = 0;
		btnPlay.setHorizontalAlignment(SwingConstants.LEFT);
		btnPlay.setVerticalAlignment(SwingConstants.TOP);
		pnlPlay.add(btnPlay, gbc_btnPlay);

		JButton btnPause = new JButton(pauseAction);
		btnPause.setText("");
		GridBagConstraints gbc_btnPause = new GridBagConstraints();
		gbc_btnPause.weightx = 0.125;
		gbc_btnPause.anchor = GridBagConstraints.NORTHWEST;
		gbc_btnPause.insets = new Insets(0, 0, 5, 5);
		gbc_btnPause.gridx = 1;
		gbc_btnPause.gridy = 0;
		pnlPlay.add(btnPause, gbc_btnPause);

		JButton btnStop = new JButton(stopAction);
		btnStop.setText("");
		GridBagConstraints gbc_btnStop = new GridBagConstraints();
		gbc_btnStop.weightx = 0.125;
		gbc_btnStop.anchor = GridBagConstraints.NORTHWEST;
		gbc_btnStop.insets = new Insets(0, 0, 5, 5);
		gbc_btnStop.gridx = 2;
		gbc_btnStop.gridy = 0;
		pnlPlay.add(btnStop, gbc_btnStop);

		JButton btnRewined = new JButton(rewindAction);
		btnRewined.setText("");
		GridBagConstraints gbc_btnRewined = new GridBagConstraints();
		gbc_btnRewined.weightx = 0.125;
		gbc_btnRewined.anchor = GridBagConstraints.NORTHWEST;
		gbc_btnRewined.insets = new Insets(0, 0, 5, 5);
		gbc_btnRewined.gridx = 3;
		gbc_btnRewined.gridy = 0;
		pnlPlay.add(btnRewined, gbc_btnRewined);

		JButton btnForward = new JButton(forwardAction);
		btnForward.setText("");
		GridBagConstraints gbc_btnForward = new GridBagConstraints();
		gbc_btnForward.weightx = 0.125;
		gbc_btnForward.anchor = GridBagConstraints.NORTHWEST;
		gbc_btnForward.insets = new Insets(0, 0, 5, 5);
		gbc_btnForward.gridx = 4;
		gbc_btnForward.gridy = 0;
		pnlPlay.add(btnForward, gbc_btnForward);

		JButton btnPrevious = new JButton(previousAction);
		btnPrevious.setText("");
		GridBagConstraints gbc_btnPrevious = new GridBagConstraints();
		gbc_btnPrevious.weightx = 0.125;
		gbc_btnPrevious.anchor = GridBagConstraints.NORTHWEST;
		gbc_btnPrevious.insets = new Insets(0, 0, 5, 5);
		gbc_btnPrevious.gridx = 5;
		gbc_btnPrevious.gridy = 0;
		pnlPlay.add(btnPrevious, gbc_btnPrevious);

		JButton btnNext = new JButton(nextAction);
		btnNext.setText("");
		GridBagConstraints gbc_btnNext = new GridBagConstraints();
		gbc_btnNext.weightx = 0.125;
		gbc_btnNext.anchor = GridBagConstraints.NORTHWEST;
		gbc_btnNext.insets = new Insets(0, 0, 5, 5);
		gbc_btnNext.gridx = 6;
		gbc_btnNext.gridy = 0;
		pnlPlay.add(btnNext, gbc_btnNext);

		JSlider sldrVolume = new JSlider();
		JToggleButton tgbtnMute = new JToggleButton("");

		sldrVolume.setOrientation(SwingConstants.VERTICAL);
		GridBagConstraints gbc_sldrVolume = new GridBagConstraints();
		gbc_sldrVolume.fill = GridBagConstraints.VERTICAL;
		gbc_sldrVolume.anchor = GridBagConstraints.NORTH;
		gbc_sldrVolume.gridheight = 3;
		gbc_sldrVolume.insets = new Insets(0, 0, 5, 0);
		gbc_sldrVolume.gridx = 7;
		gbc_sldrVolume.gridy = 0;
		pnlList.add(sldrVolume, gbc_sldrVolume);
		sldrVolume.setMaximum(GroooovPlayer.MAX_VOLUME);
		sldrVolume.setValue(GroooovPlayer.SATRT_VOLUME);
		sldrVolume.addChangeListener(new ChangeListener() {

			@Override
			public void stateChanged(ChangeEvent e) {
				soundPlayer.setVolume(sldrVolume.getValue());
				if (sldrVolume.getValue() == 0) {
					if (!tgbtnMute.isSelected())
						tgbtnMute.setSelected(true);
				} else {
					if (tgbtnMute.isSelected())
						tgbtnMute.setSelected(false);
					lastVolumePosition = sldrVolume.getValue();
				}
			}
		});

		tgbtnMute.setSelectedIcon(new ImageIcon(
				"C:\\Users\\Elf\\workspace\\Grooov\\res\\images\\mute.png"));
		tgbtnMute.setIcon(new ImageIcon(
				"C:\\Users\\Elf\\workspace\\Grooov\\res\\images\\speaker.png"));
		GridBagConstraints gbc_tgbtnMute = new GridBagConstraints();
		gbc_tgbtnMute.weightx = 0.125;
		gbc_tgbtnMute.insets = new Insets(0, 0, 5, 0);
		gbc_tgbtnMute.gridx = 7;
		gbc_tgbtnMute.gridy = 0;
		pnlPlay.add(tgbtnMute, gbc_tgbtnMute);
		tgbtnMute.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (tgbtnMute.isSelected()) {
					lastVolumePosition = sldrVolume.getValue();
					sldrVolume.setValue(0);
				} else {
					sldrVolume.setValue(lastVolumePosition);
					soundPlayer.setVolume(lastVolumePosition);
				}
			}
		});

		TimeTicLabel lblSongTime = new TimeTicLabel(0);
		GridBagConstraints gbc_lblSongTime = new GridBagConstraints();
		gbc_lblSongTime.insets = new Insets(0, 0, 5, 5);
		gbc_lblSongTime.gridx = 6;
		gbc_lblSongTime.gridy = 0;
		pnlList.add(lblSongTime, gbc_lblSongTime);

		MovingSlider sldrSongFlow = new MovingSlider();
		GridBagConstraints gbc_sldrSongFlow = new GridBagConstraints();
		gbc_sldrSongFlow.gridwidth = 6;
		gbc_sldrSongFlow.fill = GridBagConstraints.HORIZONTAL;
		gbc_sldrSongFlow.insets = new Insets(0, 0, 5, 5);
		gbc_sldrSongFlow.gridx = 0;
		gbc_sldrSongFlow.gridy = 0;
		sldrSongFlow.setValue(0);
		pnlList.add(sldrSongFlow, gbc_sldrSongFlow);
		sldrSongFlow.addMouseListener(new MouseAdapter() {

			@Override
			public void mousePressed(MouseEvent e) {
				if (soundPlayer.isPlaying()) {
					try {
						soundPlayer.pauseSong();
					} catch (BasicPlayerException e1) {
						e1.printStackTrace();
					}
				}
			}

			@Override
			public void mouseReleased(MouseEvent e) {
				if (soundPlayer.isPlaying()) {
					try {
						soundPlayer.seekSong(sldrSongFlow.getValue());
						soundPlayer.resumeSong();
					} catch (BasicPlayerException e1) {
						e1.printStackTrace();
					}
				}
			}

		});

		btnRewined.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (soundPlayer.isPlaying()) {
					long position = sldrSongFlow.getValue() - MOVE_MS_REWIND;
					position = position > 0 ? position : 0;
					try {
						soundPlayer.seekSong(position);
					} catch (BasicPlayerException e1) {
						e1.printStackTrace();
					}
				}
			}
		});

		btnForward.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (soundPlayer.isPlaying()) {
					long position = sldrSongFlow.getValue() + MOVE_MS_FORWARD;
					position = position < sldrSongFlow.getMaximum() ? position
							: sldrSongFlow.getMaximum();
					try {
						soundPlayer.pauseSong();
						soundPlayer.seekSong(position);
						soundPlayer.resumeSong();
					} catch (BasicPlayerException e1) {
						e1.printStackTrace();
					}
				}
			}
		});
		
		JScrollPane scrlListScroll = new JScrollPane();
		GridBagConstraints gbc_scrlListScroll = new GridBagConstraints();
		gbc_scrlListScroll.gridwidth = 7;
		gbc_scrlListScroll.insets = new Insets(0, 0, 5, 5);
		gbc_scrlListScroll.fill = GridBagConstraints.BOTH;
		gbc_scrlListScroll.gridx = 0;
		gbc_scrlListScroll.gridy = 2;
		pnlList.add(scrlListScroll, gbc_scrlListScroll);
		scrlListScroll.setViewportView(lstListOfSongs);

		JButton btnSaveList = new JButton("");
		btnSaveList
				.setToolTipText("Save List (\u0417\u0430\u043F\u0438\u0441\u0430\u0442\u044C \u043F\u043B\u0430\u0439\u043B\u0438\u0441\u0442)");
		btnSaveList.setIcon(new ImageIcon(
				"C:\\Users\\Elf\\workspace\\Grooov\\res\\images\\save_16.png"));
		GridBagConstraints gbc_btnSaveList = new GridBagConstraints();
		gbc_btnSaveList.insets = new Insets(0, 0, 0, 5);
		gbc_btnSaveList.gridx = 0;
		gbc_btnSaveList.gridy = 3;
		pnlList.add(btnSaveList, gbc_btnSaveList);
		btnSaveList.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				GroovFileChooser saveChooser = new GroovFileChooser(false);
				if (saveChooser.showSaveDialog((JButton) e.getSource()) == JFileChooser.APPROVE_OPTION) {
					try {

						lstListOfSongs.saveList(saveChooser.getSelectedFile());
					} catch (Exception e1) {
						e1.printStackTrace();
						System.out.println("Error saving PlayList");
					}
				}
			}
		});

		JButton btnLoadList = new JButton("");
		btnLoadList
				.setToolTipText("Load Playlist (\u0417\u0430\u0433\u0440\u0443\u0437\u0438\u0442\u044C \u043F\u043B\u044D\u0439\u043B\u0438\u0441\u0442)");
		btnLoadList
				.setIcon(new ImageIcon(
						"C:\\Users\\Elf\\workspace\\Grooov\\res\\images\\open-icon.png"));
		GridBagConstraints gbc_btnLoadList = new GridBagConstraints();
		gbc_btnLoadList.insets = new Insets(0, 0, 0, 5);
		gbc_btnLoadList.gridx = 1;
		gbc_btnLoadList.gridy = 3;
		pnlList.add(btnLoadList, gbc_btnLoadList);
		btnLoadList.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				GroovFileChooser loadChooser = new GroovFileChooser(false);
				if (loadChooser.showOpenDialog((JButton) e.getSource()) == JFileChooser.APPROVE_OPTION) {
					try {
						lstListOfSongs.loadList(loadChooser.getSelectedFile());
					} catch (Exception e1) {
						e1.printStackTrace();
						System.out.println("Error loading PlayList");
					}
				}
			}
		});

		// add songs button
		JButton btnAddSong = new JButton("");
		btnAddSong
				.setToolTipText("Add Item (\u0414\u043E\u0431\u0430\u0432\u0438\u0442\u044C \u043F\u0435\u0441\u043D\u044E)");
		btnAddSong.setIcon(new ImageIcon(
				"C:\\Users\\Elf\\workspace\\Grooov\\res\\images\\plus_16.png"));
		GridBagConstraints gbc_btnAddSong = new GridBagConstraints();
		gbc_btnAddSong.insets = new Insets(0, 0, 0, 5);
		gbc_btnAddSong.gridx = 2;
		gbc_btnAddSong.gridy = 3;
		pnlList.add(btnAddSong, gbc_btnAddSong);
		btnAddSong.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				GroovFileChooser addChooser = new GroovFileChooser(true);
				if (addChooser.showOpenDialog((JButton) e.getSource()) == JFileChooser.APPROVE_OPTION) {
					try {
						lstListOfSongs.addSongs(addChooser.getSelectedFiles());
					} catch (Exception e1) {
						e1.printStackTrace();
						System.out.println("Error addin files to List");
					}
				}
			}
		});

		JButton btnDelSong = new JButton("");
		btnDelSong.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// delete song
				((PlayList) lstListOfSongs).delSong();
			}
		});

		btnDelSong
				.setToolTipText("Del Item (\u0423\u0434\u0430\u043B\u0438\u0442\u044C \u043F\u0435\u0441\u043D\u044E)");
		btnDelSong
				.setIcon(new ImageIcon(
						"C:\\Users\\Elf\\workspace\\Grooov\\res\\images\\remove_icon.png"));
		GridBagConstraints gbc_btnDelSong = new GridBagConstraints();
		gbc_btnDelSong.insets = new Insets(0, 0, 0, 5);
		gbc_btnDelSong.gridx = 3;
		gbc_btnDelSong.gridy = 3;
		pnlList.add(btnDelSong, gbc_btnDelSong);

		JButton btnDownSongInList = new JButton("");
		btnDownSongInList
				.setToolTipText("Song Up (\u041F\u0435\u0440\u0435\u043C\u0435\u0441\u0442\u0438\u0442\u044C \u043F\u0435\u0441\u043D\u044E \u0432\u0432\u0435\u0440\u0445 \u0432 \u0441\u043F\u0438\u0441\u043A\u0435)");
		btnDownSongInList
				.setIcon(new ImageIcon(
						"C:\\Users\\Elf\\workspace\\Grooov\\res\\images\\arrow-down-icon.png"));
		GridBagConstraints gbc_btnDownSongInList = new GridBagConstraints();
		gbc_btnDownSongInList.insets = new Insets(0, 0, 0, 5);
		gbc_btnDownSongInList.gridx = 4;
		gbc_btnDownSongInList.gridy = 3;
		pnlList.add(btnDownSongInList, gbc_btnDownSongInList);

		JButton btnUpSongInList = new JButton("");
		btnUpSongInList
				.setIcon(new ImageIcon(
						"C:\\Users\\Elf\\workspace\\Grooov\\res\\images\\arrow-up-icon.png"));
		btnUpSongInList
				.setToolTipText("Up Song (\u041F\u043E\u0434\u043D\u044F\u0442\u044C \u043A\u043E\u043C\u043F\u043E\u0437\u0438\u0446\u0438\u044E \u0432 \u0441\u043F\u0438\u0441\u043A\u0435)");
		GridBagConstraints gbc_btnUpSongInList = new GridBagConstraints();
		gbc_btnUpSongInList.insets = new Insets(0, 0, 0, 5);
		gbc_btnUpSongInList.gridx = 5;
		gbc_btnUpSongInList.gridy = 3;
		pnlList.add(btnUpSongInList, gbc_btnUpSongInList);

		btnDownSongInList.addActionListener(EventHandler.create(
				ActionListener.class, lstListOfSongs, "downSong"));
		btnUpSongInList.addActionListener(EventHandler.create(
				ActionListener.class, lstListOfSongs, "upSong"));

		JPanel pnlSongName = new JPanel();
		pnlSongName.setBorder(new EtchedBorder(EtchedBorder.LOWERED, new Color(
				0, 255, 0), new Color(0, 255, 127)));
		getContentPane().add(pnlSongName, BorderLayout.SOUTH);

		MoveTextLabel lblSongName = new MoveTextLabel(
				"Welcome to the Grooooooooov!!!");
		lblSongName.setMinimumSize(new Dimension(188, 20));
		lblSongName.setMaximumSize(new Dimension(188, 20));
		lblSongName.setBounds(new Rectangle(0, 0, 450, 20));
		pnlSongName.add(lblSongName);
		lblSongName.setPreferredSize(new Dimension(400, 20));
		lblSongName.setForeground(new Color(30, 144, 255));
		lblSongName.setAlignmentY(0.0f);
		lblSongName.setAutoscrolls(true);
		lblSongName.setFont(new Font("Tahoma", Font.ITALIC, 14));
		// Add observers to sound player for managing GUI and song duration
		PlayerGUIManager manager = PlayerGUIManager.getInstance();
		manager.addObserver(lblSongName);
		manager.addObserver(lblSongTime);
		manager.addObserver(sldrSongFlow);

		JButton btnSkin = new JButton("Skin");
		btnSkin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JDialog changeLook = new SkinChangeDialog(mainFrame);
				changeLook.setVisible(true);
			}
		});

		GridBagConstraints gbc_btnSkin = new GridBagConstraints();
		gbc_btnSkin.insets = new Insets(0, 0, 0, 5);
		gbc_btnSkin.gridx = 6;
		gbc_btnSkin.gridy = 3;
		pnlList.add(btnSkin, gbc_btnSkin);

		addWindowListener(new WindowAdapter() {

			@Override
			public void windowClosing(WindowEvent e) {
				//saving current songs to default play List default.m3u
				try {
					lstListOfSongs.saveList(new File("default.m3u"));
				} catch (IOException e1) {
					e1.printStackTrace();
					Logger.getGlobal().log(Level.SEVERE, "Error creating default list on windows closing");
				}
			}
			
		});
		
		//loading default play list then starting
		lstListOfSongs.loadList(new File("default.m3u"));
		
		manager.addObserver(soundPlayer);
		soundPlayer.setManagerGUI(manager);

		setForeground(UIManager.getColor("InternalFrame.activeTitleGradient"));
		setBounds(100, 100, 428, 321);
		pack();
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
}
