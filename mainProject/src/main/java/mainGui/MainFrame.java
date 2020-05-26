package maingui;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.List;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.ActionEvent;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JTextArea;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.BorderFactory;
import javax.swing.border.TitledBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;

import java.util.ArrayList;

import mainlogic.MainGee;
import mainlogic.HardwareNameList;

public class MainFrame extends JFrame
{
	public static final int CPU  = 0; public static final int COOL = 1;
	public static final int MB   = 2; public static final int MEM  = 3;
	public static final int DISK = 4; public static final int VGA  = 5;
	public static final int PSU  = 6; public static final int CASE = 7;
	
	private final String[] names = {"CPU", "CPU Cooler", "MotherBoard", "Memory", "Disk", "Graphic",
							"PSU", "Computer Case"};
	
	private final Font title =  new Font("Monospaced", Font.BOLD, 20);
	
	private MainGee source;
	private HardwareNameList content;
	private ArrayList<ArrayList<String>> lists; // content of comboBoxes
	private ArrayList<ArrayList<String>> inputs;// chosen of comboBoxes
	
	private JPanel smartModeBtnPane; // Frame BorderLayout.NORTH
	private JButton smartModeBtn; // button for changing mode
	private boolean toRefresh;
	private boolean toInit;
	
	private JPanel masterPane; // Frame BorderLayout.CENTER
	private JScrollPane mPane; // masterPane with scroll bar
	
	private JPanel optionPane; // masterPane BorderLayout.CENTER
	private ArrayList<ArrayList<ComponentPanel>> subComponentPanes; // panels inside optionPane
	
	// components inside subComponentPanes
	private ArrayList<ArrayList<FilterComboBox>> comboBoxes;
	private ArrayList<JSpinner> spinners;
	private ArrayList<ArrayList<JButton>> subButtons;
	private ArrayList<ArrayList<JButton>> gearButtons;

	private JPanel plusPane; // masterPane BorderLayout.EAST
	private ArrayList<JPanel> subPlusBtnPanes; // panels inside plusPane
	private JButton[] plusButtons; // buttons inside subPlusBtnPanes
	
	private String subFrameFeedback; // custom chosen
	private JTextArea suggestion; // at Frame's right side
	
	public MainFrame(MainGee source, HardwareNameList content)
	{
		super("大力出奇機");
		
		this.source = source;
		this.content = content;
	}
	
