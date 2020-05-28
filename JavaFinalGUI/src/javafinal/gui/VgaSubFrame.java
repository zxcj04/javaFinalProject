package javafinal.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.SpinnerModel;
import javax.swing.SpinnerNumberModel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class VgaSubFrame extends JDialog {
	JSpinner length;
	JSpinner tdp;
	JButton set = new JButton("確定");
	
	public VgaSubFrame(MainFrame parent, OptionPanel brother) {
		super(parent);
		this.setTitle("自訂顯示卡");
		
		this.setModal(true);
		this.setModalityType(ModalityType.APPLICATION_MODAL);
		
		this.setLayout(new GridLayout(0, 1));
		
		JPanel lenPane = new JPanel();
		lenPane.setBackground(new Color(231, 242, 255));
		lenPane.setOpaque(true);
		
		JLabel label = new JLabel("長度: ");
		label.setFont(MainFrame.font);
		
		lenPane.add(label);
		
		SpinnerModel model = new SpinnerNumberModel(1, Integer.MIN_VALUE, Integer.MAX_VALUE, 1);
		length = new JSpinner(model);
		length.setPreferredSize(new Dimension(130, 32));
		((JSpinner.NumberEditor)length.getEditor()).getTextField().setBackground(new Color(215, 225, 238));
		
		((JSpinner.NumberEditor)length.getEditor()).getTextField().setFont(MainFrame.font);
		
		length.addChangeListener(new spinnerListener());
		lenPane.add(length);
		
		JLabel unit = new JLabel("cm");
		unit.setFont(MainFrame.font);
		
		lenPane.add(unit);
		
		this.add(lenPane);
		
		JPanel tdpPane = new JPanel();
		tdpPane.setBackground(new Color(231, 242, 255));
		tdpPane.setOpaque(true);
		
		JLabel label1 = new JLabel("TDP: ");
		label1.setFont(MainFrame.font);
		
		tdpPane.add(label1);
		
		SpinnerModel tdpModel = new SpinnerNumberModel(1, Integer.MIN_VALUE, Integer.MAX_VALUE, 1);
		tdp = new JSpinner(tdpModel);
		tdp.setPreferredSize(new Dimension(135, 32));
		((JSpinner.NumberEditor)tdp.getEditor()).getTextField().setBackground(new Color(215, 225, 238));
		
		((JSpinner.NumberEditor)tdp.getEditor()).getTextField().setFont(MainFrame.font);
		
		tdp.addChangeListener(new spinnerListener());
		tdpPane.add(tdp);
		
		JLabel unitWatt = new JLabel("W");
		unitWatt.setFont(MainFrame.font);
		
		tdpPane.add(unitWatt);
		
		this.add(tdpPane);
		
		JPanel btnPane = new JPanel();
		btnPane.setBackground(new Color(231, 242, 255));
		btnPane.setOpaque(true);
		
		set.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event) {
				String chosen = "custom ";
				chosen += ((JSpinner.NumberEditor)length.getEditor()).getTextField().getText();
				chosen += "cm ";
				chosen += ((JSpinner.NumberEditor)tdp.getEditor()).getTextField().getText();
				chosen += "W";
				
				brother.setFeedback(chosen);
				VgaSubFrame.this.dispose();
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
				if((Integer)tdp.getValue() >= 1000) {
					tdp.setValue(999);
				}
				else if((Integer)tdp.getValue() <= 0) {
					tdp.setValue(1);
				}
			}
		}
	}
}