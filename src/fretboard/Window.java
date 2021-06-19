package fretboard;
import java.awt.Color;

import javax.swing.JComboBox;
import javax.swing.JFrame;
/*
 * TODO: add zoom/ fretlock feature
 * add scaled selector
 */
public class Window extends JFrame implements Runnable
{
	private static final long serialVersionUID = 1L;
	
	private boolean running;
	private Thread thread;

	public static void main(String[] args) 
	{
		new Window();
	}
	
	public Window() 
	{
		setTitle("Fretboard");
		setResizable(true);
		setBounds(0, 0, 919, 509);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		getContentPane().setLayout(null);
		setLocationRelativeTo(null);
		setVisible(true);
		//adds fretboard
		add(new Fretboard(this));
		JComboBox<Pattern> box = new JComboBox<>();
		box.setBounds(0, 400, 100, 100);
		box.addItem(new Pattern(PatternCode.Major, Note.A));
		box.setBackground(Color.black);
		repaint();
		startThread();
	}
	/* https://web.mit.edu/6.005/www/sp14/psets/ps4/java-6-tutorial/components.html
	 * JComboBox to show selected things
	 * JButton to add 
	 * JList to show selected things
	 * JButton to remove
	 * just draw circles around the notes to identify them lol
	 */
	
	public synchronized void startThread()
	{
		thread = new Thread(this);
		thread.start();
		running = true;
	}
	
	public synchronized void stopThread()
	{
		try {
			thread.join();
			running = false;
		} catch (Exception e) {
			e.printStackTrace();
		}
	} 

	@Override
	public void run() 
	{
		setFocusable(true);
		requestFocus();
        while(running)
        {

        }
       stopThread();
	}
}