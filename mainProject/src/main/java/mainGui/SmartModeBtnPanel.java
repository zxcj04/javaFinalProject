package maingui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;

public class SmartModeBtnPanel extends JPanel
{
	private JButton smartModeBtn;
	private boolean toRefresh;
	
	public SmartModeBtnPanel() {
		
		this.setLayout(new FlowLayout(FlowLayout.RIGHT));
		this.setBackground(new Color(231, 242, 255));
		this.setOpaque(true);
	}
	
	public void init() {
		this.removeAll();
		
		ImageIcon toggle = new ImageIcon(getClass().getResource("/switchOn.png"));
		smartModeBtn = new JButton(toggle);
		
		smartModeBtn.setPreferredSize(new Dimension(95, 30));
		smartModeBtn.setBorder(BorderFactory.createEmptyBorder());
		smartModeBtn.setContentAreaFilled(false);
		this.add(smartModeBtn);
		
		toRefresh = true;
	}
	public void enableSwitch(boolean op) {
		smartModeBtn.setEnabled(op);
	}
	
	public JButton getSmartModeBtn() {
		return smartModeBtn;
	}
	public void setToRefresh(boolean toRefresh) {
		this.toRefresh = toRefresh;
	}
	public boolean getToRefresh() {
		return toRefresh;
	}
}