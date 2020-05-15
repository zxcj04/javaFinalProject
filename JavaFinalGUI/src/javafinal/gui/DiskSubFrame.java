package javafinal.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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

public class DiskSubFrame extends JDialog {
	FilterComboBox type;
	FilterComboBox size;
	JSpinner capacity;
	FilterComboBox unit;
	JButton set = new JButton("確定");
	
	public DiskSubFrame() {
		this.setTitle("自訂硬碟");
		
		this.setModal(true);
		this.setModalityType(ModalityType.APPLICATION_MODAL);
		
		this.setLayout(new GridLayout(0, 1));
		
		JPanel typePane = new JPanel();
		typePane.setBackground(new Color(231, 242, 255));
		typePane.setOpaque(true);
		
		ArrayList<String> typeList = new ArrayList<String>();
		typeList.add("m.2 pcie nvme");
		
		type = new FilterComboBox(typeList);
		type.getTextField().setEditable(false);
		type.setPreferredSize(new Dimension(170, 32));
		
		typePane.add(type);
		this.add(typePane);
		
		JPanel sizePane = new JPanel();
		sizePane.setBackground(new Color(231, 242, 255));
		sizePane.setOpaque(true);
		
		ArrayList<String> sizeList = new ArrayList<String>();
		sizeList.add("2.5\"");
		
		size = new FilterComboBox(sizeList);
		size.getTextField().setEditable(false);
		size.setPreferredSize(new Dimension(100, 32));

		sizePane.add(size);
		this.add(sizePane);
		
		JPanel capPane = new JPanel();
		capPane.setBackground(new Color(231, 242, 255));
		capPane.setOpaque(true);
		
		SpinnerModel model = new SpinnerNumberModel(1, 1, Integer.MAX_VALUE, 1);
		capacity = new JSpinner(model);
		capacity.setPreferredSize(new Dimension(100, 32));
		((JSpinner.NumberEditor)capacity.getEditor()).getTextField().setBackground(new Color(215, 225, 238));
		
		Font font = new Font("Monospaced", Font.BOLD, 16);
		((JSpinner.NumberEditor)capacity.getEditor()).getTextField().setFont(font);
		
		capPane.add(capacity);
		
		ArrayList<String> unitList = new ArrayList<String>();
		unitList.add("G");
		unitList.add("T");
		unit = new FilterComboBox(unitList);
		unit.getTextField().setEditable(false);
		unit.setPreferredSize(new Dimension(50, 32));
		
		capPane.add(unit);
		
		this.add(capPane);
		
		JPanel btnPane = new JPanel();
		btnPane.setBackground(new Color(231, 242, 255));
		btnPane.setOpaque(true);
		
		set.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event) {
				DiskSubFrame.this.dispose();
			}
		});
		
		btnPane.add(set);
		this.add(btnPane);
	}
}