package grovpackage;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.EventHandler;

import javax.swing.ActionMap;
import javax.swing.ImageIcon;
import javax.swing.InputMap;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JToggleButton;
import javax.swing.KeyStroke;
import javax.swing.SwingConstants;
import javax.swing.Timer;
import javax.swing.UIManager;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.JScrollPane;

import com.sun.org.apache.bcel.internal.generic.LSTORE;

import dynamiclabelimpl.LabelTextMover;

class wndMainGroov extends JFrame {

	private static final long serialVersionUID = 1L;
	private int lastVolumePosition = GroooovPlayer.SATRT_VOLUME;
	private static int TIMER_DELAY = 1000; //milliseconds
	private GroooovPlayer soundPlayer = null;
    Timer playTimer = null;
    MoveTextLable lblSongName;
    
	

	/**
	 * Create the application.
	 */
	public wndMainGroov() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		
		
		//main frame
		setTitle("MyGroooove");
		getContentPane().setForeground(UIManager.getColor("InternalFrame.activeTitleGradient"));
		getContentPane().setLayout(new BorderLayout(0, 5));
		

		//List of songs
		PlayList lstListOfSongs = new PlayList();
		lstListOfSongs.setCellRenderer(new PlayListCellRenderer());
		lstListOfSongs.setSelectedIndex(0);
			
		//Player object
		soundPlayer = new GroooovPlayer(lstListOfSongs);
		JPanel pnlList = new JPanel();
		
		getContentPane().add(pnlList);
		GridBagLayout gbl_pnlList = new GridBagLayout();
		gbl_pnlList.columnWidths = new int[] {30, 30, 30, 30, 30, 30, 0};
		gbl_pnlList.rowHeights = new int[] {0, 12, 112, 0, 10};
		gbl_pnlList.columnWeights = new double[]{1.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, 1.0};
		gbl_pnlList.rowWeights = new double[]{0.0, 0.0, 1.0, 1.0, Double.MIN_VALUE};
		pnlList.setLayout(gbl_pnlList);
		
		//Panel with play, pause, stop etc buttons
		JPanel pnlPlay = new JPanel();
		getContentPane().add(pnlPlay, BorderLayout.NORTH);
		GridBagLayout gbl_pnlPlay = new GridBagLayout();
		gbl_pnlPlay.columnWidths = new int[]{49, 49, 49, 49, 49, 49, 49, 49, 0};
		gbl_pnlPlay.rowHeights = new int[]{25, 0};
		gbl_pnlPlay.columnWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		gbl_pnlPlay.rowWeights = new double[]{0.0, Double.MIN_VALUE};
		pnlPlay.setLayout(gbl_pnlPlay);
		
