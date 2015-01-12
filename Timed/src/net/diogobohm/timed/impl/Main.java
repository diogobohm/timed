package net.diogobohm.timed.impl;

import com.google.common.collect.Lists;
import com.jgoodies.looks.windows.WindowsLookAndFeel;
import java.io.File;
import java.util.Collection;
import javax.swing.UIManager;
import net.diogobohm.timed.api.db.access.Database;
import net.diogobohm.timed.api.db.access.DatabaseConnection;
import net.diogobohm.timed.api.db.exception.DatabaseAccessException;
import net.diogobohm.timed.api.db.serializer.DBPersistenceOrchestrator;
import net.diogobohm.timed.api.domain.Task;
import net.diogobohm.timed.impl.hamster.migration.HamsterMigration;
import net.diogobohm.timed.impl.ui.mainwindow.MainWindowController;
import org.tmatesoft.sqljet.core.SqlJetException;

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

        Database db = DatabaseConnection.getConnection();
        DBPersistenceOrchestrator orchestrator = new DBPersistenceOrchestrator();
        
        Collection<Task> hamsterTasks = migrateDatabase();

        try {
            for (Task task : hamsterTasks) {
                orchestrator.writeTask(db, task);
            }
        } catch (DatabaseAccessException ex) {
            System.err.println("Error migrating hamster database!");
            ex.printStackTrace();
            System.exit(1);
        }

        Collection<Task> tasks = Lists.newArrayList();

        try {
            tasks.addAll(orchestrator.loadTasks(db));

        } catch (DatabaseAccessException ex) {
            System.err.println("Error loading tasks!");
            ex.printStackTrace();
            System.exit(1);
        }

        MainWindowController window = new MainWindowController();

        window.addTasks(tasks);
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
