package net.diogobohm.timed;

import javax.swing.JFrame;
import net.diogobohm.timed.ui.MainWindow;

/**
 *
 * @author diogo
 */
public class Main {

	/**
	 * @param args the command line arguments
	 */
	public static void main(String[] args) {
		MainWindow window = new MainWindow();
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		window.setVisible(true);
	}
	
}