	public void init() {
		try {
			Thread.sleep(5000);
		}
		catch(Exception e) {
			System.out.println(e);
		}
		
		// initialize variables
		lists = new ArrayList<ArrayList<String>>();
		
		lists.add(new ArrayList<String>(content.cpu)); 		lists.add(new ArrayList<String>(content.cooler));
		lists.add(new ArrayList<String>(content.mb)); 		lists.add(new ArrayList<String>(content.ram));
		lists.add(new ArrayList<String>(content.disk)); 	lists.add(new ArrayList<String>(content.vga));
		lists.add(new ArrayList<String>(content.psu)); 		lists.add(new ArrayList<String>(content.crate));
		
		for(ArrayList<String> list : lists) {
			list.add(0, "請選擇");
		}
		
		subComponentPanes = new ArrayList<ArrayList<ComponentPanel>>();
		comboBoxes = new ArrayList<ArrayList<FilterComboBox>>();
		gearButtons = new ArrayList<ArrayList<JButton>>();
		subButtons = new ArrayList<ArrayList<JButton>>();
		
		for(int i = 0; i < lists.size(); i++) {
			subComponentPanes.add(new ArrayList<ComponentPanel>());
			comboBoxes.add(new ArrayList<FilterComboBox>());
			gearButtons.add(new ArrayList<JButton>());
			subButtons.add(new ArrayList<JButton>());
		}
		
		// set this Frame
		this.setLayout(new BorderLayout());
		
		// smart mode button Panel
		smartModeBtnPane = new JPanel();
		smartModeBtnPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
		smartModeBtnPane.setBackground(new Color(231, 242, 255));
		smartModeBtnPane.setOpaque(true);
		this.add(smartModeBtnPane, BorderLayout.NORTH);
		
		ImageIcon toggle = new ImageIcon(getClass().getResource("switchOn.png"));
		smartModeBtn = new JButton(toggle);
		
		smartModeBtn.setPreferredSize(new Dimension(95, 30));
		smartModeBtn.setBorder(BorderFactory.createEmptyBorder());
		smartModeBtn.setContentAreaFilled(false);
		smartModeBtnPane.add(smartModeBtn);
		
		toInit = false;
		toRefresh = true;
		
		smartModeBtn.addActionListener(new ActionListener() {
			private Object[] optionsOn = {"YES", "NO"};
			private Object[] optionsOff = {"YES and Clear", "YES and Retain", "NO"};
			private ImageIcon toggleOn = new ImageIcon(getClass().getResource("switchOn.png"));
			private ImageIcon toggleOff = new ImageIcon(getClass().getResource("switchOff.png"));
			
			@Override
			public void actionPerformed(ActionEvent event) {
				if(toRefresh) { // switch to normal mode
					int option = JOptionPane.showOptionDialog(MainFrame.this, "Would you like to switch to normal mode?",
															  "Question", JOptionPane.YES_NO_CANCEL_OPTION,
															  JOptionPane.QUESTION_MESSAGE, null, optionsOff, optionsOff[0]);
					if(option == JOptionPane.YES_OPTION) {
						smartModeBtn.setIcon(toggleOff);
						
						toRefresh = false;
						toInit = true;
						optionsInit();
					}
					else if(option == JOptionPane.NO_OPTION) {
						smartModeBtn.setIcon(toggleOff);
						
						toRefresh = false;
						toInit = false;
						optionsInit();
						updateComboBoxes();
					}
				}
				else { // switch to smart mode
					int option = JOptionPane.showOptionDialog(MainFrame.this, "Would you like to switch to smart mode?",
							  								  "Question", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE,
							  								  null, optionsOn, optionsOn[0]);
					if(option == JOptionPane.YES_OPTION) {
						smartModeBtn.setIcon(toggleOn);
						toInit = true;
						optionsInit();
						toRefresh = true;
					}
				}
			}
		});
		
		// master Panel
		masterPane = new JPanel();
		masterPane.setLayout(new BorderLayout());
		
		// option Panel
		optionPane = new JPanel();
		optionPane.setLayout(new GridLayout(0, 1));
		
		masterPane.add(optionPane, BorderLayout.CENTER);
		
		// sub components' Panel
		for(int i = 0; i < lists.size(); i++) {
			subComponentPanes.get(i).add(new ComponentPanel(lists.get(i), i));
			subComponentPanes.get(i).get(0).setSubBtn(false);
			optionPane.add(subComponentPanes.get(i).get(0));
			
			subComponentPanes.get(i).get(0).setBorder(BorderFactory.createTitledBorder(names[i]));
			((TitledBorder)subComponentPanes.get(i).get(0).getBorder()).setTitleFont(title);
			
			subButtons.get(i).add(subComponentPanes.get(i).get(0).getSubBtn());
			subButtons.get(i).get(0).addActionListener(new SubBtnListener());
			
			comboBoxes.get(i).add(subComponentPanes.get(i).get(0).getComboBox());
			comboBoxes.get(i).get(0).addItemListener(new ComboBoxListener());	
			
			if(i != CPU && i != MB) {
				gearButtons.get(i).add(subComponentPanes.get(i).get(0).getGear());
				gearButtons.get(i).get(0).addActionListener(new GearListener());
			}
		}
		
		spinners = new ArrayList<JSpinner>();
		spinners.add(subComponentPanes.get(MEM).get(0).getSpinner());
		spinners.get(0).addChangeListener(new SpinnerListener());
		
		// plus Button Panel
		plusPane = new JPanel();
		plusPane.setLayout(new GridLayout(0, 1, 0, 0));
		plusPane.setBorder(BorderFactory.createLineBorder(Color.lightGray, 1));
		plusPane.setBackground(new Color(231, 242, 255));
		plusPane.setOpaque(true);

		masterPane.add(plusPane, BorderLayout.EAST);
		
		subPlusBtnPanes = new ArrayList<JPanel>();
		
		// sub plus' Panel
		for(int i = 0; i < lists.size(); i++) {
			subPlusBtnPanes.add(new JPanel());
			subPlusBtnPanes.get(i).setLayout(new BoxLayout(subPlusBtnPanes.get(i), BoxLayout.LINE_AXIS));
			subPlusBtnPanes.get(i).setAlignmentY(Component.CENTER_ALIGNMENT);
			subPlusBtnPanes.get(i).setBackground(new Color(231, 242, 255));
			subPlusBtnPanes.get(i).setOpaque(true);
			
			subPlusBtnPanes.get(i).setBorder(BorderFactory.createLineBorder(Color.lightGray));
			
			plusPane.add(subPlusBtnPanes.get(i));
		}
		ImageIcon plus = new ImageIcon(getClass().getResource("plus.jpg"));
		
		plusButtons = new JButton[3];
		for(int i = 0; i < 3; i++) {
			plusButtons[i] = new JButton(plus);
			plusButtons[i].setPreferredSize(new Dimension(30, 30));
			plusButtons[i].addActionListener(new PlusBtnListener());
		}
		subPlusBtnPanes.get(MEM).add(plusButtons[0]);
		subPlusBtnPanes.get(DISK).add(plusButtons[1]);
		subPlusBtnPanes.get(VGA).add(plusButtons[2]);
		
		// add scroll bar to masterPane
		mPane = new JScrollPane(masterPane);
		mPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		mPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		mPane.getVerticalScrollBar().setUnitIncrement(16);

		this.add(mPane, BorderLayout.CENTER);
		
		// suggestion Panel
		JPanel suggestionPane = new JPanel();
		suggestionPane.setLayout(new BorderLayout());
		suggestionPane.setBorder(BorderFactory.createTitledBorder("Suggestions"));
		suggestionPane.setPreferredSize(new Dimension(300, 600));
		suggestionPane.setBackground(new Color(231, 242, 255));
		suggestionPane.setOpaque(true);
		
		this.add(suggestionPane, BorderLayout.EAST);

		// TextArea
		suggestion = new JTextArea();
		suggestion.setLineWrap(true);
		suggestion.setEditable(false);
		suggestion.setBackground(new Color(233, 233, 233));
		suggestion.setOpaque(true);
		JScrollPane scrollSuggestion = new JScrollPane(suggestion);
		suggestionPane.add(scrollSuggestion, BorderLayout.CENTER);
	}
	
