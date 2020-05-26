package maingui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.Dialog.ModalityType;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.SpinnerModel;
import javax.swing.SpinnerNumberModel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

// m.2 \ pcie sata
// ssd hdd \ 2.5" 3.5"

public class DiskSubFrame extends JDialog {
	FilterComboBox type;
	FilterComboBox size;
	JSpinner capacity;
	FilterComboBox unit;
	JButton set = new JButton("確定");
	
	public DiskSubFrame(MainFrame parent) {
		this.setTitle("自訂硬碟");
		
		this.setModal(true);
		this.setModalityType(ModalityType.APPLICATION_MODAL);
		
		this.setLayout(new GridLayout(0, 1));
		
		JPanel typePane = new JPanel();
		typePane.setBackground(new Color(231, 242, 255));
		typePane.setOpaque(true);
		
		ArrayList<String> typeList = new ArrayList<String>();
		typeList.add("m.2");
		typeList.add("ssd");
		typeList.add("hdd");
		
		type = new FilterComboBox(typeList);
		type.getTextField().setEditable(false);
		type.setPreferredSize(new Dimension(170, 32));
		
		type.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent event) {
				if(event.getStateChange() == ItemEvent.SELECTED) {
					if(String.valueOf(type.getSelectedItem()).equals("m.2")) {
						ArrayList<String> sizeList = new ArrayList<String>();
						sizeList.add("pcie");
						sizeList.add("sata");
						
						size.updateEntries(sizeList);
					}
					else {
						ArrayList<String> sizeList = new ArrayList<String>();
						sizeList.add("2.5\"");
						sizeList.add("3.5\"");
						
						size.updateEntries(sizeList);
					}
				}
			}
		});
		
		typePane.add(type);
		this.add(typePane);
		
		JPanel sizePane = new JPanel();
		sizePane.setBackground(new Color(231, 242, 255));
		sizePane.setOpaque(true);
		
		ArrayList<String> sizeList = new ArrayList<String>();
		sizeList.add("pcie");
		sizeList.add("sata");
		
		size = new FilterComboBox(sizeList);
		size.getTextField().setEditable(false);
		size.setPreferredSize(new Dimension(100, 32));

		sizePane.add(size);
		this.add(sizePane);
		
		JPanel capPane = new JPanel();
		capPane.setBackground(new Color(231, 242, 255));
		capPane.setOpaque(true);
		
		SpinnerModel model = new SpinnerNumberModel(1, Integer.MIN_VALUE, Integer.MAX_VALUE, 1);
		capacity = new JSpinner(model);
		capacity.setPreferredSize(new Dimension(100, 32));
		((JSpinner.NumberEditor)capacity.getEditor()).getTextField().setBackground(new Color(215, 225, 238));
		
		Font font = new Font("Monospaced", Font.BOLD, 16);
		((JSpinner.NumberEditor)capacity.getEditor()).getTextField().setFont(font);
		
		capacity.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent event) {
				if(unit.getTextField().getText().equals("G") && (Integer)capacity.getValue() >= 1000) {
					ArrayList<String> list = new ArrayList<String>();
					list.add("T");
					list.add("G");
					
					unit.updateEntries(list);
					
					capacity.setValue((Integer)capacity.getValue() / 1000);
				}
				else if(unit.getTextField().getText().equals("T") && (Integer)capacity.getValue() > 64) {
					capacity.setValue(64);
				}
				else if((Integer)capacity.getValue() <= 0) {
					capacity.setValue(1);
				}
			}
		});
		
		capPane.add(capacity);
		
		ArrayList<String> unitList = new ArrayList<String>();
		unitList.add("G");
		unitList.add("T");
		unit = new FilterComboBox(unitList);
		unit.getTextField().setEditable(false);
		unit.setPreferredSize(new Dimension(50, 32));
		
		unit.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent event) {
				if(event.getStateChange() == ItemEvent.SELECTED) {
					capacity.setValue(1);
				}
			}
		});
		
		capPane.add(unit);
		
		this.add(capPane);
		
		JPanel btnPane = new JPanel();
		btnPane.setBackground(new Color(231, 242, 255));
		btnPane.setOpaque(true);
		
		set.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event) {
				String chosen = "custom ";
				chosen += type.getTextField().getText();
				chosen += " ";
				chosen += size.getTextField().getText();
				chosen += " ";
				chosen += ((JSpinner.NumberEditor)capacity.getEditor()).getTextField().getText();
				chosen += unit.getTextField().getText();
				
				parent.setFeedback(chosen);
				DiskSubFrame.this.dispose();
			}
		});
		
		btnPane.add(set);
		this.add(btnPane);
	}
}