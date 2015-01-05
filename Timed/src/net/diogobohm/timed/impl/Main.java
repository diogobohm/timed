package net.diogobohm.timed.impl;

import com.google.common.collect.Maps;
import com.jgoodies.looks.windows.WindowsLookAndFeel;
import java.io.File;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import javax.swing.UIManager;
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

        MainWindowController window = new MainWindowController();
        window.showView();

        showDatabase();
    }

    private static void showDatabase() {
        File dbFile = new File("../hamster.db");
        SqlJetDb db;
        try {
            db = SqlJetDb.open(dbFile, false);

            db.beginTransaction(SqlJetTransactionMode.READ_ONLY);
            ISqlJetTable facts = db.getTable("facts");
            ISqlJetTable activities = db.getTable("activities");
            ISqlJetCursor factsCursor = facts.order(facts.getPrimaryKeyIndexName());
            ISqlJetCursor activitiesCursor = activities.order(activities.getPrimaryKeyIndexName());
            Map<Integer, String> activityMap = Maps.newHashMap();
            
            if (!activitiesCursor.eof()) {
            do {
                    activityMap.put(Long.valueOf(activitiesCursor.getRowId()).intValue(), activitiesCursor.getString("name"));
                } while (activitiesCursor.next());
            }
                
            if (!factsCursor.eof()) {
                do {
                    System.out.println(factsCursor.getRowId() + " : "
                            + factsCursor.getString("start_time") + " "
                            + factsCursor.getString("end_time")
                            + activityMap.get(Long.valueOf(factsCursor.getInteger("activity_id")).intValue()));
                } while (factsCursor.next());
            }

        } catch (SqlJetException ex) {
            ex.printStackTrace();
        }
    }
}