	// get feedback from sub frames
	public void setFeedback(String s) {
		subFrameFeedback = s;
	}
	
	public boolean customMatched(String str, int cur) {
		boolean matched = false;
		switch(cur) {
			case COOL:
				if(str.matches("custom [0-9]{1,3}cm")) {
					matched = true;
				}
				break;
			case MEM:
				if(str.matches("custom ddr[1-4] (1|2|4|8|16|32)G")) {
					matched = true;			
				}
				break;
			case DISK:
				if(str.matches("custom ((m.2 (pcie|sata))|((ssd|hdd) (2.5\"|3.5\"))) [0-9]{1,3}(G|T)")) {
					matched = true;
				}
				break;
			case VGA:
				if(str.matches("custom [0-9]{1,3}cm [0-9]{1,3}W")) {
					matched = true;
				}
				break;
			case PSU:
				if(str.matches("custom [0-9]{1,3}cm [0-9]{1,3}W (ATX|SFX)")) {
					matched = true;
				}
				break;
			case CASE:
				if(str.matches("custom (ATX|MATX|EATX|ITX|MITX) [0-9]{1,3}cm (ATX|SFX) [0-9]{1,3}cm [0-9]{1,3}cm [0-9]{1,3}個")) {
					matched = true;
				}
				break;
		}
		return matched;
	}
	
