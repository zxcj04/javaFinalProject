package javafinal.gui;

import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.UIManager;

import fanrende.MainGee;

public class MainFrameTest
{
	public static void main(String[] args)
	{
		try {
	    	UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
	    }
	    catch(Exception e) {
	    	e.printStackTrace();
	    }
		
		MainGee code = new MainGee();
		MainFrame mainFrame = new MainFrame(code, code.getList());
		
		mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		mainFrame.setSize(1000, 705);
		mainFrame.setMinimumSize(new Dimension(1000, 705));
//		mainFrame.setResizable(false);
		mainFrame.setLocationRelativeTo(null);
		
		LoadingFrame loading = new LoadingFrame();
		loading.setLocationRelativeTo(mainFrame);
		loading.setVisible(true);
		
		
		mainFrame.init();
		
		loading.dispose();
		mainFrame.setVisible(true);
//		mainFrame.test();
	}
}