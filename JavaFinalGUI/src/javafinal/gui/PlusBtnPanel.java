package javafinal.gui;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;

public class PlusBtnPanel extends JPanel
{
	private MainFrame parent;
	private ArrayList<JPanel> subPlusBtnPanes;
	private JButton[] plusButtons;
	
	public PlusBtnPanel(MainFrame parent) {
		this.parent = parent;
		this.setLayout(new GridLayout(0, 1, 0, 0));
		this.setBorder(BorderFactory.createLineBorder(Color.lightGray, 1));
		this.setBackground(new Color(231, 242, 255));
		this.setOpaque(true);
	}
	
	public void init() {
		this.removeAll();
		
		subPlusBtnPanes = new ArrayList<JPanel>();
		
		// sub plus' Panel
		for(int i = 0; i < parent.content.getLists().size(); i++) {
			subPlusBtnPanes.add(new JPanel());
			subPlusBtnPanes.get(i).setLayout(new BoxLayout(subPlusBtnPanes.get(i), BoxLayout.LINE_AXIS));
			subPlusBtnPanes.get(i).setAlignmentY(Component.CENTER_ALIGNMENT);
			subPlusBtnPanes.get(i).setBackground(new Color(231, 242, 255));
			subPlusBtnPanes.get(i).setOpaque(true);
			
//			subPlusBtnPanes.get(i).setBorder(BorderFactory.createLineBorder(Color.lightGray));
			
			this.add(subPlusBtnPanes.get(i));
		}
		
		ImageIcon plus = new ImageIcon(getClass().getResource("plus.png"));
		
		plusButtons = new JButton[3];
		for(int i = 0; i < 3; i++) {
			plusButtons[i] = new JButton(plus);
			plusButtons[i].setPreferredSize(new Dimension(30, 30));
		}
		subPlusBtnPanes.get(Content.MEM).add(plusButtons[0]);
		subPlusBtnPanes.get(Content.DISK).add(plusButtons[1]);
		subPlusBtnPanes.get(Content.VGA).add(plusButtons[2]);
	}
	
	public void addPanel(int count) {
		subPlusBtnPanes.add(count - 1, new JPanel());
		subPlusBtnPanes.get(count - 1).setBackground(new Color(231, 242, 255));
		subPlusBtnPanes.get(count - 1).setOpaque(true);
		

//		subPlusBtnPanes.get(count - 1).setBorder(BorderFactory.createLineBorder(Color.lightGray));
		
		this.add(subPlusBtnPanes.get(count - 1), count - 1);
		this.setBackground(new Color(231, 242, 255));
		this.setOpaque(true);
	}
	
	public void removePanel(int count, int i, int j) {
		subPlusBtnPanes.remove(count + ((j!=0)?0:1));
		this.remove(count + ((j!=0)?0:1));
	}
	public void enableSwitch(boolean op) {
		plusButtons[0].setEnabled(op);
		plusButtons[1].setEnabled(op);
		plusButtons[2].setEnabled(op);
	}
	public JButton[] getPlusBtns() {
		return plusButtons;
	}
}