	public void optionsInit() {
		lists = new ArrayList<ArrayList<String>>();
		
		lists.add(new ArrayList<String>(content.cpu)); 		lists.add(new ArrayList<String>(content.cooler));
		lists.add(new ArrayList<String>(content.mb)); 		lists.add(new ArrayList<String>(content.ram));
		lists.add(new ArrayList<String>(content.disk)); 	lists.add(new ArrayList<String>(content.vga));
		lists.add(new ArrayList<String>(content.psu)); 		lists.add(new ArrayList<String>(content.crate));
		
		for(ArrayList<String> list : lists) {
			list.add(0, "請選擇");
		}
		
		
		if(toInit) {
			optionPane.removeAll();
			plusPane.removeAll();
			subPlusBtnPanes.clear();
			
			for(int i = 0; i < lists.size(); i++) {
				ComponentPanel copy = new ComponentPanel(lists.get(i), i);
				subComponentPanes.get(i).clear();
				subComponentPanes.get(i).add(copy);

				subComponentPanes.get(i).get(0).setSubBtn(false);
				subComponentPanes.get(i).get(0).setBorder(BorderFactory.createTitledBorder(names[i]));
				((TitledBorder)subComponentPanes.get(i).get(0).getBorder()).setTitleFont(title);
				
				optionPane.add(subComponentPanes.get(i).get(0));
				
				subPlusBtnPanes.add(new JPanel());
				subPlusBtnPanes.get(i).setLayout(new BoxLayout(subPlusBtnPanes.get(i), BoxLayout.LINE_AXIS));
				subPlusBtnPanes.get(i).setAlignmentY(Component.CENTER_ALIGNMENT);
				subPlusBtnPanes.get(i).setBackground(new Color(231, 242, 255));
				subPlusBtnPanes.get(i).setOpaque(true);
				
				subPlusBtnPanes.get(i).setBorder(BorderFactory.createLineBorder(Color.lightGray));
				
				plusPane.add(subPlusBtnPanes.get(i));
				
				comboBoxes.get(i).clear();
				comboBoxes.get(i).add(subComponentPanes.get(i).get(0).getComboBox());
				comboBoxes.get(i).get(0).addItemListener(new ComboBoxListener());
				
				gearButtons.get(i).clear();
				gearButtons.get(i).add(subComponentPanes.get(i).get(0).getGear());
				gearButtons.get(i).get(0).addActionListener(new GearListener());
				
				subButtons.get(i).clear();
				subButtons.get(i).add(subComponentPanes.get(i).get(0).getSubBtn());
				subButtons.get(i).get(0).addActionListener(new SubBtnListener());
			}
			
			spinners.clear();
			spinners.add(subComponentPanes.get(MEM).get(0).getSpinner());
			spinners.get(0).addChangeListener(new SpinnerListener());
			
			subPlusBtnPanes.get(MEM).add(plusButtons[0]);
			subPlusBtnPanes.get(DISK).add(plusButtons[1]);
			subPlusBtnPanes.get(VGA).add(plusButtons[2]);
		}
	}
	
	// refresh content
	public void refresh() {
		updateInputs();
		
		if(toRefresh) {
			updateLists();
		}
		updateComboBoxes();
	}
	
	public void updateComboBoxes() {
		for(int i = 0; i < comboBoxes.size(); i++) {
			if(toRefresh) { // smart mode
				lists.get(i).add(0, "請選擇");
			}
			for(int j = 0; j < comboBoxes.get(i).size(); j++) {
				String current = comboBoxes.get(i).get(j).getTextField().getText();
				int index = lists.get(i).indexOf(current);
				
				if(index >= 0 && !current.equals("請選擇")) {
					lists.get(i).remove(index);
					lists.get(i).add(0, current);
					comboBoxes.get(i).get(j).updateEntries(lists.get(i));
					lists.get(i).remove(0);
					lists.get(i).add(index, current);
				}
				else if(current.indexOf("custom") == 0) {
					if(customMatched(current, i)) {
						lists.get(i).add(0, current);
						comboBoxes.get(i).get(j).updateEntries(lists.get(i));
						lists.get(i).remove(0);
						lists.get(i).add(current);
					}
				}
				else {
					comboBoxes.get(i).get(j).updateEntries(lists.get(i));
				}
			}
		}
	}
	
	
	public void updateLists() {
		HardwareNameList currentInputs = new HardwareNameList(inputs.get(CPU),
															  inputs.get(COOL),
															  inputs.get(MB),
															  inputs.get(MEM),
															  inputs.get(DISK),
															  inputs.get(VGA),
															  inputs.get(PSU),
															  inputs.get(CASE));
		
		HardwareNameList newLists = source.getList(currentInputs, toRefresh);
		
		lists = new ArrayList<ArrayList<String>>();
		lists.add(newLists.cpu);	lists.add(newLists.cooler);
		lists.add(newLists.mb);		lists.add(newLists.ram);
		lists.add(newLists.disk);	lists.add(newLists.vga);
		lists.add(newLists.psu);	lists.add(newLists.crate);
	}
	
