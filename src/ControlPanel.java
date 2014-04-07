import java.awt.Color;
import java.awt.Component;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.Box;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.JSlider;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;


public class ControlPanel 
{
	private static final Color color = new Color(116, 131, 145);
	private Generator g;
	private JFrame frame;
	
	public ControlPanel(Generator g) throws IOException
	{
		this.g = g;
		buildGUI();
	}
	
	public void show()
	{
		frame.setVisible(true);
	}
	
	private void buildGUI() throws IOException
	{
		try {
		    UIManager.setLookAndFeel("com.seaglasslookandfeel.SeaGlassLookAndFeel");
		} catch (Exception e) {
		    e.printStackTrace();
		}
		
		frame = new JFrame("Music Gen");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);
		JPanel content = new JPanel();
		frame.add(content);
		content.setBackground(color);
		
		content.setLayout(new GridLayout(1, 3));
		content.setBorder(new EmptyBorder(10, 10, 10, 10));
		
		JLabel instructions = new JLabel("<html><center>Instructions:<br><br>Adjust generation settings in the left pane,<br>then just hit play to generate a song!</center></html>");
		instructions.setAlignmentX(Component.CENTER_ALIGNMENT);
		
		content.add(optionsPane());
		content.add(this.controlsPane());
		content.add(instructions);
		
		frame.pack();
	}
	
	private Box optionsPane()
	{
		Box options = Box.createVerticalBox();
		String[] keys = {"C", "C#", "D", "D#", "E", "F", "F#", "G", "G#", "A", "A#", "B"};
		String[] instruments = {"ALTO_SAX", "CELLO", "CHURCH_ORGAN", "CRYSTAL", "GUITAR", "HALO", "OVERDRIVEN_GUITAR", "PIANO", "POLYSYNTH", "SHAMISEN", 
				"SITAR", "STRING_ENSEMBLE_1", "STRING_ENSEMBLE_2", "VIOLIN", "WHISTLE"};
		
		JComboBox<String> key = new JComboBox<String>(keys);
		JComboBox<String> lead = new JComboBox<String>(instruments);
		JComboBox<String> rhythm = new JComboBox<String>(instruments);
		
		key.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				JComboBox<String> source = (JComboBox<String>) e.getSource();
				g.setRoot((String) source.getSelectedItem());
			}
		});
		
		lead.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				JComboBox<String> source = (JComboBox<String>) e.getSource();
				g.setLeadInst((String) source.getSelectedItem());
			}
		});
		
		rhythm.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				JComboBox<String> source = (JComboBox<String>) e.getSource();
				g.setChordsInst((String) source.getSelectedItem());
			}
		});
		
		JSlider tempo = new JSlider(JSlider.HORIZONTAL, 40, 200, 120);
		tempo.setMajorTickSpacing(40);
		tempo.setMinorTickSpacing(10);
		tempo.setPaintTicks(true);
		tempo.setPaintLabels(true);
		tempo.setBackground(color);
		tempo.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent e) {
				JSlider source = (JSlider) e.getSource();
				if(!source.getValueIsAdjusting())
				{
					g.setTempo(source.getValue());
				}
			}
		});
		
		Box mood = Box.createHorizontalBox();
		JCheckBox major = new JCheckBox("Major", true);
		major.setAlignmentX(Component.CENTER_ALIGNMENT);
		major.setBackground(color);
		mood.add(major);
		JCheckBox minor  = new JCheckBox("Minor");
		minor.setAlignmentX(Component.CENTER_ALIGNMENT);
		minor.setBackground(color);
		mood.add(minor);
		
		major.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				
			}
		});
		
		minor.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				
			}
		});
		
		JLabel keyLabel = new JLabel("Key Signature");
		keyLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
		options.add(keyLabel);
		options.add(key);
		options.add(mood);
		
		options.add(Box.createVerticalStrut(10));
		
		JLabel tempoLabel = new JLabel("Tempo");
		tempoLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
		options.add(tempoLabel);
		options.add(tempo);
		
		options.add(Box.createVerticalStrut(10));
		options.add(new JSeparator(SwingConstants.HORIZONTAL));
		options.add(Box.createVerticalStrut(10));
		
		JLabel rhythmLabel = new JLabel("Rhythm");
		rhythmLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
		options.add(rhythmLabel);
		options.add(rhythm);
		
		JLabel leadLabel = new JLabel("Lead");
		leadLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
		options.add(leadLabel);
		options.add(lead);
		
		return options;
	}
	
	private Box controlsPane() throws IOException
	{
		Box controls = Box.createVerticalBox();
		
		BufferedImage logo = ImageIO.read(new File("gui.png"));
		JLabel background = new JLabel(new ImageIcon(logo));
		background.setAlignmentX(Component.CENTER_ALIGNMENT);
		
		Box playback = Box.createHorizontalBox();
		JButton play = new JButton(">");
		play.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				g.play();
			}
		});
		JButton stop = new JButton("[]");
		JButton pause = new JButton("| |");
		playback.add(play);
		playback.add(stop);
		playback.add(pause);
		
		JButton save = new JButton("Save");
		save.setAlignmentX(Component.CENTER_ALIGNMENT);
		
		controls.add(background);
		
		JLabel playbackLabel = new JLabel("Playback");
		playbackLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
		controls.add(playbackLabel);
		controls.add(playback);
		
		controls.add(Box.createVerticalStrut(2));
		controls.add(save);
		
		return controls;
	}
}