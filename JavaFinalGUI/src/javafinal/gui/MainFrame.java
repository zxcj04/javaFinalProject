package javafinal.gui;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.ActionEvent;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JTextArea;
import javax.swing.JScrollPane;
import javax.swing.BorderFactory;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;

import java.util.ArrayList;

public class MainFrame extends JFrame
{
	public static final int CPU  = 0;
	public static final int COOL = 1;
	public static final int MB   = 2;
	public static final int MEM  = 3;
	public static final int DISK = 4;
	public static final int VGA  = 5;
	public static final int PSU  = 6;
	public static final int CASE = 7;
	
	private final Border line = BorderFactory.createLineBorder(Color.black);
	
	private final String[] names = {"CPU", "CPU Cooler", "MotherBoard", "Memory", "Disk", "Graphic",
							"PSU", "Computer Case"};
	
	private final Font title =  new Font("Monospaced", Font.BOLD, 20);
	
	private ArrayList<ArrayList<String>> inputs = new ArrayList<ArrayList<String>>();
	private ArrayList<ArrayList<String>> lists = new ArrayList<ArrayList<String>>();
	
	private ArrayList<JPanel> subPlusBtnPane = new ArrayList<JPanel>();
	private JPanel masterPane = new JPanel();
	private JScrollPane mPane = new JScrollPane();
	private JPanel optionPane = new JPanel();
	private JPanel plusPane = new JPanel();
	private JButton[] plusButtons = new JButton[3];
	private ArrayList<ArrayList<JComboBox>> comboBoxes = new ArrayList<ArrayList<JComboBox>>();
	private ArrayList<ArrayList<JButton>> subButtons = new ArrayList<ArrayList<JButton>>();
	private ArrayList<ArrayList<JButton>> gearButtons = new ArrayList<ArrayList<JButton>>();
	private JTextArea suggestion = new JTextArea();
	
	private ArrayList<ArrayList<ComponentPanel>> subComponentPanes = 
			new ArrayList<ArrayList<ComponentPanel>>();
	
	public MainFrame(ArrayList<String> cpuList,
					 ArrayList<String> cpuCoolerList,
					 ArrayList<String> motherBoardList,
					 ArrayList<String> memoryList,
					 ArrayList<String> diskList,
					 ArrayList<String> vgaList,
					 ArrayList<String> psuList,
					 ArrayList<String> caseList)
	{
		super("大力出奇機");
		
		lists.add(cpuList);
		lists.add(cpuCoolerList);
		lists.add(motherBoardList);
		lists.add(memoryList);
		lists.add(diskList);
		lists.add(vgaList);
		lists.add(psuList);
		lists.add(caseList);

		for(int i = 0; i < lists.size(); i++) {
			inputs.add(new ArrayList<String>());
			subComponentPanes.add(new ArrayList<ComponentPanel>());
			comboBoxes.add(new ArrayList<JComboBox>());
			gearButtons.add(new ArrayList<JButton>());
		}
		
		// set this
		this.setLayout(new BorderLayout());
		
		masterPane.setLayout(new BorderLayout());
		
		// option Panel
		optionPane.setLayout(new GridLayout(0, 1));
		
		// components Panel
		for(int i = 0; i < lists.size(); i++) {
			subComponentPanes.get(i).add(new ComponentPanel(lists.get(i), i, true));
			optionPane.add(subComponentPanes.get(i).get(0));
			
			subComponentPanes.get(i).get(0).setBorder(BorderFactory.createTitledBorder(names[i]));
			((TitledBorder)subComponentPanes.get(i).get(0).getBorder()).setTitleFont(title);
			
			
			comboBoxes.get(i).add(subComponentPanes.get(i).get(0).getComboBox());
			inputs.get(i).add("Hi");
			comboBoxes.get(i).get(0).addItemListener(new comboBoxListener());	
			
			if(i != CPU && i != MB) {
				gearButtons.get(i).add(subComponentPanes.get(i).get(0).getGear());
			}
		}
		
		gearButtons.get(MEM).get(0).addActionListener(new memoryListener());
		gearButtons.get(DISK).get(0).addActionListener(new diskListener());
		gearButtons.get(VGA).get(0).addActionListener(new vgaListener());
		
		masterPane.add(optionPane, BorderLayout.CENTER);
		
		// plus Button Panel
		plusPane.setLayout(new GridLayout(0, 1));
		plusPane.setBorder(BorderFactory.createLineBorder(Color.lightGray, 1));
		plusPane.setBackground(new Color(231, 242, 255));
		plusPane.setOpaque(true);

		masterPane.add(plusPane, BorderLayout.EAST);
		
		for(int i = 0; i < lists.size(); i++) {
			subPlusBtnPane.add(new JPanel());
			subPlusBtnPane.get(i).setLayout(new BoxLayout(subPlusBtnPane.get(i), BoxLayout.LINE_AXIS));
			subPlusBtnPane.get(i).setAlignmentY(Component.CENTER_ALIGNMENT);
			subPlusBtnPane.get(i).setBackground(new Color(231, 242, 255));
			subPlusBtnPane.get(i).setOpaque(true);
			plusPane.add(subPlusBtnPane.get(i));
		}
		ImageIcon plus = new ImageIcon(getClass().getResource("plus.jpg"));
		
		for(int i = 0; i < 3; i++) {
			plusButtons[i] = new JButton(plus);
			plusButtons[i].setPreferredSize(new Dimension(30, 30));
			plusButtons[i].addActionListener(new PlusBtnListener());
		}
		subPlusBtnPane.get(MEM).add(plusButtons[0]);
		subPlusBtnPane.get(DISK).add(plusButtons[1]);
		subPlusBtnPane.get(VGA).add(plusButtons[2]);
		

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
		suggestion.setLineWrap(true);
		suggestion.setEditable(false);
		suggestion.setBackground(new Color(233, 233, 233));
		suggestion.setOpaque(true);
		JScrollPane scrollSuggestion = new JScrollPane(suggestion);
		suggestionPane.add(scrollSuggestion, BorderLayout.CENTER);
	}
	
