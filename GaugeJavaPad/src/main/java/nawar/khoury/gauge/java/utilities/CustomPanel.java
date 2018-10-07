package nawar.khoury.gauge.java.utilities;

import java.awt.BorderLayout;
import java.awt.Component;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.border.Border;

public class CustomPanel extends JPanel
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public CustomPanel()
	{
		this(null);
	}
	
	public CustomPanel(String name)
	{
		super();
//		setBackground(Styling.themeColor);
		
		if(name != null && !name.isEmpty())
		{
			Border innerBorder = BorderFactory.createTitledBorder(name);
			Border outerBorder = BorderFactory.createEmptyBorder(5, 5, 5, 5);
			setBorder(BorderFactory.createCompoundBorder(outerBorder, innerBorder));
			
			setLayout(new BorderLayout());
		}
		
		this.setAutoscrolls(true);
	}
	
	public void setElement(Component element) {
		add(element, BorderLayout.CENTER);
	}
}