	public void updateInputs(){
		inputs = new ArrayList<ArrayList<String>>();
		
		for(int i = 0; i < comboBoxes.size(); i++) {
			inputs.add(new ArrayList<String>());
			
			for(int j = 0; j < comboBoxes.get(i).size(); j++) {
				String chosen = comboBoxes.get(i).get(j).getTextField().getText();
				
				if((lists.get(i).indexOf(chosen) >= 0 && !chosen.equals("請選擇")) || customMatched(chosen, i)){
					if(i != MEM) {
						inputs.get(i).add(chosen);
					}
					
					else {
						for(int k = 0
							; k < (Integer)(subComponentPanes.get(i).get(j).getSpinner().getValue())
							; k++) {
							
							inputs.get(i).add(chosen);
						}
					}
				}
			}
		}
		
		for(ArrayList<String> list: inputs) {
			for(String s: list) {
				System.out.print(s + " ");
			}
			System.out.println();
		}
	}
	
	private class ComboBoxListener implements ItemListener {
		@Override
		public void itemStateChanged(ItemEvent event) {
			if(event.getStateChange() == ItemEvent.SELECTED) {
				System.out.println("comboBox");
				
				for(int i = 0; i < comboBoxes.size(); i++) {
					for(int j = 0; j < comboBoxes.get(i).size(); j++) {
						if(event.getSource() == comboBoxes.get(i).get(j)) {
							if(lists.get(i).indexOf(String.valueOf(comboBoxes.get(i).get(j).getSelectedItem())) >= 0) {
								refresh();
							}
							return;
						}
					}
				}
			}
		}
	}
	
	private class SpinnerListener implements ChangeListener {

		@Override
		public void stateChanged(ChangeEvent event) {
			System.out.println("spinner");
			refresh();
		}
	}
	
