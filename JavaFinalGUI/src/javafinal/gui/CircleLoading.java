package javafinal.gui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class CircleLoading extends JFrame
{
	public CircleLoading() {
		super("Loading");
		
		ImageIcon loadingGif = new ImageIcon(getClass().getResource("loading.gif"));
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