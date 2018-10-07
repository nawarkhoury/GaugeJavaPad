package nawar.khoury.gauge.java.utilities;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.Set;

import javax.swing.JLabel;
import javax.swing.JPanel;

import org.reflections.Reflections;
import org.reflections.scanners.MethodAnnotationsScanner;

import com.thoughtworks.gauge.Step;


/**
 * @author nawar.khoury
 *
 */
public class SuggestionsPanel extends JPanel
{
	private LinkedList<String> possibilites;
	private String currentLine;
	private AutoCompleteHandler autoCompleteHandler;
	private int renderId = 0;
	private ArrayList<JLabel> labels;
	
	public SuggestionsPanel (){
		possibilites = findAllStepsAnnotations();
		System.out.println("The Number of possibilites is: " + possibilites.size());
		renderUI();
	}
	public void renderUI() {
		labels = new ArrayList<JLabel>();
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{0};
		gridBagLayout.rowHeights = new int[possibilites.size()];
		gridBagLayout.columnWeights = new double[]{Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[possibilites.size()];
		
		JPanel panel = new JPanel();
		panel.setLayout(gridBagLayout);
		
		for(int i=0; i<possibilites.size(); i++)
		{
			String labelString = possibilites.get(i);
			JLabel lblNewLabel = new ClickableLabel(labelString, display -> {
				autoCompleteHandler.onChoice("*" + display);
			});
			GridBagConstraints gbc_lblNewLabel = createGbc(0, i);
			labels.add(lblNewLabel);
			panel.add(labels.get(i), gbc_lblNewLabel);
		}
		setLayout(new BorderLayout());
		add(panel, BorderLayout.CENTER);
	}
	
	public void setCurrentLine(String line) {
		this.currentLine = line;
		final String lineTrimmed = line.replaceAll("([\"]++[ ]*+[0-9A-Za-z]++[ ]*+[\"]++)+", "");
		
		possibilites.sort(new Comparator<String>() {
			@Override
			public int compare(String o1, String o2) {
				o1 = o1.replaceAll("([<]++[ ]*+[0-9A-Za-z]++[ ]*+[>]++)+", "");
				o2 = o2.replaceAll("([<]++[ ]*+[0-9A-Za-z]++[ ]*+[>]++)+", "");
				
//				return levensteinWithContainsCheckComparison(o1, o2);
				return wordCountContainsComparison(o1, o2);
			}

			private int wordCountContainsComparison(String o1, String o2)
			{
				String[] words = line.split(" ");
				int o1score = calcStringSimilarity(o1, words);
				int o2score = calcStringSimilarity(o2, words);
				
				if(o1.toUpperCase().contains(line.toUpperCase()))
					o1score += 100;
				
				if(o2.toUpperCase().contains(line.toUpperCase()))
					o2score += 100;
				
				return o2score - o1score;
			}

			private int calcStringSimilarity(String str, String[] words)
			{
				int similarity = 0;
				for(String word : words) {
					if(str.toUpperCase().contains(word.toUpperCase()))
						similarity += 1;
				}
				return similarity;
			}

//			private int levensteinWithContainsCheckComparison(String o1, String o2)
//			{
//				if(o1.toUpperCase().contains(lineTrimmed.toUpperCase()))
//					return Integer.MIN_VALUE;
//				else if(o2.toUpperCase().contains(lineTrimmed.toUpperCase()))
//					return Integer.MAX_VALUE;
//				else
//					return StringUtils.getLevenshteinDistance(o1, currentLine) - StringUtils.getLevenshteinDistance(o2, currentLine);
//			}
		});
		
		rerenderUI();
	}
	
	private void rerenderUI() {
		for(int i=0; i<possibilites.size(); i++)
		{
			labels.get(i).setName(possibilites.get(i));
		}
	}
	
	private LinkedList<String> findAllStepsAnnotations() 
	{
		LinkedList<String> steps = new LinkedList<String>();
		Set<Method> stepsMethods = new Reflections("", new MethodAnnotationsScanner()).getMethodsAnnotatedWith(Step.class);
		stepsMethods.forEach(method -> {
			String[] methodSteps = method.getAnnotation(Step.class).value();
			for(String step : methodSteps) {
				steps.add(step);
			}
		});
		return steps;
	}
	
	public void setAutoCompleteHandler(AutoCompleteHandler autoCompleteHandler) {
		this.autoCompleteHandler = autoCompleteHandler;
	}
	
	private GridBagConstraints createGbc(int x, int y) {
	      GridBagConstraints gbc = new GridBagConstraints();
	      gbc.gridx = x;
	      gbc.gridy = y;
	      gbc.gridwidth = 1;
	      gbc.gridheight = 1;

	      gbc.anchor = (x == 0) ? GridBagConstraints.WEST : GridBagConstraints.EAST;
	      gbc.fill = (x == 0) ? GridBagConstraints.BOTH
	            : GridBagConstraints.HORIZONTAL;

//	      gbc.insets = (x == 0) ? WEST_INSETS : EAST_INSETS;
	      gbc.weightx = (x == 0) ? 0.1 : 1.0;
	      gbc.weighty = 1.0;
	      return gbc;
	   }
}

interface AutoCompleteHandler {
	public void onChoice(String choice);
}
