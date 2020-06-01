package javafinal.gui;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.border.TitledBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class OptionPanel extends JPanel
{
	private MainFrame parent;
	private ArrayList<ArrayList<ComponentPanel>> subComponentPanes;
	
	private ArrayList<ArrayList<FilterComboBox>> comboBoxes;
	private ArrayList<JSpinner> spinners;
	private ArrayList<ArrayList<JButton>> subButtons;
	private ArrayList<ArrayList<JButton>> gearButtons;
	
	private String subFrameFeedback;
	private boolean memoryIsCustomized;
	private String memoryType;
	
	public OptionPanel(MainFrame parent) {
		this.parent = parent;
		this.setLayout(new GridLayout(0, 1));
	}
	
	public void init() {
		this.removeAll();
		
		subComponentPanes = new ArrayList<ArrayList<ComponentPanel>>();
		comboBoxes = new ArrayList<ArrayList<FilterComboBox>>();
		gearButtons = new ArrayList<ArrayList<JButton>>();
		subButtons = new ArrayList<ArrayList<JButton>>();
		
		for(int i = 0; i <  parent.content.getLists().size(); i++) {
			subComponentPanes.add(new ArrayList<ComponentPanel>());
			comboBoxes.add(new ArrayList<FilterComboBox>());
			gearButtons.add(new ArrayList<JButton>());
			subButtons.add(new ArrayList<JButton>());
		}
		
		for(int i = 0; i < parent.content.getLists().size(); i++) {
			subComponentPanes.get(i).add(new ComponentPanel(parent.content.getLists().get(i), i));
			subComponentPanes.get(i).get(0).setSubBtn(false);
			this.add(subComponentPanes.get(i).get(0));
			
			subComponentPanes.get(i).get(0).setBorder(BorderFactory.createTitledBorder(MainFrame.names[i]));
			((TitledBorder)subComponentPanes.get(i).get(0).getBorder()).setTitleFont(MainFrame.title);
			
			subButtons.get(i).add(subComponentPanes.get(i).get(0).getSubBtn());
			
			comboBoxes.get(i).add(subComponentPanes.get(i).get(0).getComboBox());
			comboBoxes.get(i).get(0).addItemListener(new ComboBoxListener());	
			
			if(i != Content.CPU && i != Content.MB) {
				gearButtons.get(i).add(subComponentPanes.get(i).get(0).getGear());
				gearButtons.get(i).get(0).addActionListener(new GearListener());
			}
		}
		
		comboBoxes.get(Content.PSU).get(0).getTextField().addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent event) {
				if(event.getKeyCode() == KeyEvent.VK_ENTER
						&& comboBoxes.get(Content.PSU).get(0).getTextField().getText().contentEquals("sleep")) {
					comboBoxes.get(Content.PSU).get(0).getTextField().setText("請選擇");
					parent.switchCards("LOAD");
				}
			}
		});
		
		spinners = new ArrayList<JSpinner>();
		spinners.add(subComponentPanes.get(Content.MEM).get(0).getSpinner());
		spinners.get(0).addChangeListener(new SpinnerListener());
	}
	
	private class ComboBoxListener implements ItemListener {
		@Override
		public void itemStateChanged(ItemEvent event) {
			if(event.getStateChange() == ItemEvent.SELECTED) {
				
				for(int i = 0; i < comboBoxes.size(); i++) {
					for(int j = 0; j < comboBoxes.get(i).size(); j++) {
						if(event.getSource() == comboBoxes.get(i).get(j)) {
							if(parent.content.getLists().get(i).indexOf(String.valueOf(comboBoxes.get(i).get(j).getSelectedItem())) >= 0) {
								parent.refresh();
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
			parent.refresh();
		}
	}
	private class GearListener implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent event) {
			setFeedback("");
			
			for(int i = 0; i < gearButtons.size(); i++) {
				for(int j = 0; j < gearButtons.get(i).size(); j++) {
					
					if(event.getSource() == gearButtons.get(i).get(j)) {
						new RunCustomDialogs(parent, OptionPanel.this, i);
						
						if(!subFrameFeedback.isEmpty()) {

							if(i ==  Content.MEM && !memoryIsCustomized) {
								memoryType = subFrameFeedback.substring(7,11);
								memoryIsCustomized = true;
							}
							comboBoxes.get(i).get(j).getTextField().setText(subFrameFeedback);
							setFeedback("");
							parent.refresh();
						}
					}
				}
			}
		}
	}
	
	public int addComponent(int choice) {
		subComponentPanes.get(choice).
			add(new ComponentPanel(parent.content.getLists().get(choice), choice));
		
		spinners.
			add(subComponentPanes.get(choice).get(subComponentPanes.get(choice).size() - 1).getSpinner());
		spinners.get(spinners.size() - 1).addChangeListener(new SpinnerListener());
		
		gearButtons.get(choice).
			add(subComponentPanes.get(choice).get(subComponentPanes.get(choice).size() - 1).getGear());
		gearButtons.get(choice).get(gearButtons.get(choice).size() - 1).addActionListener(new GearListener());
		
		subComponentPanes.get(choice).get(0).setSubBtn(true);
		
		subComponentPanes.get(choice).get(subComponentPanes.get(choice).size() - 1)
			.setBorder(BorderFactory.createTitledBorder("​"));
		((TitledBorder)subComponentPanes.get(choice).get(subComponentPanes.get(choice).size() - 1)
			.getBorder()).setTitleFont(MainFrame.title);
		
		subButtons.get(choice).add(subComponentPanes.get(choice).get(subComponentPanes.get(choice).size() - 1).getSubBtn());

		comboBoxes.get(choice).add(subComponentPanes.get(choice).get(subComponentPanes.get(choice).size() - 1).getComboBox());
		comboBoxes.get(choice).get(comboBoxes.get(choice).size() - 1).addItemListener(new ComboBoxListener());
		
		int count = 0, i = 0, j = 0;
		
		for(i = 0; i <= choice ; i++) {
			for(j = 0; j < subComponentPanes.get(i).size(); j++) {
				count ++;
			}
		}
		
		this.add(subComponentPanes.get(choice).get(subComponentPanes.get(choice).size() - 1), count - 1);
		
		return count;
	}
	
	public void removeComponent(int count , int i, int j) {
		this.remove(count);
		subComponentPanes.get(i).remove(j);
		gearButtons.get(i).remove(j);
		subButtons.get(i).remove(j);
		comboBoxes.get(i).remove(j);
		if(j == 0) {
			subComponentPanes.get(i).get(j).setBorder(BorderFactory.createTitledBorder(MainFrame.names[i]));
			((TitledBorder)subComponentPanes.get(i).get(j).getBorder()).setTitleFont(MainFrame.title);
		}
		
		if(subComponentPanes.get(i).size() == 1) {
			subComponentPanes.get(i).get(0).setSubBtn(false);
		}
	}
	
	public void enableSwitch(boolean op) {
		for(int i = 0; i < subComponentPanes.size(); i++) {
			for(int j = 0; j < subComponentPanes.get(i).size(); j++) {
				comboBoxes.get(i).get(j).setEnabled(op);
				if(i != Content.CPU && i != Content.MB) {
					gearButtons.get(i).get(j).setEnabled(op);
				}
				subButtons.get(i).get(j).setEnabled(op);
				
				if(i == Content.MEM) {
					spinners.get(j).setEnabled(op);
				}
			}
		}
	}
	public void updateComboBoxes(boolean toRefresh) {
		for(int i = 0; i < comboBoxes.size(); i++) {
			if(toRefresh) { // smart mode
				parent.content.getLists().get(i).add(0, "請選擇");
			}
			for(int j = 0; j < comboBoxes.get(i).size(); j++) {
				String current = comboBoxes.get(i).get(j).getTextField().getText();
				int index = parent.content.getLists().get(i).indexOf(current);
				
				if(index >= 0 && !current.equals("請選擇")) {
					parent.content.getLists().get(i).remove(index);
					parent.content.getLists().get(i).add(0, current);
					comboBoxes.get(i).get(j).updateEntries(parent.content.getLists().get(i));
					parent.content.getLists().get(i).remove(0);
					parent.content.getLists().get(i).add(index, current);
				}
				else if(current.indexOf("custom") == 0) {
					if(parent.content.customMatched(current, i)) {
						parent.content.getLists().get(i).add(0, current);
						comboBoxes.get(i).get(j).updateEntries(parent.content.getLists().get(i));
						parent.content.getLists().get(i).remove(0);
						parent.content.getLists().get(i).add(current);
					}
				}
				else {
					comboBoxes.get(i).get(j).updateEntries(parent.content.getLists().get(i));
				}
			}
		}
		
		memoryIsCustomized = false;
		for(String s: parent.content.getLists().get(Content.MEM)) {
			if(s.contains("custom")) {
				memoryIsCustomized = true;
				break;
			}
		}
	}
	
	public ArrayList<ArrayList<JButton>> getSubButtons(){
		return subButtons;
	}
	public ArrayList<ArrayList<FilterComboBox>> getComboBoxes(){
		return comboBoxes;
	}
	public ArrayList<JSpinner> getSpinners(){
		return spinners;
	}
	
	public void setFeedback(String s) {
		subFrameFeedback = s;
	}
	public String getFeedback() {
		return subFrameFeedback;
	}
	public boolean getMemoryIsCustomized() {
		return memoryIsCustomized;
	}
	public String getMemoryType() {
		return memoryType;
	}
}