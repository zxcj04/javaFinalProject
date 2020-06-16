package maingui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.border.TitledBorder;

public class SuggestionPanel extends JPanel
{
	private JTextArea suggestion;
	
	public SuggestionPanel(){
		this.setLayout(new BorderLayout());
		this.setBorder(BorderFactory.createTitledBorder("Suggestions"));
		((TitledBorder)this.getBorder()).setTitleFont(MainFrame.title);
		this.setPreferredSize(new Dimension(300, 600));
		this.setBackground(new Color(231, 242, 255));
		this.setOpaque(true);

		suggestion = new JTextArea();
		suggestion.setLineWrap(true);
		suggestion.setFont(MainFrame.font);
		suggestion.setEditable(false);
		suggestion.setBackground(new Color(233, 233, 233));
		suggestion.setOpaque(true);
		
		JScrollPane scrollSuggestion = new JScrollPane(suggestion);
		
		this.add(scrollSuggestion, BorderLayout.CENTER);
	}
	
	public void init() {
		suggestion.setText("");
	}
	
	public JTextArea getSuggestion() {
		return suggestion;
	}
}