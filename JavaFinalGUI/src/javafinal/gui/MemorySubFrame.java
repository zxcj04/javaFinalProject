package javafinal.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Dialog.ModalityType;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class MemorySubFrame extends JDialog {
	FilterComboBox type;
	FilterComboBox capacity;
	JButton set = new JButton("確定");
	
	public MemorySubFrame(MainFrame parent, boolean memoryIsCustomized, String memoryType) {
		this.setTitle("自訂記憶體");
		
		this.setModal(true);
		this.setModalityType(ModalityType.APPLICATION_MODAL);
		
		this.setLayout(new GridLayout(0, 1));
		
		JPanel typePane = new JPanel();
		typePane.setBackground(new Color(231, 242, 255));
		typePane.setOpaque(true);
		
		ArrayList<String> typeList = new ArrayList<String>();
		typeList.add("ddr4");
		typeList.add("ddr3");
		typeList.add("ddr2");
		typeList.add("ddr1");
		
		if(memoryIsCustomized) {
			typeList.remove(typeList.indexOf(memoryType));
			typeList.add(0, memoryType);
		}
		
		type = new FilterComboBox(typeList);
		type.getTextField().setEditable(false);
		type.setPreferredSize(new Dimension(145, 32));
		
		if(memoryIsCustomized) {
			type.setEnabled(false);
		}
		
		typePane.add(type);
		this.add(typePane);
		
		JPanel capPane = new JPanel();
		capPane.setBackground(new Color(231, 242, 255));
		capPane.setOpaque(true);
		
		ArrayList<String> capList = new ArrayList<String>();
		capList.add("1");
		capList.add("2");
		capList.add("4");
		capList.add("8");
		capList.add("16");
		capList.add("32");
		
		capacity = new FilterComboBox(capList);
		capacity.getTextField().setEditable(false);
		
		JLabel giga = new JLabel("G");
		Font g = new Font("Monospaced", Font.BOLD, 20);
		giga.setFont(g);

		capPane.add(capacity);
		capPane.add(giga);
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
				chosen += capacity.getTextField().getText();
				chosen += "G";
				
				parent.setFeedback(chosen);
				
				MemorySubFrame.this.dispose();
			}
		});
		
		btnPane.add(set);
		this.add(btnPane);
	}
}