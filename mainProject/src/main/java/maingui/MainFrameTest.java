package maingui;

import java.awt.Dimension;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.UIManager;

import mainlogic.MainGee;

public class MainFrameTest
{
	public static void main(String[] args)
	{
		try {
    		UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
    	}
    	catch(Exception e2) {
    		e2.printStackTrace();
    	}

		LoadingFrame loading = new LoadingFrame();
		
		ImageIcon icon = new ImageIcon(loading.getClass().getResource("/icon.png"));
		
		loading.setIconImage(icon.getImage());
		loading.setLocationRelativeTo(null);
		loading.setVisible(true);
		
		MainGee code = new MainGee();
		code.init();
		MainFrame mainFrame = new MainFrame(code, code.getList());
		
		mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		mainFrame.setSize(1000, 705);
		mainFrame.setMinimumSize(new Dimension(1000, 730));
//		mainFrame.setResizable(false);
		mainFrame.setLocationRelativeTo(null);
		
		mainFrame.setIconImage(icon.getImage());
		
		mainFrame.init();
		
		loading.dispose();
		mainFrame.setVisible(true);
//		mainFrame.test();
	}
}