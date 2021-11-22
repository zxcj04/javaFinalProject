package maingui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class LoadingPanel extends JPanel
{
	private MainFrame parent;
	
	public LoadingPanel(MainFrame parent) {
		this.parent = parent;
		
//		this.setLayout(new GridLayout(3, 1));
		this.setLayout(new BorderLayout());
		
//		JPanel panel = new JPanel();
//		panel.setLayout(new BorderLayout());
//		
//		this.add(new JPanel());
//		this.add(panel);
//		this.add(new JPanel());
		
//		JPanel iconPane = new JPanel();
//		iconPane.setLayout(new BorderLayout());
		
		ImageIcon loadingGif = new ImageIcon(getClass().getResource("/loading.gif"));
		JLabel loading = new JLabel(loadingGif);
		
		loading.setFocusable(false);
		
		this.add(loading, BorderLayout.CENTER);
		
//		JPanel textPane = new JPanel();
//		textPane.setLayout(new FlowLayout(FlowLayout.CENTER));
		
//		JLabel text = new JLabel("Loading...");
//		Font f = new Font("Monospaced", Font.BOLD, 32);
//		text.setFont(f);
//		
//		textPane.add(text);
//		
//		panel.add(iconPane, BorderLayout.CENTER);
//		panel.add(textPane, BorderLayout.SOUTH);
		
		this.setFocusable(true);
		this.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent event) {
				parent.switchCards("MAIN");
			}
		});
	}
}