package net.diogobohm.timed.impl;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.jgoodies.looks.windows.WindowsLookAndFeel;
import java.io.File;
import java.util.Collection;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import javax.swing.UIManager;
import net.diogobohm.timed.api.db.access.DatabaseConnection;
import net.diogobohm.timed.api.domain.Task;
import net.diogobohm.timed.impl.hamster.migration.HamsterMigration;
import net.diogobohm.timed.impl.ui.mainwindow.MainWindow;
import net.diogobohm.timed.impl.ui.mainwindow.MainWindowController;
import org.tmatesoft.sqljet.core.SqlJetException;
import org.tmatesoft.sqljet.core.SqlJetTransactionMode;
import org.tmatesoft.sqljet.core.table.ISqlJetCursor;
import org.tmatesoft.sqljet.core.table.ISqlJetTable;
import org.tmatesoft.sqljet.core.table.SqlJetDb;

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

        DatabaseConnection.getConnection();

        MainWindowController window = new MainWindowController();

        //window.addTasks(migrateDatabase());
        window.showView();
    }

    private static Collection<Task> migrateDatabase() {
        File dbFile = new File("../hamster.db");
        Collection<Task> hamsterTasks = Lists.newArrayList();

        try {
            hamsterTasks.addAll(new HamsterMigration().migrateHamsterDatabase(dbFile));
        } catch (SqlJetException ex) {
            ex.printStackTrace();
        }

        return hamsterTasks;
    }
}
