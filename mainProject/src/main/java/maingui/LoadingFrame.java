package maingui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.io.InputStream;
import java.nio.file.Paths;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class LoadingFrame extends JFrame
{
	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	public LoadingFrame() {
		super("Loading");
		
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		this.setSize(500, 500);
		
		ImageIcon loadingGif = new ImageIcon(getClass().getResource("/loading.gif"));
		JLabel loading = new JLabel(loadingGif);
		
		this.add(loading, BorderLayout.CENTER);
		
		JPanel textPane = new JPanel();
		textPane.setLayout(new FlowLayout(FlowLayout.CENTER));
		
		JLabel text = new JLabel("Loading...");

		try
		{
			// text.setFont(Loadfont.loadFont(Paths.get(getClass().getResource("/sarasa-fixed-cl-bold.ttf").toURI()).toString(), 32));
			text.setFont(new Font("Greek", Font.BOLD, 32));

			// InputStream is = getClass().getResourceAsStream("/sarasa-fixed-cl-bold.ttf");

			// Font f = Font.createFont(Font.TRUETYPE_FONT, is);

			// is.close();

			// text.setFont(f.deriveFont(32f));
		}
		catch(Exception e)
		{
			e.printStackTrace();

			text.setFont(new Font("Monospaced", Font.BOLD, 32));
		}
		
		textPane.add(text);
		
		this.add(textPane, BorderLayout.SOUTH);
	}
}