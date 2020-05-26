package maingui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class LoadingPanel extends JPanel
{
	public LoadingPanel() {
		this.setLayout(new BorderLayout());
		
		JPanel iconPane = new JPanel();
		iconPane.setLayout(new BorderLayout());
		
		ImageIcon loadingGif = new ImageIcon(getClass().getResource("loading.gif"));
		JLabel loading = new JLabel(loadingGif);
		
		iconPane.add(loading, BorderLayout.CENTER);
		
		JPanel textPane = new JPanel();
		textPane.setLayout(new FlowLayout(FlowLayout.CENTER));
		
		JLabel text = new JLabel("Loading...");
		Font f = new Font("Monospaced", Font.BOLD, 32);
		text.setFont(f);
		
		textPane.add(text);
		
		this.add(iconPane, BorderLayout.CENTER);
		this.add(textPane, BorderLayout.SOUTH);
	}
}