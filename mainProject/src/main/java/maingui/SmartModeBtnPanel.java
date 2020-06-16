package maingui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class SmartModeBtnPanel extends JPanel
{
	private JButton smartModeBtn;
	private boolean toRefresh;
	
	public SmartModeBtnPanel() {
		
		this.setLayout(new GridLayout(0, 2));
		this.setBackground(new Color(231, 242, 255));
		this.setOpaque(true);
	}
	
	public void init() {
		this.removeAll();
		
		JPanel rabbitPanel = new JPanel();
		rabbitPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
		rabbitPanel.setBackground(new Color(231, 242, 255));

		ImageIcon rabbit = new ImageIcon(getClass().getResource("/rabbit.gif"));
		JLabel rabbitLabel = new JLabel(rabbit);

		rabbitPanel.add(rabbitLabel);
		this.add(rabbitPanel);

		JPanel buttonPanel = new JPanel();
		buttonPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
		buttonPanel.setBackground(new Color(231, 242, 255));

		ImageIcon toggle = new ImageIcon(getClass().getResource("/switchOn.png"));
		smartModeBtn = new JButton(toggle);
		smartModeBtn.setFont(new Font("Monospaced", Font.BOLD, 40));
		
		smartModeBtn.setPreferredSize(new Dimension(150, 60));
		smartModeBtn.setBorder(BorderFactory.createEmptyBorder());
		smartModeBtn.setContentAreaFilled(false);
		smartModeBtn.setFocusable(false);
		buttonPanel.add(smartModeBtn);
		this.add(buttonPanel);
		
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