	public ArrayList<ArrayList<String>> updateInputs(){
		// when sub Button Listened
		// when comboBox Listened
		return inputs;
	}
	
	private class comboBoxListener implements ItemListener {
		@Override
		public void itemStateChanged(ItemEvent event) {
			for(int i = 0; i < comboBoxes.size(); i++) {
				for(int j = 0; j < comboBoxes.get(i).size(); j++) {
					if(event.getSource() == comboBoxes.get(i).get(j) &&
					   event.getStateChange() == ItemEvent.SELECTED) {
						try {
							String choosen = lists.get(i).get(comboBoxes.get(i).get(j).getSelectedIndex());
							
							if(!choosen.equals("請選擇"))
								inputs.get(i).set(j, choosen);
							else {
								inputs.get(i).set(j, "Hi");
							}
							
//							for(ArrayList<String> list: inputs) {
//								for(String item : list) {
//									System.out.print(item + " ");
//								}
//								System.out.println();
//							}
						}catch(Exception e){}
						
						return;
					}
				}
			}
		}
	}
	
	private class PlusBtnListener implements ActionListener
	{
		@Override
		public void actionPerformed(ActionEvent event) {
			int count = 0;
			int [] index = new int[8];
			
			if(event.getSource() == plusButtons[0]) {
				subComponentPanes.get(MEM).add(new ComponentPanel(lists.get(MEM), MEM, false));
				subComponentPanes.get(MEM).get(0).setSubBtn(true);
				
				comboBoxes.get(MEM).add(subComponentPanes.get(MEM).get(subComponentPanes.get(MEM).size() - 1).getComboBox());
				comboBoxes.get(MEM).get(comboBoxes.get(MEM).size() - 1).addItemListener(new comboBoxListener());
				
				inputs.get(MEM).add("Hi");
				
				gearButtons.get(MEM).add(subComponentPanes.get(MEM).get(subComponentPanes.get(MEM).size() - 1).getGear());
				gearButtons.get(MEM).get(gearButtons.get(MEM).size() - 1).addActionListener(new memoryListener());
			}
			else if(event.getSource() == plusButtons[1]) {
				subComponentPanes.get(DISK).add(new ComponentPanel(lists.get(DISK), DISK, false));
				subComponentPanes.get(DISK).get(0).setSubBtn(true);
				
				comboBoxes.get(DISK).add(subComponentPanes.get(DISK).get(subComponentPanes.get(DISK).size() - 1).getComboBox());
				comboBoxes.get(DISK).get(comboBoxes.get(DISK).size() - 1).addItemListener(new comboBoxListener());
				
				inputs.get(DISK).add("Hi");
				
				gearButtons.get(DISK).add(subComponentPanes.get(DISK).get(subComponentPanes.get(DISK).size() - 1).getGear());
				gearButtons.get(DISK).get(gearButtons.get(DISK).size() - 1).addActionListener(new diskListener());
			}
			else {
				subComponentPanes.get(VGA).add(new ComponentPanel(lists.get(VGA), VGA, false));
				subComponentPanes.get(VGA).get(0).setSubBtn(true);

				comboBoxes.get(VGA).add(subComponentPanes.get(VGA).get(subComponentPanes.get(VGA).size() - 1).getComboBox());
				comboBoxes.get(VGA).get(comboBoxes.get(VGA).size() - 1).addItemListener(new comboBoxListener());
				
				inputs.get(VGA).add("Hi");
				
				gearButtons.get(VGA).add(subComponentPanes.get(VGA).get(subComponentPanes.get(VGA).size() - 1).getGear());
				gearButtons.get(VGA).get(gearButtons.get(VGA).size() - 1).addActionListener(new vgaListener());
			}
			
			optionPane.removeAll();
			plusPane.removeAll();
			subPlusBtnPane.clear();
			subButtons.clear();
			
			int i = 0;
			for(ArrayList<ComponentPanel> list : subComponentPanes) {
				index[i] = count;
				i++;
				subButtons.add(new ArrayList<JButton>());
				for(ComponentPanel pane : list) {
					(subButtons.get(i - 1)).add(pane.getSubBtn());
					pane.getSubBtn().addActionListener(new subListener());
					
					optionPane.add(pane);
					
					if(count == index[i - 1]) {
						pane.setBorder(BorderFactory.createTitledBorder(names[i - 1]));
						((TitledBorder)pane.getBorder()).setTitleFont(title);
					}
					else {
						pane.setBorder(BorderFactory.createTitledBorder("​"));
						((TitledBorder)pane.getBorder()).setTitleFont(title);
					}

					subPlusBtnPane.add(new JPanel());
					subPlusBtnPane.get(count).setLayout(new BoxLayout(subPlusBtnPane.get(count), BoxLayout.LINE_AXIS));
					subPlusBtnPane.get(count).setAlignmentY(Component.CENTER_ALIGNMENT);
					
					count++;
				}
			}
			
			subPlusBtnPane.get(index[MEM]).add(plusButtons[0]);
			subPlusBtnPane.get(index[DISK]).add(plusButtons[1]);
			subPlusBtnPane.get(index[VGA]).add(plusButtons[2]);
			
			plusPane.setBackground(new Color(231, 242, 255));
			plusPane.setOpaque(true);
			
			for(JPanel pane : subPlusBtnPane) {
				plusPane.add(pane);
				pane.setBackground(new Color(231, 242, 255));
				pane.setOpaque(true);
			}
			
			MainFrame.this.revalidate();
		}
	}
	private class subListener implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent event) {
			int count = 0;
			
			for(int i = 0; i < subButtons.size(); i++) {
				for(int j = 0; j < subButtons.get(i).size(); j++, count++) {
					if(event.getSource() == subButtons.get(i).get(j) &&
					   subComponentPanes.get(i).size() > 1) {
						
						optionPane.remove(count);
						subComponentPanes.get(i).remove(j);
						
						if(i != CPU && i != MB) {
							gearButtons.get(i).remove(j);
						}
						
						subButtons.get(i).remove(j);
						inputs.get(i).remove(j);
						
						if(j != 0) {
							subPlusBtnPane.remove(count);
							plusPane.remove(count);
						}
						else {
							plusPane.remove(count + 1);
							subPlusBtnPane.remove(count + 1);
						}
						
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
		}
	}
	
	private class memoryListener implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent event) {
			
			JDialog memFrame = new MemorySubFrame();
			
			memFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
			
			memFrame.setSize(250, 150);
			memFrame.setResizable(false);
			memFrame.setLocationRelativeTo(MainFrame.this);
			memFrame.setVisible(true);
		}
	}
	
	private class diskListener implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent event) {
			
			JDialog diskFrame = new DiskSubFrame();
			
			diskFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
			
			diskFrame.setSize(300, 200);
			diskFrame.setResizable(false);
			diskFrame.setLocationRelativeTo(MainFrame.this);
			diskFrame.setVisible(true);
		}
	}
	
	private class vgaListener implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent event) {
			
			JDialog vgaFrame = new VgaSubFrame();
			
			vgaFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
			
			vgaFrame.setSize(300, 120);
			vgaFrame.setResizable(false);
			vgaFrame.setLocationRelativeTo(MainFrame.this);
			vgaFrame.setVisible(true);
		}
	}
}