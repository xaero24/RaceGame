/**
 * 
 */
package utilities;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.border.Border;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellEditor;

import factory.RaceBuilder;
import factory.RacingClassesFinder;
import game.arenas.Arena;
import game.arenas.exceptions.RacerLimitException;
import game.arenas.exceptions.RacerTypeException;
import game.racers.Racer;

/**
 * @author Michael Afonin, 310514997
 *
 */
public class raceWindow extends JFrame {
	private int selectedArenaLength, selectedMaxRacers;
	private String arenaName, racerName, racerNickname;
	private String colorName;
	private String racerShortName;
	private EnumContainer.Color racerColor;
	private int selectedMaxSpeed;
	private int selectedAcceleration;
	private URL imagePath;
	private Arena arena;
	private Racer racer;
	private ArrayList<JLabel> racerIcons = new ArrayList<JLabel>();
	private ArrayList<Racer> racers = new ArrayList<Racer>();
	private static RaceBuilder builder = RaceBuilder.getInstance();
	private ImageManager racePanel;
	private JComboBox<utilities.EnumContainer.Color> cmbColor;
	private int racerIndex=0;
	private JTable infoTable;
	private Object[][] data;
	private JDialog infoShower;

	public raceWindow() {
		super("Race");
		//raceWindow.setLayout(new GridBagLayout());
		Component controlPanelComponents = controlPanel();
		getContentPane().add(controlPanelComponents, BorderLayout.LINE_END);
		racePanel = new ImageManager();
		getContentPane().add(racePanel, BorderLayout.CENTER);
		racePanel.setLayout(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(1000+controlPanelComponents.getWidth(),700);
		setVisible(true);
		//raceWindow.setResizable(false);
	}

	public Component controlPanel() {
		arena = null; arenaName = null;
		racer = null; racerName = null; racerColor = null;
		JPanel pane = new JPanel();
		pane.add(new JLabel("Choose arena:"));
		JComboBox<String> arenaComboBox = new JComboBox<String>();
		for (String string : RacingClassesFinder.getInstance().getArenasNamesList()) {
			arenaComboBox.addItem(string);
		}

		pane.add(arenaComboBox);

		pane.add(new JLabel("Arena length:"));
		JTextField areaLengthInputField = new JTextField(String.valueOf(1000));
		pane.add(areaLengthInputField);

		pane.add(new JLabel("Max racers number:"));
		JTextField maxRacersInputField = new JTextField(String.valueOf(8));
		pane.add(maxRacersInputField);

		JButton buildArenaButton = new JButton("Build arena");
		//buildArenaButton.setMnemonic(KeyEvent.BUTTON1_DOWN_MASK);
		buildArenaButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				for (String arenaFullName : RacingClassesFinder.getInstance().getArenasList()) {
					if (arenaFullName.contains( arenaComboBox.getSelectedItem().toString() )) {
						arenaName = arenaFullName;
						imagePath = this.getClass().getResource("icons/"+arenaComboBox.getSelectedItem().toString()+".jpg");
					}
				}
				selectedArenaLength = Integer.parseInt(areaLengthInputField.getText());
				selectedMaxRacers = Integer.parseInt(maxRacersInputField.getText());
				if (selectedArenaLength<100 || selectedArenaLength>3000) {
					JOptionPane.showMessageDialog(null, "Invalid arena size. Choose 100-3000.", "Message", JOptionPane.INFORMATION_MESSAGE);
				} else if (selectedMaxRacers<1 || selectedMaxRacers>20) {
					JOptionPane.showMessageDialog(null, "Invalid max racers count. Choose 1-20.", "Message", JOptionPane.INFORMATION_MESSAGE);
				}else {
					try {
						arena = builder.buildArena(arenaName, selectedArenaLength, selectedMaxRacers);
						if (racers.size() != 0) {
							racers = new ArrayList<Racer>();
							racerIcons = new ArrayList<JLabel>();
						}
						racePanel.removeAll();
						racePanel.changeImage();
						racePanel.setLayout(null);
						if (75*selectedMaxRacers>700) {
							setSize(selectedArenaLength+pane.getWidth()+100, 75*selectedMaxRacers);
							racePanel.setSize(selectedArenaLength+100, 75*selectedMaxRacers);
						}
						else {
							setSize(selectedArenaLength+pane.getWidth()+100, 700);
							racePanel.setSize(selectedArenaLength+100, 700);
						}
						revalidate();
						repaint();
					} catch (Exception E) {
						JOptionPane.showMessageDialog(null, "Error caught:\n"+E.getMessage(), "", JOptionPane.ERROR_MESSAGE);
					}
				}
			}
		});
		pane.add(buildArenaButton);
		// TODO get the separator to the middle
		pane.add(new JSeparator(JSeparator.HORIZONTAL), BorderLayout.LINE_START);

		pane.add(new JLabel("Choose racer:"));
		JComboBox<String> racerComboBox = new JComboBox<String>();
		for (String string : RacingClassesFinder.getInstance().getRacersNamesList()) {
			racerComboBox.addItem(string);
		}
		pane.add(racerComboBox);

		pane.add(new JLabel("Choose color:"));
		cmbColor = new JComboBox<utilities.EnumContainer.Color>();
		for (utilities.EnumContainer.Color color : Arrays.asList(utilities.EnumContainer.Color.values())) {
			cmbColor.addItem(color);
		}

		pane.add(cmbColor);

		pane.add(new JLabel("Racer name:"));
		JTextField racerNameInputField = new JTextField("");
		pane.add(racerNameInputField);

		pane.add(new JLabel("Max speed:"));
		JTextField maxSpeedInputField = new JTextField();
		pane.add(maxSpeedInputField);

		pane.add(new JLabel("Acceleration:"));
		JTextField accelerationInputField = new JTextField();
		pane.add(accelerationInputField);

		JButton addRacerButton = new JButton("Add racer");
		addRacerButton.setMnemonic(KeyEvent.BUTTON1_DOWN_MASK);
		addRacerButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				colorName=cmbColor.getSelectedItem().toString();
				for (String racerFullName : RacingClassesFinder.getInstance().getRacersList()) {
					racerShortName = racerComboBox.getSelectedItem().toString();
					if (racerFullName.contains( racerShortName )) {
						racerName = racerFullName;
					}
				}
				if (arena.getActiveRacers().size() == selectedMaxRacers) {
					JOptionPane.showMessageDialog(null, "Maximum number of racers ("+selectedMaxRacers+") was reached.", "Message", JOptionPane.INFORMATION_MESSAGE);
				} else if (maxSpeedInputField.getText().isEmpty() || accelerationInputField.getText().isEmpty()) {
					JOptionPane.showMessageDialog(null, "No max speed or acceleration entered. Try again.", "Message", JOptionPane.INFORMATION_MESSAGE);
				} else if (racerName == null) {
					JOptionPane.showMessageDialog(null, "No racer was selected.", "Message", JOptionPane.INFORMATION_MESSAGE);
				} 
				else {
					racerNickname = racerNameInputField.getText();
					if (racerNickname == "") {
						racerNickname = racer.className()+" #" + Racer.getStaticSN();
					}
					selectedMaxSpeed = Integer.parseInt(maxSpeedInputField.getText());
					selectedAcceleration = Integer.parseInt(accelerationInputField.getText());
					try {
						racer = builder.buildRacer(racerName, racerNickname, selectedMaxSpeed, selectedAcceleration, racerColor);
						arena.addRacer(racer);
						racers.add(racer);
						arena.initRace();
						ImageIcon icon = new ImageIcon(getClass().getResource("icons/"+racerShortName+colorName+".png"));

						//racers.indexOf(racer) - index of current racer

						racerIcons.add(new JLabel(""));
						racerIcons.get(racers.indexOf(racer)).setIcon(new ImageIcon(getScaledImage(icon.getImage(), 70, 70)));

						racerIcons.get(racers.indexOf(racer)).setBounds(0, racerIndex*arena.getMinYGap(), 70, 70);

						racePanel.add(racerIcons.get(racers.indexOf(racer)));
						revalidate();
						repaint();
						racerIndex++;

					} catch (ClassNotFoundException | NoSuchMethodException | SecurityException | InstantiationException
							| IllegalAccessException | IllegalArgumentException | InvocationTargetException | RacerTypeException | RacerLimitException E) {
						JOptionPane.showMessageDialog(null, "Error caughtin building racer:\n"+E.getMessage(), "", JOptionPane.ERROR_MESSAGE);
					}
				}
			}
		});
		pane.add(addRacerButton);
		// TODO get the separator to the middle
		pane.add(new JSeparator(JSeparator.HORIZONTAL), BorderLayout.LINE_START);

		JButton startRaceButton = new JButton("Start race");
		startRaceButton.setMnemonic(KeyEvent.BUTTON1_DOWN_MASK);
		startRaceButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// TODO add error message on arena size and racers count
				if (arena == null) {
					JOptionPane.showMessageDialog(null, "Please build arena first and add racers!", "Message", JOptionPane.INFORMATION_MESSAGE);
				} else if (arena.hasActiveRacers() == false) {
					JOptionPane.showMessageDialog(null, "No racers were found. Create at least one racer.", "Message", JOptionPane.INFORMATION_MESSAGE);
				} else {
					ExecutorService ex =Executors.newSingleThreadExecutor();
					arena.initRace();
					ex.submit(new Runnable() {
						@Override
						public synchronized void run() {

							arena.startRace();
							while(!ex.isShutdown()) {
								racerPicMove();
								try {
									TimeUnit.MILLISECONDS.sleep(30);
								} catch (InterruptedException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
							}
							ex.shutdown();	
						}


					});
				}
			}
		});

		pane.add(startRaceButton);

		JButton showInfoButton = new JButton("Show info");
		showInfoButton.setMnemonic(KeyEvent.BUTTON1_DOWN_MASK);
		showInfoButton.addActionListener(new ActionListener() {
			private String[] columnHeaders;
			private String isFinished;

			public void actionPerformed(ActionEvent e) {
				if (arena == null) {
					JOptionPane.showMessageDialog(null, "Please build arena first and add racers!", "Message", JOptionPane.INFORMATION_MESSAGE);
				} else if (arena.hasActiveRacers() == false) {
					JOptionPane.showMessageDialog(null, "No racers were found. Create at least one racer.", "Message", JOptionPane.INFORMATION_MESSAGE);
				} else {
					infoShower = new JDialog();
					columnHeaders = new String[] {"Racer name", "Current speed", "Max speed", "Current X location", "Finished"};
					infoShower.getContentPane().setLayout(new BorderLayout());
					infoTable = new JTable(data, columnHeaders);
					DefaultTableModel model = new DefaultTableModel(0,0);
					model.setColumnIdentifiers(columnHeaders);
					infoTable.setModel(model);
					model.addRow(columnHeaders);
					for (Racer r : racers) {
						if (r.getCurrentLocation().getX() == selectedArenaLength){
							isFinished = "Yes";
						} else {
							isFinished="No";
						}
						model.addRow(new Object[]{r.getName(), r.getCurrentSpeed(), r.getMaxSpeed(), r.getCurrentLocation().getX(), isFinished});
					}
					model.addTableModelListener(new TableModelListener() {

						@Override
						public void tableChanged(TableModelEvent e) {
							model.addRow(columnHeaders);
							for(Racer r: racers) {
								if(r.getCurrentLocation().getX() == selectedArenaLength) {
									isFinished="Yes";
								} else {
									isFinished="No";
								}
								model.addRow(new Object[]{r.getName(), r.getCurrentSpeed(), r.getMaxSpeed(), r.getCurrentLocation().getX(), isFinished});
							}

						}
					});
					infoShower.getContentPane().add(infoTable);
					infoShower.pack();
					infoShower.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
					infoShower.setTitle("Racers information");
					infoShower.setVisible(true);
				}
			}
		});
		pane.add(showInfoButton);

		pane.setBorder(BorderFactory.createEmptyBorder(10, 30, 10, 10));
		pane.setLayout(new GridLayout(0, 1, 8, 8));
		pane.setSize(60, 700);
		return pane;
	}

	public class ImageManager extends JPanel {
		private Image image;
		public ImageManager() {
			super();
		}
		@Override
		protected void paintComponent(Graphics g){ 
			super.paintComponent(g);    
			g.drawImage(image, 0, 0, null);
		} 

		public void changeImage() {
			try {
				image = ImageIO.read(imagePath);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			image = image.getScaledInstance(selectedArenaLength+70,getHeight(),Image.SCALE_DEFAULT);
			repaint();
		}
	}

	private Image getScaledImage(Image srcImg, int w, int h){
		BufferedImage resizedImg = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
		Graphics2D g2 = resizedImg.createGraphics();

		g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
		g2.drawImage(srcImg, 0, 0, w, h, null);
		g2.dispose();

		return resizedImg;
	}
	private synchronized void racerPicMove() {
		for(Racer racer:racers) {
			racerIcons.get(racers.indexOf(racer)).setLocation((int)racer.getCurrentLocation().getX(),(int) racer.getCurrentLocation().getY());
			if(racer.getCurrentLocation().getX() >= selectedArenaLength) {
				racerIcons.get(racers.indexOf(racer)).setLocation((int) selectedArenaLength,(int) racer.getCurrentLocation().getY());
			}
		}

	}

	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable(){
			@Override
			public void run() {
				new raceWindow();
			}
		});

	}
}