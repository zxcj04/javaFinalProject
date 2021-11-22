package maingui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class MemorySubFrame extends JDialog {
	JComboBox<String> type;
	JComboBox<String> capacity;
	JButton set = new JButton("確定");
	
	public MemorySubFrame(MainFrame parent, OptionPanel brother, boolean memoryIsFixed, String fixedType, boolean memoryIsCustomized, String memoryType) {
		super(parent);
		this.setTitle("自訂記憶體");
		
		this.setModal(true);
		this.setModalityType(ModalityType.APPLICATION_MODAL);
		
		this.setLayout(new GridLayout(0, 1));
		
		JPanel typePane = new JPanel();
		typePane.setBackground(new Color(231, 242, 255));
		typePane.setOpaque(true);
		
		type = new JComboBox<String>();
		type.setEditable(false);
		type.setPreferredSize(new Dimension(145, 32));
		type.setBackground(new Color(215, 225, 238));
		type.setFont(MainFrame.font);

		type.addItem("ddr4");
		type.addItem("ddr3");
		type.addItem("ddr2");
		type.addItem("ddr1");
		
		if(parent.getSmartModeBtnPanel().getToRefresh()) {
			
			if(memoryIsFixed) {
				type.removeAllItems();
				type.addItem(fixedType);
			}
			else if(memoryIsCustomized) {
				type.removeAllItems();
				type.addItem(memoryType);
			}
			
			type.revalidate();
		}
		
		
		if(parent.getSmartModeBtnPanel().getToRefresh() && (memoryIsFixed || memoryIsCustomized)) {
			type.setEnabled(false);
		}
		
		typePane.add(type);
		this.add(typePane);
		
		JPanel capPane = new JPanel();
		capPane.setBackground(new Color(231, 242, 255));
		capPane.setOpaque(true);
		
		capacity = new JComboBox<String>();
		capacity.setEditable(false);
		capacity.setBackground(new Color(215, 225, 238));
		capacity.setFont(MainFrame.font);
		
		capacity.addItem("1");
		capacity.addItem("2");
		capacity.addItem("4");
		capacity.addItem("8");
		capacity.addItem("16");
		capacity.addItem("32");
		
		JLabel giga = new JLabel("G");
		giga.setFont(MainFrame.font);

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
				
				chosen += (String)type.getSelectedItem();
				chosen += " ";
				chosen += (String)capacity.getSelectedItem();
				chosen += "G";
				
				brother.setFeedback(chosen);
				
				MemorySubFrame.this.dispose();
			}
		});
		
		btnPane.add(set);
		this.add(btnPane);
	}
}