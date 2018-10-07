package nawar.khoury.gauge.java.utilities;


import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

/**
 * @author nawar.khoury
 *
 */
public class GaugePad extends JPanel 
{
	private JTextArea textArea;
	private SuggestionsPanel suggestionPanel;
	
	public GaugePad() 
	{
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{1};
		gridBagLayout.rowHeights = new int[] {3, 1};
		gridBagLayout.columnWeights = new double[]{1.0};
		gridBagLayout.rowWeights = new double[]{3, 1.8};
		setLayout(gridBagLayout);
		
		textArea = new JTextArea();
		GridBagConstraints gbc_textArea = new GridBagConstraints();
		gbc_textArea.insets = new Insets(0, 0, 5, 0);
		gbc_textArea.fill = GridBagConstraints.BOTH;
		gbc_textArea.gridx = 0;
		gbc_textArea.gridy = 0;
		JScrollPane textAreaScroll = new JScrollPane(textArea);
		add(textAreaScroll, gbc_textArea);
		
		textArea.addKeyListener(generateTextChangeListener());
		
		suggestionPanel = new SuggestionsPanel();
		JScrollPane scrollableSuggestionPanel = new JScrollPane(suggestionPanel);
		scrollableSuggestionPanel.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		
		suggestionPanel.setAutoCompleteHandler(value -> {
			String text = textArea.getText();
			int lastLineStart = text.lastIndexOf("\n");
			
			if(lastLineStart < 0)
				text = "";
			else
				text = text.substring(0, lastLineStart);
			
			textArea.setText(text + (text.length() > 0 ? "\n" : "" ) + value);
		});
		
		GridBagConstraints gbc_lblNewLabel = new GridBagConstraints();
		gbc_lblNewLabel.fill = GridBagConstraints.BOTH;
		gbc_lblNewLabel.gridx = 0;
		gbc_lblNewLabel.gridy = 1;
		add(scrollableSuggestionPanel, gbc_lblNewLabel);
	}
	
	 private KeyListener generateTextChangeListener() {
			return new KeyListener() {
				
				@Override
				public void keyTyped(KeyEvent e) {
					
				}
				
				@Override
				public void keyReleased(KeyEvent e) {
					try {
						int offset = textArea.getCaretPosition();
			            int line = textArea.getLineOfOffset(offset);
			            String currentLine = textArea.getText().split("\n")[line];
			            suggestionPanel.setCurrentLine(currentLine);
					}
					catch(Exception ex) 
					{ 
						suggestionPanel.setCurrentLine(""); 
					}
				}
				
				@Override
				public void keyPressed(KeyEvent e) 
				{
					
				}
			};
		}
}