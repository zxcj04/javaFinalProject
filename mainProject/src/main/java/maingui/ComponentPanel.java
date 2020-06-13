package maingui;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.util.ArrayList;

import javax.swing.DefaultListCellRenderer.UIResource;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.SpinnerModel;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.plaf.basic.BasicSpinnerUI;
import javax.swing.plaf.basic.BasicArrowButton;

public class ComponentPanel extends JPanel
{
	private final boolean[][] visableSet = {{true, false, false, true},  // cpu
											{true, false,  true, true},  // cooler
											{true, false, false, true},  // motherboard
											{true,  true,  true, true},  // memory
											{true, false,  true, true},  // disk
											{true, false,  true, true},  // vga
											{true, false,  true, true},  // psu
											{true, false,  true, true},};// case

	private FilterComboBox comboBox;
	private JSpinner counter;
	private JButton customBtn;
	private JButton subBtn;
	
	public ComponentPanel(ArrayList<String> list, int cur) {
		super();
		
		this.setLayout(new BorderLayout());
		this.setBackground(new Color(231, 242, 255));
		this.setOpaque(true);
		
		JPanel panel1 = new JPanel();
		panel1.setLayout(new FlowLayout(FlowLayout.LEFT));
		this.add(panel1, BorderLayout.CENTER);
		
		JPanel panel2 = new JPanel();
		panel2.setLayout(new FlowLayout(FlowLayout.RIGHT));
		this.add(panel2, BorderLayout.EAST);
		
		if(cur % 2 == 1) {
			panel1.setBackground(new Color(98, 130, 170));
			panel1.setOpaque(true);
			panel2.setBackground(new Color(98, 130, 170));
			panel2.setOpaque(true);
		}
		else {
			panel1.setBackground(new Color(162, 193, 233));
			panel1.setOpaque(true);
			panel2.setBackground(new Color(162, 193, 233));
			panel2.setOpaque(true);
		}
		
		// comboBox
		comboBox = new FilterComboBox(list);

		comboBox.setPreferredSize(new Dimension(420, 25));
		panel1.add(comboBox);
		comboBox.setVisible(visableSet[cur][0]);
		
		// spinner
		SpinnerModel spinnerModel = new SpinnerNumberModel(0, 0, 16, 1);
		counter = new JSpinner(spinnerModel);
		counter.setUI(new BasicSpinnerUI() {
			@Override
			protected Component createNextButton() {
				Component c = createArrowButton(SwingConstants.NORTH);
			    //c.setName("nextButton");
			    installNextButtonListeners(c);
			    return c;
		    }
			@Override
			protected Component createPreviousButton() {
		        Component c = createArrowButton(SwingConstants.SOUTH);
		        //c.setName("previousButton");
		        installPreviousButtonListeners(c);
		        return c;
		    }
			
			private Component createArrowButton(int direction) {
			    JButton b = new BasicArrowButton(direction);
			    
			    Border buttonBorder = UIManager.getBorder("Spinner.arrowButtonBorder");
			    if (buttonBorder instanceof UIResource) {
			      b.setBorder(new CompoundBorder(buttonBorder, null));
			    } else {
			      b.setBorder(buttonBorder);
			    }
			    b.setInheritsPopupMenu(true);
			    return b;
			}
		});
		
		counter.setPreferredSize(new Dimension(100, 25));
		((JSpinner.NumberEditor)counter.getEditor()).getTextField().setEditable(false);
		((JSpinner.NumberEditor)counter.getEditor()).getTextField().setBackground(new Color(215, 225, 238));
		
		Font font = new Font("Monospaced", Font.BOLD, 16);
		((JSpinner.NumberEditor)counter.getEditor()).getTextField().setFont(font);
		
		panel1.add(counter);
		counter.setVisible(visableSet[cur][1]);
		
		// gear Button
		ImageIcon gear = new ImageIcon(getClass().getResource("/gear.png"));
		customBtn = new JButton(gear);
		customBtn.setPreferredSize(new Dimension(30, 30));
		
		panel1.add(customBtn);
		customBtn.setVisible(visableSet[cur][2]);
		
		// sub Button
		ImageIcon sub = new ImageIcon(getClass().getResource("/sub.png"));
		subBtn = new JButton(sub);
		subBtn.setPreferredSize(new Dimension(30, 30));
		
		panel2.add(subBtn);
		subBtn.setVisible(true);
	}
	
	public void setSubBtn(boolean visable)
	{
		subBtn.setVisible(visable);
	}
	
	public JButton getSubBtn()
	{
		return subBtn;
	}
	
	public JButton getGear()
	{
		return customBtn;
	}
	
	public FilterComboBox getComboBox() {
		return comboBox;
	}
	
	public JSpinner getSpinner() {
		return counter;
	}
}