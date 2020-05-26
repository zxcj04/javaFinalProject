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
	public static final int CPU  = 0; public static final int COOL = 1;
	public static final int MB   = 2; public static final int MEM  = 3;
	public static final int DISK = 4; public static final int VGA  = 5;
	public static final int PSU  = 6; public static final int CASE = 7;
	
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
			subButtons.add(new ArrayList<JButton>());
		}
		
		// set this
		this.setLayout(new BorderLayout());
		
		masterPane.setLayout(new BorderLayout());
		
		// option Panel
		optionPane.setLayout(new GridLayout(0, 1));
		
		masterPane.add(optionPane, BorderLayout.CENTER);
		
		// components Panel
		for(int i = 0; i < lists.size(); i++) {
			subComponentPanes.get(i).add(new ComponentPanel(lists.get(i), i, true));
			optionPane.add(subComponentPanes.get(i).get(0));
			
			subComponentPanes.get(i).get(0).setBorder(BorderFactory.createTitledBorder(names[i]));
			((TitledBorder)subComponentPanes.get(i).get(0).getBorder()).setTitleFont(title);
			
			subButtons.get(i).add(subComponentPanes.get(i).get(0).getSubBtn());
			subButtons.get(i).get(0).addActionListener(new SubListener());
			
			comboBoxes.get(i).add(subComponentPanes.get(i).get(0).getComboBox());
			inputs.get(i).add("");
			comboBoxes.get(i).get(0).addItemListener(new comboBoxListener());	
			
			if(i != CPU && i != MB) {
				gearButtons.get(i).add(subComponentPanes.get(i).get(0).getGear());
			}
		}
		
		gearButtons.get(MEM).get(0).addActionListener(new MemoryListener());
		gearButtons.get(DISK).get(0).addActionListener(new DiskListener());
		gearButtons.get(VGA).get(0).addActionListener(new VgaListener());
		
		
		// plus Button Panel
		plusPane.setLayout(new GridLayout(0, 1, 0, 0));
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
			
			subPlusBtnPane.get(i).setBorder(BorderFactory.createLineBorder(Color.lightGray));
			
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
								inputs.get(i).set(j, "");
							}
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
			int current;
			
			if(event.getSource() == plusButtons[0]) {
				current = MEM;
				
				subComponentPanes.get(current).add(new ComponentPanel(lists.get(current), current, false));
				gearButtons.get(current).add(subComponentPanes.get(current).get(subComponentPanes.get(current).size() - 1).getGear());
				gearButtons.get(current).get(gearButtons.get(current).size() - 1).addActionListener(new MemoryListener());
			}
			else if(event.getSource() == plusButtons[1]) {
				current = DISK;
				
				subComponentPanes.get(current).add(new ComponentPanel(lists.get(current), current, false));
				gearButtons.get(current).add(subComponentPanes.get(current).get(subComponentPanes.get(current).size() - 1).getGear());
				gearButtons.get(current).get(gearButtons.get(current).size() - 1).addActionListener(new DiskListener());
			}
			else {
				current = VGA;
				
				subComponentPanes.get(current).add(new ComponentPanel(lists.get(current), current, false));
				gearButtons.get(current).add(subComponentPanes.get(current).get(subComponentPanes.get(current).size() - 1).getGear());
				gearButtons.get(current).get(gearButtons.get(current).size() - 1).addActionListener(new VgaListener());
			}
			
			subComponentPanes.get(current).get(0).setSubBtn(true);
			
			subComponentPanes.get(current).get(subComponentPanes.get(current).size() - 1).setBorder(BorderFactory.createTitledBorder("​"));
			((TitledBorder)subComponentPanes.get(current).get(subComponentPanes.get(current).size() - 1).getBorder()).setTitleFont(title);
			
			subButtons.get(current).add(subComponentPanes.get(current).get(subComponentPanes.get(current).size() - 1).getSubBtn());
			subButtons.get(current).get(subButtons.get(current).size() - 1).addActionListener(new SubListener());

			comboBoxes.get(current).add(subComponentPanes.get(current).get(subComponentPanes.get(current).size() - 1).getComboBox());
			comboBoxes.get(current).get(comboBoxes.get(current).size() - 1).addItemListener(new comboBoxListener());
			
			inputs.get(current).add("");
			
			int count = 0, i = 0, j = 0;
			
			for(i = 0; i <= current ; i++) {
				for(j = 0; j < subComponentPanes.get(i).size(); j++) {
					count ++;
				}
			}
			
			optionPane.add(subComponentPanes.get(current).get(subComponentPanes.get(current).size() - 1), count - 1);
			
			subPlusBtnPane.add(count - 1, new JPanel());
			subPlusBtnPane.get(count - 1).setBackground(new Color(231, 242, 255));
			subPlusBtnPane.get(count - 1).setOpaque(true);
			

			subPlusBtnPane.get(count - 1).setBorder(BorderFactory.createLineBorder(Color.lightGray));
			
			plusPane.add(subPlusBtnPane.get(count - 1), count - 1);
			plusPane.setBackground(new Color(231, 242, 255));
			plusPane.setOpaque(true);
			
			MainFrame.this.revalidate();
		}
	}
	private class SubListener implements ActionListener{
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
	
	private class MemoryListener implements ActionListener{
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
	
	private class DiskListener implements ActionListener{
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
	
	private class VgaListener implements ActionListener{
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