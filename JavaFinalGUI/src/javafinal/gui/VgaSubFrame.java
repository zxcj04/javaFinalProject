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

public class VgaSubFrame extends JDialog {
	JSpinner length;
	JButton set = new JButton("確定");
	
	public VgaSubFrame() {
		this.setTitle("自訂顯示卡");
		
		this.setModal(true);
		this.setModalityType(ModalityType.APPLICATION_MODAL);
		
		this.setLayout(new GridLayout(0, 1));
		
		JPanel lenPane = new JPanel();
		lenPane.setBackground(new Color(231, 242, 255));
		lenPane.setOpaque(true);
		
		JLabel label = new JLabel("長度: ");
		Font font = new Font("Monospaced", Font.BOLD, 16);
		label.setFont(font);
		
		lenPane.add(label);
		
		SpinnerModel model = new SpinnerNumberModel(1, 1, Integer.MAX_VALUE, 1);
		length = new JSpinner(model);
		length.setPreferredSize(new Dimension(130, 32));
		((JSpinner.NumberEditor)length.getEditor()).getTextField().setBackground(new Color(215, 225, 238));
		
		((JSpinner.NumberEditor)length.getEditor()).getTextField().setFont(font);
		lenPane.add(length);
		
		JLabel unit = new JLabel("cm");
		unit.setFont(font);
		
		lenPane.add(unit);
		
		this.add(lenPane);
		
		JPanel btnPane = new JPanel();
		btnPane.setBackground(new Color(231, 242, 255));
		btnPane.setOpaque(true);
		
		set.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event) {
				VgaSubFrame.this.dispose();
			}
		});
		
		btnPane.add(set);
		this.add(btnPane);
	}
}