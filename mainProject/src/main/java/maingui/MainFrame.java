package maingui;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Container;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.GraphicsEnvironment;
import java.awt.FontFormatException;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.nio.file.Paths;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;

import mainlogic.HardwareNameList;
import mainlogic.MainGee;

public class MainFrame extends JFrame {
	public Content content;

	public static final String[] names = { "CPU", "CPU Cooler", "MotherBoard", "Memory", "Disk", "Graphic", "PSU",
			"Computer Case" };

	// public static final Font title = new Font("Greek", Font.BOLD, 20);
	// public static final Font font = new Font("Greek", Font.BOLD, 16);

	public static Font title;
	public static Font font;

	private final Container contentPane;
	private CardLayout card;

	private RefreshWorker worker;

	private SmartModeBtnPanel smartModeBtnPanel;

	private MainPanel mainPanel;

	private SuggestionPanel suggestionPanel;

	public MainFrame(MainGee source, HardwareNameList content)
	{
		super("大力出奇機");
		
		this.content = new Content();
		this.content.setSource(source);
		this.content.setContent(content);
		this.content.initLists();
		
		card = new CardLayout();
		contentPane = this.getContentPane();
		contentPane.setLayout(card);

		// try {
		// 	GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();

		// 	ge.registerFont(Font.createFont(Font.TRUETYPE_FONT, new File("/sarasa-fixed-cl-bold.ttf")));
		// } 
		// catch (IOException|FontFormatException e) {
		// 	e.printStackTrace();
		// }

		try {
			// FileInputStream stream = getClass().getResourceAsStream("/sarasa-fixed-cl-bold.ttf");
			// System.out.println(Paths.get(getClass().getResource("/sarasa-fixed-cl-bold.ttf").toURI()).toString());
			// File f = new File("/sarasa-fixed-cl-bold.ttf");
			// System.out.println(f.getAbsolutePath());

			InputStream is = getClass().getResourceAsStream("/sarasa-fixed-cl-bold.ttf");

			Font fT = Font.createFont(Font.TRUETYPE_FONT, is);
			// Font f = Font.createFont(Font.TRUETYPE_FONT, is);

			is.close();

			GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
			ge.registerFont(fT);
			// ge.registerFont(f);

			// System.out.println(Paths.get(getClass().getResource("/circle.gif").toURI()).toString());

			// title 		= Loadfont.loadFont(Paths.get(getClass().getResource("../sarasa-fixed-cl-bold.ttf").toURI()).toString(), 22);
			// font 		= Loadfont.loadFont(Paths.get(getClass().getResource("../sarasa-fixed-cl-bold.ttf").toURI()).toString(), 16);
			

			// String path = System.getProperty("java.class.path");
			// String fileseparator = System.getProperty("file.separator");
			// int pathlength = path.length();
			// if (path.charAt(pathlength-1) != fileseparator.charAt(0)) { path += fileseparator; }
			// path += "sarasa-fixed-cl-bold.ttf";

			// System.out.println("\""+ path+ "\"");

			// Font f = Font.createFont(Font.TRUETYPE_FONT, new File(path));

			// // stream.close();
			title 		= fT.deriveFont(22f);
			font 		= fT.deriveFont(16f);			

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void init() {
		JPanel masterPanel = new JPanel();
		masterPanel.setLayout(new BorderLayout());
		contentPane.add("MAIN", masterPanel);
		
		LoadingPanel loadingPanel = new LoadingPanel(this);
		contentPane.add("LOAD", loadingPanel);

		// masterPanel
		
		// SmartModeBtnPanel
		smartModeBtnPanel = new SmartModeBtnPanel();
		smartModeBtnPanel.init();
		smartModeBtnPanel.getSmartModeBtn().addActionListener(new SmartListener());
		
		
		JPanel centerPanel = new JPanel();
		centerPanel.setLayout(new BorderLayout());
		// mainPanel
		mainPanel = new MainPanel(this);
		mainPanel.init();
		
		JScrollPane scrollMainPanel = new JScrollPane(mainPanel);
		scrollMainPanel.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		scrollMainPanel.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		scrollMainPanel.getVerticalScrollBar().setUnitIncrement(16);
		
		// suggestion Panel
		suggestionPanel = new SuggestionPanel();
		suggestionPanel.init();
		
		centerPanel.add(scrollMainPanel, BorderLayout.CENTER);
		centerPanel.add(suggestionPanel, BorderLayout.EAST);
		
		masterPanel.add(smartModeBtnPanel, BorderLayout.NORTH);
		masterPanel.add(centerPanel, BorderLayout.CENTER);
	}
	
	public void test() {
		try {
			Thread.sleep(1000);
		}
		catch(Exception e) {
			System.out.println(e);
		}
		switchCards("LOAD");
		try {
			Thread.sleep(1000);
		}
		catch(Exception e) {
			System.out.println(e);
		}
		switchCards("MAIN");
	}
	
	public void switchCards(String choice) {
		card.show(contentPane, choice);
	}
	
	public void refresh() {
		content.setInputs(mainPanel.getOptionPanel().getComboBoxes()
						, mainPanel.getOptionPanel().getSpinners());

		worker = new RefreshWorker(this);
		worker.execute();
//		switchCards("LOAD");
		enableSwitch(false);
	}
	
	public void setSuggestion() {
		ArrayList<String> sugg = new ArrayList<String>(content.getSuggestions());
		String text = "";
		
		for(int i = 0; i < sugg.size() - 3; i++) {
			text += String.format("%s%n", sugg.get(i));
		}
		suggestionPanel.getSuggestion().setText("");
		suggestionPanel.getSuggestion().setText(text);
	}
	
	public void enableSwitch(boolean op) {
		mainPanel.getOptionPanel().enableSwitch(op);
		mainPanel.getPlusBtnPanel().enableSwitch(op);
		smartModeBtnPanel.enableSwitch(op);
	}
	
	private class SmartListener implements ActionListener {
		private Object[] optionsOn = {"YES", "NO"};
		private Object[] optionsOff = {"YES and Clear", "YES and Retain", "NO"};
		private ImageIcon toggleOn = new ImageIcon(getClass().getResource("/switchOn.png"));
		private ImageIcon toggleOff = new ImageIcon(getClass().getResource("/switchOff.png"));
		
		@Override
		public void actionPerformed(ActionEvent event) {
			if(smartModeBtnPanel.getToRefresh()) { // switch to normal mode
				int option = JOptionPane.showOptionDialog(MainFrame.this, "Would you like to switch to normal mode?",
														  "Question", JOptionPane.YES_NO_CANCEL_OPTION,
														  JOptionPane.QUESTION_MESSAGE, null, optionsOff, optionsOff[0]);
				if(option == JOptionPane.YES_OPTION) {
					smartModeBtnPanel.getSmartModeBtn().setIcon(toggleOff);
					
					smartModeBtnPanel.setToRefresh(false);
					
					content.initLists();
					mainPanel.getOptionPanel().init();
					mainPanel.getPlusBtnPanel().init();
					mainPanel.init();
					suggestionPanel.init();
				}
				else if(option == JOptionPane.NO_OPTION) {
					smartModeBtnPanel.getSmartModeBtn().setIcon(toggleOff);
					
					smartModeBtnPanel.setToRefresh(false);
					
					
					content.initLists();
					mainPanel.getOptionPanel().updateComboBoxes(smartModeBtnPanel.getToRefresh());

					for(JSpinner spinner : mainPanel.getOptionPanel().getSpinners()) {
						spinner.setEnabled(true);
					}
				}
			}
			else { // switch to smart mode
				int option = JOptionPane.showOptionDialog(MainFrame.this, "Would you like to switch to smart mode?",
						  								  "Question", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE,
						  								  null, optionsOn, optionsOn[0]);
				if(option == JOptionPane.YES_OPTION) {
					smartModeBtnPanel.getSmartModeBtn().setIcon(toggleOn);
					
					smartModeBtnPanel.setToRefresh(true);

					content.initLists();
					mainPanel.getOptionPanel().init();
					mainPanel.getPlusBtnPanel().init();
					mainPanel.init();
					suggestionPanel.init();
				}
			}
		}
	}
	
	public Content getContent() {
		return content;
	}
	
	public MainPanel getMainPanel() {
		return mainPanel;
	}
	
	public SmartModeBtnPanel getSmartModeBtnPanel() {
		return smartModeBtnPanel;
	}
}