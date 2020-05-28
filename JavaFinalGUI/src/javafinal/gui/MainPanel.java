package javafinal.gui;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JPanel;

public class MainPanel extends JPanel
{
	private MainFrame parent;
	private OptionPanel optionPanel;
	private PlusBtnPanel plusPanel;
	JButton[] plusButtons;
	ArrayList<ArrayList<JButton>> subButtons;
	
	public MainPanel(MainFrame parent) {
		this.parent = parent;
		this.setLayout(new BorderLayout());
		
		// option Panel
		optionPanel = new OptionPanel(parent);
		optionPanel.init();
		this.add(optionPanel, BorderLayout.CENTER);
		
		// plus Button Panel
		plusPanel = new PlusBtnPanel(parent);
		plusPanel.init();
		this.add(plusPanel, BorderLayout.EAST);
	}
	
	public void init() {
		for(JButton btn: plusPanel.getPlusBtns()) {
			btn.addActionListener(new PlusBtnListener());
		}
		
		for(ArrayList<JButton> list: optionPanel.getSubButtons()) {
			for(JButton btn: list) {
				btn.addActionListener(new SubBtnListener());
			}
		}
	}
	
	private class PlusBtnListener implements ActionListener
	{
		@Override
		public void actionPerformed(ActionEvent event) {
			int current;
			
			if(event.getSource() == plusPanel.getPlusBtns()[0]) {
				current = Content.MEM;
			}
			else if(event.getSource() == plusPanel.getPlusBtns()[1]) {
				current =Content.DISK;
			}
			else {
				current = Content.VGA;
			}
			
			int count = optionPanel.addComponent(current);
			plusPanel.addPanel(count);
			
			optionPanel.getSubButtons().get(current).get(optionPanel.getSubButtons().get(current).size() - 1)
				.addActionListener(new SubBtnListener());
			
			parent.revalidate();
		}
		
	}
	private class SubBtnListener implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent event) {
			int count = 0;
			int i, j = 0;
			
			outer:
			for(i = 0; i < optionPanel.getSubButtons().size(); i++) {
				for(j = 0; j < optionPanel.getSubButtons().get(i).size(); j++, count++) {
					if(event.getSource() == optionPanel.getSubButtons().get(i).get(j)) {
						optionPanel.removeComponent(count, i, j);
						plusPanel.removePanel(count, i, j);
						break outer;
					}
				}
			}
			
			parent.revalidate();
			
			parent.refresh();
		}
	}
	
	public OptionPanel getOptionPanel() {
		return optionPanel;
	}
	public PlusBtnPanel getPlusBtnPanel() {
		return plusPanel;
	}
}