	private class PlusBtnListener implements ActionListener
	{
		@Override
		public void actionPerformed(ActionEvent event) {
			int current;
			
			if(event.getSource() == plusButtons[0]) {
				current = MEM;
			}
			else if(event.getSource() == plusButtons[1]) {
				current = DISK;
			}
			else {
				current = VGA;
			}
			
			subComponentPanes.get(current).add(new ComponentPanel(lists.get(current), current));
			
			spinners.add(subComponentPanes.get(current).get(subComponentPanes.get(current).size() - 1).getSpinner());
			spinners.get(spinners.size() - 1).addChangeListener(new SpinnerListener());
			
			gearButtons.get(current).add(subComponentPanes.get(current).get(subComponentPanes.get(current).size() - 1).getGear());
			gearButtons.get(current).get(gearButtons.get(current).size() - 1).addActionListener(new GearListener());
			
			subComponentPanes.get(current).get(0).setSubBtn(true);
			
			subComponentPanes.get(current).get(subComponentPanes.get(current).size() - 1).setBorder(BorderFactory.createTitledBorder("​"));
			((TitledBorder)subComponentPanes.get(current).get(subComponentPanes.get(current).size() - 1).getBorder()).setTitleFont(title);
			
			subButtons.get(current).add(subComponentPanes.get(current).get(subComponentPanes.get(current).size() - 1).getSubBtn());
			subButtons.get(current).get(subButtons.get(current).size() - 1).addActionListener(new SubBtnListener());

			comboBoxes.get(current).add(subComponentPanes.get(current).get(subComponentPanes.get(current).size() - 1).getComboBox());
			comboBoxes.get(current).get(comboBoxes.get(current).size() - 1).addItemListener(new ComboBoxListener());
			
			int count = 0, i = 0, j = 0;
			
			for(i = 0; i <= current ; i++) {
				for(j = 0; j < subComponentPanes.get(i).size(); j++) {
					count ++;
				}
			}
			
			optionPane.add(subComponentPanes.get(current).get(subComponentPanes.get(current).size() - 1), count - 1);
			
			subPlusBtnPanes.add(count - 1, new JPanel());
			subPlusBtnPanes.get(count - 1).setBackground(new Color(231, 242, 255));
			subPlusBtnPanes.get(count - 1).setOpaque(true);
			

			subPlusBtnPanes.get(count - 1).setBorder(BorderFactory.createLineBorder(Color.lightGray));
			
			plusPane.add(subPlusBtnPanes.get(count - 1), count - 1);
			plusPane.setBackground(new Color(231, 242, 255));
			plusPane.setOpaque(true);
			
			MainFrame.this.revalidate();
		}
	}
	private class SubBtnListener implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent event) {
			int count = 0;
			
			for(int i = 0; i < subButtons.size(); i++) {
				for(int j = 0; j < subButtons.get(i).size(); j++, count++) {
					if(event.getSource() == subButtons.get(i).get(j)) {
						
						optionPane.remove(count);
						subComponentPanes.get(i).remove(j);
						gearButtons.get(i).remove(j);
						subButtons.get(i).remove(j);
						comboBoxes.get(i).remove(j);
						
						subPlusBtnPanes.remove(count + ((j!=0)?0:1));
						plusPane.remove(count + ((j!=0)?0:1));
						
						if(j == 0) {
							subComponentPanes.get(i).get(j).setBorder(BorderFactory.createTitledBorder(names[i]));
							((TitledBorder)subComponentPanes.get(i).get(j).getBorder()).setTitleFont(title);
						}
						
						if(subComponentPanes.get(i).size() == 1) {
							subComponentPanes.get(i).get(0).setSubBtn(false);
						}
						
						break;
					}
				}
			}
			
			MainFrame.this.revalidate();
			
			refresh();
		}
	}
	
	private class GearListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent event) {
			
			for(int i = 0; i < gearButtons.size(); i++) {
				for(int j = 0; j < gearButtons.get(i).size(); j++) {
					
					if(event.getSource() == gearButtons.get(i).get(j)) {
						
						switch(i) {
							case COOL:
								runCoolerSubFrame();
								break;
							case MEM:
								runMemorySubFrame();
								break;
							case DISK:
								runDiskSubFrame();
								break;
							case VGA:
								runVgaSubFrame();
								break;
							case PSU:
								runPsuSubFrame();
								break;
							case CASE:
								ruCaseSubFrame();
								break;
						}
						
						comboBoxes.get(i).get(j).getTextField().setText(subFrameFeedback);
						refresh();
					}
				}
			}
		}
	}
	
	private void runCoolerSubFrame() {
		
		JDialog coolerFrame = new CoolerSubFrame(this);
		
		coolerFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		
		coolerFrame.setSize(280, 120);
		coolerFrame.setResizable(false);
		coolerFrame.setLocationRelativeTo(MainFrame.this);
		coolerFrame.setVisible(true);
	}
	
	private void runMemorySubFrame(){
			
		JDialog memFrame = new MemorySubFrame(MainFrame.this);
		
		memFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		
		memFrame.setSize(250, 150);
		memFrame.setResizable(false);
		memFrame.setLocationRelativeTo(MainFrame.this);
		memFrame.setVisible(true);
	}
	
	private void runDiskSubFrame() {
			
		JDialog diskFrame = new DiskSubFrame(this);
		
		diskFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		
		diskFrame.setSize(250, 200);
		diskFrame.setResizable(false);
		diskFrame.setLocationRelativeTo(MainFrame.this);
		diskFrame.setVisible(true);
	}
	
	private void runVgaSubFrame() {
			
		JDialog vgaFrame = new VgaSubFrame(this);
		
		vgaFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		
		vgaFrame.setSize(280, 170);
		vgaFrame.setResizable(false);
		vgaFrame.setLocationRelativeTo(MainFrame.this);
		vgaFrame.setVisible(true);
	}
	private void runPsuSubFrame() {
		
		JDialog psuFrame = new PsuSubFrame(this);
		
		psuFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		
		psuFrame.setSize(300, 200);
		psuFrame.setResizable(false);
		psuFrame.setLocationRelativeTo(MainFrame.this);
		psuFrame.setVisible(true);
	}
	private void ruCaseSubFrame() {
		
		JDialog caseFrame = new CaseSubFrame(this);
		
		caseFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		
		caseFrame.setSize(380, 280);
		caseFrame.setResizable(false);
		caseFrame.setLocationRelativeTo(MainFrame.this);
		caseFrame.setVisible(true);
	}
}