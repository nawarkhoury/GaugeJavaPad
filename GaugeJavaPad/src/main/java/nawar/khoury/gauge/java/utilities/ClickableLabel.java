package nawar.khoury.gauge.java.utilities;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.border.Border;


/**
 * @author nawar.khoury
 *
 */
public class ClickableLabel extends JLabel 
{
	private String value;
	public ClickableLabel(String name, ClickHandler handler) 
	{
		super(name);
		setFont(new Font("Monospaced", Font.PLAIN, 15));
		setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		
		Border innerBorder2 = BorderFactory.createLoweredBevelBorder();
		Border innerBorder = BorderFactory.createLineBorder(Color.gray);
		Border outerBorder = BorderFactory.createEmptyBorder(5, 5, 5, 5);
		setBorder(BorderFactory.createCompoundBorder(outerBorder, innerBorder2));
		
		setText(name);
		
		addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				super.mouseClicked(e);
				handler.onClick(value);
			}
		});
	}
	
	@Override
	public void setName(String name) {
		super.setName(name);
		setText(name);
		value = name;
	}
}

interface ClickHandler {
	public void onClick(String display);
}
