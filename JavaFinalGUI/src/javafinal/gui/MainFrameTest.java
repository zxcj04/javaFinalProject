package javafinal.gui;

import javax.swing.JFrame;

import fanrende.MainGee;

public class MainFrameTest
{
	public static void main(String[] args)
	{
		MainGee code = new MainGee();
		MainFrame mainFrame = new MainFrame(code, code.getList());
		
		mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		mainFrame.setSize(1000, 665);
		mainFrame.setResizable(false);
		mainFrame.setLocationRelativeTo(null);
		mainFrame.setVisible(true);
	}
}