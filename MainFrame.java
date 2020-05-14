import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JTextArea;
import javax.swing.JScrollPane;
import javax.swing.BorderFactory;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;
import javax.swing.border.EtchedBorder;
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
		}
		
		// set this
		this.setLayout(new BorderLayout());
		
		masterPane.setLayout(new BorderLayout());
		
		// option Panel
		optionPane.setLayout(new GridLayout(0, 1));
		optionPane.setBorder(BorderFactory.createLineBorder(Color.darkGray, 1));
		
		// components Panel
		for(int i = 0; i < lists.size(); i++) {
			subComponentPanes.get(i).add(new ComponentPanel(lists.get(i), i, true));
			optionPane.add(subComponentPanes.get(i).get(0));
			
			subComponentPanes.get(i).get(0).setBorder(BorderFactory.createTitledBorder(names[i]));
			((TitledBorder)subComponentPanes.get(i).get(0).getBorder()).setTitleFont(title);
		}
		
		masterPane.add(optionPane, BorderLayout.CENTER);
		
		// plus Button Panel
		plusPane.setLayout(new GridLayout(0, 1));
		plusPane.setBorder(BorderFactory.createLineBorder(Color.darkGray, 1));
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
		plusButtons[0] = new JButton(plus);
		plusButtons[1] = new JButton(plus);
		plusButtons[2] = new JButton(plus);
		subPlusBtnPane.get(MEM).add(plusButtons[0]);
		subPlusBtnPane.get(DISK).add(plusButtons[1]);
		subPlusBtnPane.get(VGA).add(plusButtons[2]);

		plusButtons[0].addActionListener(new PlusBtnListener());
		plusButtons[1].addActionListener(new PlusBtnListener());
		plusButtons[2].addActionListener(new PlusBtnListener());
		

		mPane = new JScrollPane(masterPane);
		mPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		mPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
//		mPane.setBackground(new Color(162, 193, 233));
//		mPane.setOpaque(true);
		mPane.getVerticalScrollBar().setUnitIncrement(16);
		mPane.getViewport().getView().setForeground(new Color(162, 193, 233));
		

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
	
	private class PlusBtnListener implements ActionListener
	{
		@Override
		public void actionPerformed(ActionEvent event) {
			int count = 0;
			int [] index = new int[8];
			
			if(event.getSource() == plusButtons[0]) {
				subComponentPanes.get(MEM).add(new ComponentPanel(lists.get(MEM), MEM, false));
				subComponentPanes.get(MEM).get(0).setSubBtn(true);
			}
			else if(event.getSource() == plusButtons[1]) {
				subComponentPanes.get(DISK).add(new ComponentPanel(lists.get(DISK), DISK, false));
				subComponentPanes.get(DISK).get(0).setSubBtn(true);
			}
			else {
				subComponentPanes.get(VGA).add(new ComponentPanel(lists.get(VGA), VGA, false));
				subComponentPanes.get(VGA).get(0).setSubBtn(true);
			}
			
			optionPane.removeAll();
			plusPane.removeAll();
			subPlusBtnPane.clear();
			
			int i = 0;
			String name;
			for(ArrayList<ComponentPanel> list : subComponentPanes) {
				index[i] = count;
				i++;
				for(ComponentPanel pane : list) {
					optionPane.add(pane);
					if(count == index[i-1])
						name = names[i-1];
					else
						name = String.format("%s - %d", names[i-1], count - index[i-1]);
					
					if(count == index[i - 1]) {
						pane.setBorder(BorderFactory.createTitledBorder(name));
						((TitledBorder)pane.getBorder()).setTitleFont(title);
					}
					else {
//						pane.setBorder(BorderFactory.createEmptyBorder(10, 5, 10, 5));
						pane.setBorder(BorderFactory.createTitledBorder("‌"));
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
}