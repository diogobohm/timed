package net.diogobohm.timed.impl;

import com.jgoodies.looks.windows.WindowsLookAndFeel;
import javax.swing.JFrame;
import javax.swing.UIManager;
import net.diogobohm.timed.impl.ui.mainwindow.MainWindow;
import net.diogobohm.timed.impl.ui.mainwindow.MainWindowController;

/**
 *
 * @author diogo
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(new WindowsLookAndFeel());
        } catch (Exception e) {
        }

        MainWindowController window = new MainWindowController();
        window.showView();
    }

}
