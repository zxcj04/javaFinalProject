package maingui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.SpinnerModel;
import javax.swing.SpinnerNumberModel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class CaseSubFrame extends JDialog {
	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	FilterComboBox mbSize;
	JSpinner vgaLength;
	FilterComboBox psuSize;
	JSpinner psuLength;
	JSpinner coolerHeight;
	JSpinner diskQuantity;
	JButton set = new JButton("確定");
	
	public CaseSubFrame(MainFrame parent, OptionPanel brother) {
		super(parent);
		this.setTitle("自訂機殼");
		
		this.setModal(true);
		this.setModalityType(ModalityType.APPLICATION_MODAL);
		
		this.setLayout(new GridLayout(0, 1));
		
		JPanel mbPane = new JPanel();
		mbPane.setBackground(new Color(231, 242, 255));
		mbPane.setOpaque(true);
		
		JLabel label1 = new JLabel("主機板大小: ");
		Font font = new Font("Monospaced", Font.BOLD, 16);
		label1.setFont(font);
		
		mbPane.add(label1);
		
		ArrayList<String> mbList = new ArrayList<String>();
		mbList.add("ATX");
		mbList.add("MATX");
		mbList.add("EATX");
		mbList.add("ITX");
		mbList.add("MITX");
		
		mbSize = new FilterComboBox(mbList);
		mbSize.getTextField().setEditable(false);
		mbSize.setPreferredSize(new Dimension(110, 32));
		
		mbPane.add(mbSize);
		this.add(mbPane);
		
		JPanel vgaPane = new JPanel();
		vgaPane.setBackground(new Color(231, 242, 255));
		vgaPane.setOpaque(true);
		
		JLabel label2 = new JLabel("顯卡長度: ");
		label2.setFont(font);
		
		vgaPane.add(label2);
		
		SpinnerModel vgaModel = new SpinnerNumberModel(1, Integer.MIN_VALUE, Integer.MAX_VALUE, 1);
		vgaLength = new JSpinner(vgaModel);
		vgaLength.setPreferredSize(new Dimension(95, 32));
		((JSpinner.NumberEditor)vgaLength.getEditor()).getTextField().setBackground(new Color(215, 225, 238));
		
		((JSpinner.NumberEditor)vgaLength.getEditor()).getTextField().setFont(font);
		
		vgaLength.addChangeListener(new spinnerListener());
		vgaPane.add(vgaLength);
		
		JLabel unitCm = new JLabel("cm");
		unitCm.setFont(font);
		
		vgaPane.add(unitCm);
		
		this.add(vgaPane);
		
		JPanel psuPane = new JPanel();
		psuPane.setBackground(new Color(231, 242, 255));
		psuPane.setOpaque(true);
		
		JLabel label3 = new JLabel("電源大小: ");
		label3.setFont(font);
		
		psuPane.add(label3);
		
		ArrayList<String> psuList = new ArrayList<String>();
		psuList.add("ATX");
		psuList.add("SFX");
		
		psuSize = new FilterComboBox(psuList);
		psuSize.getTextField().setEditable(false);
		psuSize.setPreferredSize(new Dimension(70, 32));
		
		psuPane.add(psuSize);
		
		JLabel label4 = new JLabel("電源長度: ");
		label4.setFont(font);
		
		psuPane.add(label4);
		
		SpinnerModel psuModel = new SpinnerNumberModel(1, Integer.MIN_VALUE, Integer.MAX_VALUE, 1);
		psuLength = new JSpinner(psuModel);
		psuLength.setPreferredSize(new Dimension(70, 32));
		((JSpinner.NumberEditor)psuLength.getEditor()).getTextField().setBackground(new Color(215, 225, 238));
		
		((JSpinner.NumberEditor)psuLength.getEditor()).getTextField().setFont(font);
		
		psuLength.addChangeListener(new spinnerListener());
		psuPane.add(psuLength);
		
		psuPane.add(unitCm);
		
		this.add(psuPane);
		
		JPanel coolerPane = new JPanel();
		coolerPane.setBackground(new Color(231, 242, 255));
		coolerPane.setOpaque(true);
		
		JLabel label5 = new JLabel("散熱器高度: ");
		label5.setFont(font);
		
		coolerPane.add(label5);
		
		SpinnerModel coolerModel = new SpinnerNumberModel(1, Integer.MIN_VALUE, Integer.MAX_VALUE, 1);
		coolerHeight = new JSpinner(coolerModel);
		coolerHeight.setPreferredSize(new Dimension(95, 32));
		((JSpinner.NumberEditor)coolerHeight.getEditor()).getTextField().setBackground(new Color(215, 225, 238));
		
		((JSpinner.NumberEditor)coolerHeight.getEditor()).getTextField().setFont(font);
		
		coolerHeight.addChangeListener(new spinnerListener());
		coolerPane.add(coolerHeight);
		
		coolerPane.add(unitCm);
		
		this.add(coolerPane);
		
		JPanel diskPane = new JPanel();
		diskPane.setBackground(new Color(231, 242, 255));
		diskPane.setOpaque(true);
		
		JLabel label6 = new JLabel("3.5\"硬碟位: ");
		label6.setFont(font);
		
		diskPane.add(label6);
		
		SpinnerModel diskModel = new SpinnerNumberModel(1, Integer.MIN_VALUE, Integer.MAX_VALUE, 1);
		diskQuantity = new JSpinner(diskModel);
		diskQuantity.setPreferredSize(new Dimension(105, 32));
		((JSpinner.NumberEditor)diskQuantity.getEditor()).getTextField().setBackground(new Color(215, 225, 238));
		
		((JSpinner.NumberEditor)diskQuantity.getEditor()).getTextField().setFont(font);
		
		diskQuantity.addChangeListener(new spinnerListener());
		diskPane.add(diskQuantity);
		
		JLabel unitNum = new JLabel("個");
		unitNum.setFont(font);
		diskPane.add(unitNum);
		
		this.add(diskPane);
		
		JPanel btnPane = new JPanel();
		btnPane.setBackground(new Color(231, 242, 255));
		btnPane.setOpaque(true);
		
		set.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event) {
				String chosen = "custom ";
				chosen += mbSize.getTextField().getText();
				chosen += " ";
				chosen += ((JSpinner.NumberEditor)vgaLength.getEditor()).getTextField().getText();
				chosen += "cm ";
				chosen += psuSize.getTextField().getText();
				chosen += " ";
				chosen += ((JSpinner.NumberEditor)psuLength.getEditor()).getTextField().getText();
				chosen += "cm ";
				chosen += ((JSpinner.NumberEditor)coolerHeight.getEditor()).getTextField().getText();
				chosen += "cm ";
				chosen += ((JSpinner.NumberEditor)diskQuantity.getEditor()).getTextField().getText();
				chosen += "個";
				
				brother.setFeedback(chosen);
				CaseSubFrame.this.dispose();
			}
		});
		
		btnPane.add(set);
		this.add(btnPane);
	}
	
	private class spinnerListener implements ChangeListener {
		@Override
		public void stateChanged(ChangeEvent event) {
			if(event.getSource() == vgaLength) {
				if((Integer)vgaLength.getValue() >= 1000) {
					vgaLength.setValue(999);
				}
				else if((Integer)vgaLength.getValue() <= 0) {
					vgaLength.setValue(1);
				}
			}
			else if(event.getSource() == psuLength) {
				if((Integer)psuLength.getValue() >= 1000) {
					psuLength.setValue(999);
				}
				else if((Integer)psuLength.getValue() <= 0) {
					psuLength.setValue(1);
				}
			}
			else if(event.getSource() == coolerHeight) {
				if((Integer)coolerHeight.getValue() >= 1000) {
					coolerHeight.setValue(999);
				}
				else if((Integer)psuLength.getValue() <= 0) {
					psuLength.setValue(1);
				}
			}
			else if(event.getSource() == diskQuantity) {
				if((Integer)diskQuantity.getValue() >= 1000) {
					diskQuantity.setValue(999);
				}
				else if((Integer)diskQuantity.getValue() <= 0) {
					diskQuantity.setValue(1);
				}
			}
		}
	}
}