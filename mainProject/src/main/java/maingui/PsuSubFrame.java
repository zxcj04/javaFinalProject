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
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerModel;
import javax.swing.SpinnerNumberModel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class PsuSubFrame extends JDialog {
	JSpinner length;
	JSpinner watt;
	JComboBox<String> size;
	JButton set = new JButton("確定");
	
	public PsuSubFrame(MainFrame parent, OptionPanel brother) {
		super(parent);
		this.setTitle("自訂電源供應器");
		
		this.setModal(true);
		this.setModalityType(ModalityType.APPLICATION_MODAL);
		
		this.setLayout(new GridLayout(0, 1));
		
		JPanel lenPane = new JPanel();
		lenPane.setBackground(new Color(231, 242, 255));
		lenPane.setOpaque(true);
		
		JLabel label = new JLabel("長度: ");
		label.setFont(MainFrame.font);
		
		lenPane.add(label);
		
		SpinnerModel lenModel = new SpinnerNumberModel(1, Integer.MIN_VALUE, Integer.MAX_VALUE, 1);
		length = new JSpinner(lenModel);
		length.setPreferredSize(new Dimension(130, 32));
		((JSpinner.NumberEditor)length.getEditor()).getTextField().setBackground(new Color(215, 225, 238));
		
		((JSpinner.NumberEditor)length.getEditor()).getTextField().setFont(MainFrame.font);
		
		length.addChangeListener(new spinnerListener());
		lenPane.add(length);
		
		JLabel unitCm = new JLabel("cm");
		unitCm.setFont(MainFrame.font);
		
		lenPane.add(unitCm);
		
		this.add(lenPane);
		
		JPanel wattPane = new JPanel();
		wattPane.setBackground(new Color(231, 242, 255));
		wattPane.setOpaque(true);
		
		JLabel label1 = new JLabel("瓦數: ");
		label1.setFont(MainFrame.font);
		
		wattPane.add(label1);
		
		SpinnerModel wattModel = new SpinnerNumberModel(1, Integer.MIN_VALUE, Integer.MAX_VALUE, 1);
		watt = new JSpinner(wattModel);
		watt.setPreferredSize(new Dimension(138, 32));
		((JSpinner.NumberEditor)watt.getEditor()).getTextField().setBackground(new Color(215, 225, 238));
		
		((JSpinner.NumberEditor)watt.getEditor()).getTextField().setFont(MainFrame.font);
		
		watt.addChangeListener(new spinnerListener());
		wattPane.add(watt);
		
		JLabel unitWatt = new JLabel("W");
		unitWatt.setFont(MainFrame.font);
		
		wattPane.add(unitWatt);
		
		this.add(wattPane);
		
		JPanel sizePane = new JPanel();
		sizePane.setBackground(new Color(231, 242, 255));
		sizePane.setOpaque(true);
		
		size = new JComboBox<String>();
		size.setEditable(false);
		size.setPreferredSize(new Dimension(90, 32));
		size.setBackground(new Color(215, 225, 238));
		size.setFont(MainFrame.font);

		size.addItem("ATX");
		size.addItem("SFX");
		
		sizePane.add(size);
		this.add(sizePane);
		
		JPanel btnPane = new JPanel();
		btnPane.setBackground(new Color(231, 242, 255));
		btnPane.setOpaque(true);
		
		set.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event) {
				String chosen = "custom ";
				chosen += ((JSpinner.NumberEditor)length.getEditor()).getTextField().getText();
				chosen += "cm ";
				chosen += ((JSpinner.NumberEditor)watt.getEditor()).getTextField().getText();
				chosen += "W ";
				chosen += (String)size.getSelectedItem();
				
				brother.setFeedback(chosen);
				PsuSubFrame.this.dispose();
			}
		});
		
		btnPane.add(set);
		this.add(btnPane);
	}
	
	private class spinnerListener implements ChangeListener {
		@Override
		public void stateChanged(ChangeEvent event) {
			if(event.getSource() == length) {
				if((Integer)length.getValue() >= 1000) {
					length.setValue(999);
				}
				else if((Integer)length.getValue() <= 0) {
					length.setValue(1);
				}
			}
			else {
				if((Integer)watt.getValue() >= 1000) {
					watt.setValue(999);
				}
				else if((Integer)watt.getValue() <= 0) {
					watt.setValue(1);
				}
			}
		}
	}
}