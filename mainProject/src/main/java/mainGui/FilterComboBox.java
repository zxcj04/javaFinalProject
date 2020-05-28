package maingui;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

/**
 * A class for filtered combo box.
 */
public class FilterComboBox
    extends JComboBox
{
    /**
     * Entries to the combobox ArrayList.
     */
    private ArrayList<String> entries;
    
    private final JTextField textfield =
            (JTextField) this.getEditor().getEditorComponent();

    public ArrayList<String> getEntries()
    {
        return entries;
    }

    public FilterComboBox(ArrayList<String> entries)
    {
        super(entries.toArray());
        this.entries = entries  ;
        this.setEditable(true);

        final JTextField textfield =
            (JTextField) this.getEditor().getEditorComponent();
                
        textfield.setBackground(new Color(215, 225, 238));
        
        Font font = new Font("Monospaced", Font.BOLD, 16);
        textfield.setFont(font);

        /**
         * Listen for key presses.
         */
        textfield.addKeyListener(new KeyAdapter()
        {
            public void keyReleased(KeyEvent ke)
            {
                SwingUtilities.invokeLater(new Runnable()
                {
                    public void run()
                    {
                        /**
                         * On key press filter the ArrayList.
                         * If there is "text" entered in text field of this combo use that "text" for filtering.
                         */
                        comboFilter(textfield.getText());
                    }
                });
            }
        });

    }

    /**
     * Build a ArrayList of entries that match the given filter.
     */
    public void comboFilter(String enteredText)
    {
        ArrayList<String> entriesFiltered = new ArrayList<String>();

        for (String entry : getEntries())
        {
            if (entry.toLowerCase().contains(enteredText.toLowerCase()))
            {
                entriesFiltered.add(entry);
            }
        }

        if (entriesFiltered.size() > 0)
        {
            this.setModel(new DefaultComboBoxModel(entriesFiltered.toArray()));
            this.setSelectedItem(enteredText);
            this.showPopup();
        }
        else
        {
            this.hidePopup();
        }
    }
    
    public void updateEntries(ArrayList<String> entries) {
    	this.entries = new ArrayList<String>(entries);
    	
        this.setModel(new DefaultComboBoxModel(entries.toArray()));
    }
    
    public JTextField getTextField() {
    	return textfield;
    }
}