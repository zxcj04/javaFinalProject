package maingui;

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
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class CoolerSubFrame extends JDialog {
	JSpinner height;
	JButton set = new JButton("確定");
	
	public CoolerSubFrame(MainFrame parent) {
		this.setTitle("自訂散熱器");
		
		this.setModal(true);
		this.setModalityType(ModalityType.APPLICATION_MODAL);
		
		this.setLayout(new GridLayout(0, 1));
		
		JPanel heightPane = new JPanel();
		heightPane.setBackground(new Color(231, 242, 255));
		heightPane.setOpaque(true);
		
		JLabel label = new JLabel("高度: ");
		Font font = new Font("Monospaced", Font.BOLD, 16);
		label.setFont(font);
		
		heightPane.add(label);
		
		SpinnerModel model = new SpinnerNumberModel(1, Integer.MIN_VALUE, Integer.MAX_VALUE, 1);
		height = new JSpinner(model);
		height.setPreferredSize(new Dimension(130, 32));
		((JSpinner.NumberEditor)height.getEditor()).getTextField().setBackground(new Color(215, 225, 238));
		
		((JSpinner.NumberEditor)height.getEditor()).getTextField().setFont(font);
		
		height.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent event) {
				if((Integer)height.getValue() >= 1000) {
					height.setValue(999);
				}
				else if((Integer)height.getValue() <= 0) {
					height.setValue(1);
				}
			}
		});
		heightPane.add(height);
		
		JLabel unit = new JLabel("cm");
		unit.setFont(font);
		
		heightPane.add(unit);
		
		this.add(heightPane);
		
		JPanel btnPane = new JPanel();
		btnPane.setBackground(new Color(231, 242, 255));
		btnPane.setOpaque(true);
		
		set.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event) {
				String chosen = "custom ";
				chosen += ((JSpinner.NumberEditor)height.getEditor()).getTextField().getText();
				chosen += "cm";
				
				parent.setFeedback(chosen);
				CoolerSubFrame.this.dispose();
			}
		});
		
		btnPane.add(set);
		this.add(btnPane);
	}
}