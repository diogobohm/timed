/**
 * TODO: define a license.
 */
package net.diogobohm.timed.impl.hamster.migration;

import com.google.common.collect.Maps;
import java.io.File;
import java.util.Map;
import net.diogobohm.timed.api.domain.Activity;
import net.diogobohm.timed.api.domain.Project;
import net.diogobohm.timed.api.domain.Tag;
import net.diogobohm.timed.api.domain.Task;
import org.tmatesoft.sqljet.core.SqlJetException;
import org.tmatesoft.sqljet.core.SqlJetTransactionMode;
import org.tmatesoft.sqljet.core.table.ISqlJetCursor;
import org.tmatesoft.sqljet.core.table.ISqlJetTable;
import org.tmatesoft.sqljet.core.table.SqlJetDb;

/**
 *
 * @author diogo.bohm
 */
public class HamsterMigration {

    public void migrateHamsterDatabase(File hamsterDbFile, SqlJetDb database) throws SqlJetException {
        SqlJetDb hamsterDb = SqlJetDb.open(hamsterDbFile, false);

        hamsterDb.beginTransaction(SqlJetTransactionMode.READ_ONLY);

        Map<Integer, String> activityMap = Maps.newHashMap();
        Map<Integer, Activity> activities = loadActivities(hamsterDb);
        Map<Integer, Tag> tags;
        Map<Integer, Project> projects;
        Map<Integer, Task> tasks;

    }

    private Map<Integer, Activity> loadActivities(SqlJetDb hamsterDb) throws SqlJetException {
        Map<Integer, Activity> activities = Maps.newHashMap();
        ISqlJetTable activitiesTable = hamsterDb.getTable("activities");
        ISqlJetCursor activitiesCursor = activitiesTable.order(activitiesTable.getPrimaryKeyIndexName());

        if (!activitiesCursor.eof()) {
            do {
                Integer id = fromLong(activitiesCursor.getInteger(activitiesTable.getPrimaryKeyIndexName()));
                String name = activitiesCursor.getString("name");

                activities.put(id, new Activity(name));
            } while (activitiesCursor.next());
        }

        return activities;
    }

    private Map<Integer, Task> loadTasks(SqlJetDb hamsterDb, Map<Integer, Activity> activityMap) throws SqlJetException {
        Map<Integer, Task> tasks = Maps.newHashMap();
        ISqlJetTable tasksTable = hamsterDb.getTable("facts");
        ISqlJetCursor activitiesCursor = tasksTable.order(tasksTable.getPrimaryKeyIndexName());

        if (!activitiesCursor.eof()) {
            do {
                Integer id = fromLong(activitiesCursor.getInteger(tasksTable.getPrimaryKeyIndexName()));
                String name = activitiesCursor.getString("name");

                tasks.put(id, new Task(name));
            } while (activitiesCursor.next());
        }

        return Task;
    }

    private Integer fromLong(long number) {
        return Integer.valueOf(Long.valueOf(number).intValue());
    }
}
