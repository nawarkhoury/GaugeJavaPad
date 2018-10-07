package nawar.khoury.gauge.java.utilities;



import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;
import javax.swing.UnsupportedLookAndFeelException;


/**
 * @author nawar.khoury
 *
 */
public class Pad extends JFrame
{
	private GaugePad pad;
	
	public Pad() throws Exception
	{
		super("Gauge Pad");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		getContentPane().setLayout(new BorderLayout());
		pad = new GaugePad();
		setBounds(300, 50, 750, 650);
		getContentPane().add(pad, BorderLayout.CENTER);
	}

	public static void main(String[] args) throws ClassNotFoundException, InstantiationException, IllegalAccessException, UnsupportedLookAndFeelException
	{
		for (LookAndFeelInfo info : UIManager.getInstalledLookAndFeels())
		{
			if ("Nimbus".equals(info.getName()))
			{
				UIManager.setLookAndFeel(info.getClassName());
				break;
			}
		}
		EventQueue.invokeLater(new Runnable()
		{
			public void run()
			{
				try
				{
					Pad frame = new Pad();
					frame.setVisible(true);
				} catch (Exception e)
				{
					e.printStackTrace();
				}
			}
		});
	}
}
