package javafinal.gui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Dialog.ModalityType;

import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class CircleLoading extends JDialog
{
	public CircleLoading(MainFrame parent) {
		super(parent);
		this.setTitle("Loading");
		
//		this.setModal(true);
//		this.setModalityType(ModalityType.APPLICATION_MODAL);
		
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		this.setSize(300, 200);
		this.setLocationRelativeTo(parent);
		
		ImageIcon loadingGif = new ImageIcon(getClass().getResource("circle.gif"));
		JLabel loading = new JLabel(loadingGif);
		
		this.add(loading, BorderLayout.CENTER);
		
		JPanel textPane = new JPanel();
		textPane.setLayout(new FlowLayout(FlowLayout.CENTER));
		
		JLabel text = new JLabel("Loading...");
		Font f = new Font("Monospaced", Font.BOLD, 20);
		text.setFont(f);
		
		textPane.add(text);
		
		this.add(textPane, BorderLayout.SOUTH);
	}
}