		//hot keys combinations
		InputMap imap = pnlPlay.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW); 
		imap.put(KeyStroke.getKeyStroke("ctrl g"), "soundPlayer.play");
		imap.put(KeyStroke.getKeyStroke("ctrl w"), "soundPlayer.pause");
		imap.put(KeyStroke.getKeyStroke("ctrl s"), "soundPlayer.stop");
		imap.put(KeyStroke.getKeyStroke("ctrl f"), "soundPlayer.forward");
		imap.put(KeyStroke.getKeyStroke("ctrl b"), "soundPlayer.backward");
		imap.put(KeyStroke.getKeyStroke("ctrl n"), "soundPlayer.playNext");
		imap.put(KeyStroke.getKeyStroke("ctrl p"), "soundPlayer.playPrev");
		
		PlayerButtonsAction playAction = new PlayerButtonsAction("Play", soundPlayer, 
				                                new ImageIcon("res\\images\\Play.png"), "Play (Ctrl+g)");
		
		PlayerButtonsAction pauseAction = new PlayerButtonsAction("Pause", soundPlayer, 
                                        new ImageIcon("res\\images\\Pause-icon.png"), "Pause (Ctrl+w)");
		
		PlayerButtonsAction stopAction = new PlayerButtonsAction("Stop", soundPlayer, 
                                         new ImageIcon("res\\images\\Stop-red-icon.png"), "Stop (Ctrl+s)");
		
		PlayerButtonsAction forwardAction = new PlayerButtonsAction("Forward", soundPlayer, 
                                          new ImageIcon("res\\images\\forward.png"), "Forward (Ctrl+f)");
	
		PlayerButtonsAction rewindAction = new PlayerButtonsAction("Rewind", soundPlayer, 
                                            new ImageIcon("res\\images\\rewind.png"), "Rewind (Ctrl+b)");
	
		PlayerButtonsAction nextAction = new PlayerButtonsAction("Next", soundPlayer, 
                                  new ImageIcon("res\\images\\next-icon.png"), "Next song (Ctrl+n)");
	
		PlayerButtonsAction previousAction = new PlayerButtonsAction("Previous", soundPlayer, 
                                   new ImageIcon("res\\images\\prev-icon.png"), "Previos song (Ctrl+p)");
	
		ActionMap amap = pnlPlay.getActionMap();
		amap.put("soundPlayer.play", playAction);
		amap.put("soundPlayer.pause", pauseAction);
		amap.put("soundPlayer.stop", stopAction);
		amap.put("soundPlayer.forward", forwardAction);
		amap.put("soundPlayer.rewind", rewindAction);
		amap.put("soundPlayer.playNext", nextAction);
		amap.put("soundPlayer.playPrev", previousAction);
		
		JButton btnPlay = new JButton(playAction);
		btnPlay.setText("");
		GridBagConstraints gbc_btnPlay = new GridBagConstraints();
		gbc_btnPlay.anchor = GridBagConstraints.NORTHWEST;
		gbc_btnPlay.insets = new Insets(0, 0, 0, 5);
		gbc_btnPlay.gridx = 0;
		gbc_btnPlay.gridy = 0;
		btnPlay.setHorizontalAlignment(SwingConstants.LEFT);
		btnPlay.setVerticalAlignment(SwingConstants.TOP);
		pnlPlay.add(btnPlay, gbc_btnPlay);
		btnPlay.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				lblSongName.setText("Dasdasmdkmasl askmdakmdka");
				lblSongName.start();
			}
		});
				
		JButton btnPause = new JButton(pauseAction);
		btnPause.setText("");
		GridBagConstraints gbc_btnPause = new GridBagConstraints();
		gbc_btnPause.anchor = GridBagConstraints.NORTHWEST;
		gbc_btnPause.insets = new Insets(0, 0, 0, 5);
		gbc_btnPause.gridx = 1;
		gbc_btnPause.gridy = 0;
		pnlPlay.add(btnPause, gbc_btnPause);
		
		JButton btnStop = new JButton(stopAction);
		btnStop.setText("");
		GridBagConstraints gbc_btnStop = new GridBagConstraints();
		gbc_btnStop.anchor = GridBagConstraints.NORTHWEST;
		gbc_btnStop.insets = new Insets(0, 0, 0, 5);
		gbc_btnStop.gridx = 2;
		gbc_btnStop.gridy = 0;
		pnlPlay.add(btnStop, gbc_btnStop);
		
		JButton btnRewined = new JButton(rewindAction);
		btnRewined.setText("");
		GridBagConstraints gbc_btnRewined = new GridBagConstraints();
		gbc_btnRewined.anchor = GridBagConstraints.NORTHWEST;
		gbc_btnRewined.insets = new Insets(0, 0, 0, 5);
		gbc_btnRewined.gridx = 3;
		gbc_btnRewined.gridy = 0;
		pnlPlay.add(btnRewined, gbc_btnRewined);
		
		JButton btnForward = new JButton(forwardAction);
		btnForward.setText("");
		GridBagConstraints gbc_btnForward = new GridBagConstraints();
		gbc_btnForward.anchor = GridBagConstraints.NORTHWEST;
		gbc_btnForward.insets = new Insets(0, 0, 0, 5);
		gbc_btnForward.gridx = 4;
		gbc_btnForward.gridy = 0;
		pnlPlay.add(btnForward, gbc_btnForward);
		
		JButton btnPrevious = new JButton(previousAction);
		btnPrevious.setText("");
		GridBagConstraints gbc_btnPrevious = new GridBagConstraints();
		gbc_btnPrevious.anchor = GridBagConstraints.NORTHWEST;
		gbc_btnPrevious.insets = new Insets(0, 0, 0, 5);
		gbc_btnPrevious.gridx = 5;
		gbc_btnPrevious.gridy = 0;
		pnlPlay.add(btnPrevious, gbc_btnPrevious);
		
		JButton btnNext = new JButton(nextAction);
		btnNext.setText("");
		GridBagConstraints gbc_btnNext = new GridBagConstraints();
		gbc_btnNext.anchor = GridBagConstraints.NORTHWEST;
		gbc_btnNext.insets = new Insets(0, 0, 0, 5);
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
			    if(sldrVolume.getValue() == 0){
					if(!tgbtnMute.isSelected()) tgbtnMute.setSelected(true);
				}else{
					if(tgbtnMute.isSelected()) tgbtnMute.setSelected(false);
					lastVolumePosition = sldrVolume.getValue();
				}
			}
		});
		
		tgbtnMute.setSelectedIcon(new ImageIcon("C:\\Users\\Elf\\workspace\\Grooov\\res\\images\\mute.png"));
		tgbtnMute.setIcon(new ImageIcon("C:\\Users\\Elf\\workspace\\Grooov\\res\\images\\speaker.png"));
		GridBagConstraints gbc_tgbtnMute = new GridBagConstraints();
		gbc_tgbtnMute.gridx = 7;
		gbc_tgbtnMute.gridy = 0;
		pnlPlay.add(tgbtnMute, gbc_tgbtnMute);
		tgbtnMute.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
			    if(tgbtnMute.isSelected()){
			    	lastVolumePosition = sldrVolume.getValue();
                	sldrVolume.setValue(0);
                }else{
                	sldrVolume.setValue( lastVolumePosition );
                	soundPlayer.setVolume(lastVolumePosition);
                }
			}
		});
		
			
		JSlider sldrSongFlow = new JSlider();
		GridBagConstraints gbc_sldrSongFlow = new GridBagConstraints();
		gbc_sldrSongFlow.gridwidth = 6;
		gbc_sldrSongFlow.fill = GridBagConstraints.HORIZONTAL;
		gbc_sldrSongFlow.insets = new Insets(0, 0, 5, 5);
		gbc_sldrSongFlow.gridx = 0;
		gbc_sldrSongFlow.gridy = 0;
		sldrSongFlow.setValue(0);
		pnlList.add(sldrSongFlow, gbc_sldrSongFlow);
		
		JLabel lblSongTime = new JLabel("00:00:00");
		GridBagConstraints gbc_lblSongTime = new GridBagConstraints();
		gbc_lblSongTime.insets = new Insets(0, 0, 5, 5);
		gbc_lblSongTime.gridx = 6;
		gbc_lblSongTime.gridy = 0;
		pnlList.add(lblSongTime, gbc_lblSongTime);
		
		lblSongName = new MoveTextLable("Song Name", 1000, null);
		GridBagConstraints gbc_lblSongName = new GridBagConstraints();
		gbc_lblSongName.fill = GridBagConstraints.HORIZONTAL;
		gbc_lblSongName.anchor = GridBagConstraints.WEST;
		gbc_lblSongName.gridwidth = 7;
		gbc_lblSongName.insets = new Insets(0, 0, 5, 5);
		gbc_lblSongName.gridx = 0;
		gbc_lblSongName.gridy = 1;
		pnlList.add(lblSongName, gbc_lblSongName);
		lblSongName.setChanger(new LabelTextMover());
		
		
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
		btnSaveList.setToolTipText("Save List (\u0417\u0430\u043F\u0438\u0441\u0430\u0442\u044C \u043F\u043B\u0430\u0439\u043B\u0438\u0441\u0442)");
		btnSaveList.setIcon(new ImageIcon("C:\\Users\\Elf\\workspace\\Grooov\\res\\images\\save_16.png"));
		GridBagConstraints gbc_btnSaveList = new GridBagConstraints();
		gbc_btnSaveList.insets = new Insets(0, 0, 0, 5);
		gbc_btnSaveList.anchor = GridBagConstraints.WEST;
		gbc_btnSaveList.gridx = 0;
		gbc_btnSaveList.gridy = 3;
		pnlList.add(btnSaveList, gbc_btnSaveList);
		btnSaveList.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				GroovFileChooser saveChooser = new GroovFileChooser(false);
				if( saveChooser.showSaveDialog((JButton) e.getSource() ) == JFileChooser.APPROVE_OPTION ){
	                try {
	                	
						lstListOfSongs.saveList(saveChooser.getSelectedFile());
					} catch (Exception e1) {
						e1.printStackTrace();
						System.out.println("Error saving PlayList");
					}			
				}
			}
		} );
		
		JButton btnLoadList = new JButton("");
		btnLoadList.setToolTipText("Load Playlist (\u0417\u0430\u0433\u0440\u0443\u0437\u0438\u0442\u044C \u043F\u043B\u044D\u0439\u043B\u0438\u0441\u0442)");
		btnLoadList.setIcon(new ImageIcon("C:\\Users\\Elf\\workspace\\Grooov\\res\\images\\open-icon.png"));
		GridBagConstraints gbc_btnLoadList = new GridBagConstraints();
		gbc_btnLoadList.insets = new Insets(0, 0, 0, 5);
		gbc_btnLoadList.gridx = 1;
		gbc_btnLoadList.gridy = 3;
		pnlList.add(btnLoadList, gbc_btnLoadList);
		btnLoadList.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				GroovFileChooser loadChooser = new GroovFileChooser(false);
				if( loadChooser.showOpenDialog((JButton) e.getSource() ) == JFileChooser.APPROVE_OPTION ){
	                try {
						lstListOfSongs.loadList(loadChooser.getSelectedFile());
					} catch (Exception e1) {
						e1.printStackTrace();
						System.out.println("Error loading PlayList");
					}			
				}
			}
		});
		
		//add songs button
		JButton btnAddSong = new JButton("");
		btnAddSong.setToolTipText("Add Item (\u0414\u043E\u0431\u0430\u0432\u0438\u0442\u044C \u043F\u0435\u0441\u043D\u044E)");
		btnAddSong.setIcon(new ImageIcon("C:\\Users\\Elf\\workspace\\Grooov\\res\\images\\plus_16.png"));
		GridBagConstraints gbc_btnAddSong = new GridBagConstraints();
		gbc_btnAddSong.insets = new Insets(0, 0, 0, 5);
		gbc_btnAddSong.gridx = 2;
		gbc_btnAddSong.gridy = 3;
		pnlList.add(btnAddSong, gbc_btnAddSong);
		btnAddSong.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				GroovFileChooser addChooser = new GroovFileChooser(true);
				if( addChooser.showOpenDialog((JButton) e.getSource() ) == JFileChooser.APPROVE_OPTION ){
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
				//delete song
				((PlayList) lstListOfSongs).delSong();
			}
		});
		
		btnDelSong.setToolTipText("Del Item (\u0423\u0434\u0430\u043B\u0438\u0442\u044C \u043F\u0435\u0441\u043D\u044E)");
		btnDelSong.setIcon(new ImageIcon("C:\\Users\\Elf\\workspace\\Grooov\\res\\images\\remove_icon.png"));
		GridBagConstraints gbc_btnDelSong = new GridBagConstraints();
		gbc_btnDelSong.insets = new Insets(0, 0, 0, 5);
		gbc_btnDelSong.gridx = 3;
		gbc_btnDelSong.gridy = 3;
		pnlList.add(btnDelSong, gbc_btnDelSong);
		
		JButton btnDownSongInList = new JButton("");
		btnDownSongInList.setToolTipText("Song Up (\u041F\u0435\u0440\u0435\u043C\u0435\u0441\u0442\u0438\u0442\u044C \u043F\u0435\u0441\u043D\u044E \u0432\u0432\u0435\u0440\u0445 \u0432 \u0441\u043F\u0438\u0441\u043A\u0435)");
		btnDownSongInList.setIcon(new ImageIcon("C:\\Users\\Elf\\workspace\\Grooov\\res\\images\\arrow-down-icon.png"));
		GridBagConstraints gbc_btnDownSongInList = new GridBagConstraints();
		gbc_btnDownSongInList.insets = new Insets(0, 0, 0, 5);
		gbc_btnDownSongInList.gridx = 4;
		gbc_btnDownSongInList.gridy = 3;
		pnlList.add(btnDownSongInList, gbc_btnDownSongInList);
		
		JButton btnUpSongInList = new JButton("");
		btnUpSongInList.setIcon(new ImageIcon("C:\\Users\\Elf\\workspace\\Grooov\\res\\images\\arrow-up-icon.png"));
		btnUpSongInList.setToolTipText("Up Song (\u041F\u043E\u0434\u043D\u044F\u0442\u044C \u043A\u043E\u043C\u043F\u043E\u0437\u0438\u0446\u0438\u044E \u0432 \u0441\u043F\u0438\u0441\u043A\u0435)");
		GridBagConstraints gbc_btnUpSongInList = new GridBagConstraints();
		gbc_btnUpSongInList.insets = new Insets(0, 0, 0, 5);
		gbc_btnUpSongInList.gridx = 5;
		gbc_btnUpSongInList.gridy = 3;
		pnlList.add(btnUpSongInList, gbc_btnUpSongInList);
				
		btnDownSongInList.addActionListener(EventHandler.create(ActionListener.class, lstListOfSongs, "downSong"));
		btnUpSongInList.addActionListener(EventHandler.create(ActionListener.class, lstListOfSongs, "upSong"));
		
		JMenuBar menuBar = new JMenuBar();
		getContentPane().add(menuBar, BorderLayout.SOUTH);
		
		JMenu mnMenu = new JMenu("Menu");
		mnMenu.setHorizontalAlignment(SwingConstants.LEFT);
		menuBar.add(mnMenu);
		
		JMenuItem mntmSettings = new JMenuItem("Settings");
		mnMenu.add(mntmSettings);
		
		JMenuItem mntmChangeSkin = new JMenuItem("Change skin");
		mnMenu.add(mntmChangeSkin);
		setForeground(UIManager.getColor("InternalFrame.activeTitleGradient"));
		setBounds(100, 100, 450, 300);
		pack();
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
}