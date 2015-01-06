/**
 * TODO: define a license.
 */
package net.diogobohm.timed.api.db.access;

import java.io.File;
import net.diogobohm.timed.api.db.domain.Activity;
import net.diogobohm.timed.api.db.domain.Project;
import net.diogobohm.timed.api.db.domain.Tag;
import net.diogobohm.timed.api.db.domain.Task;
import net.diogobohm.timed.api.db.domain.TaskTag;
import net.diogobohm.timed.api.db.exception.DatabaseAccessException;
import org.tmatesoft.sqljet.core.SqlJetException;
import org.tmatesoft.sqljet.core.SqlJetTransactionMode;
import org.tmatesoft.sqljet.core.schema.SqlJetConflictAction;
import org.tmatesoft.sqljet.core.table.ISqlJetCursor;
import org.tmatesoft.sqljet.core.table.ISqlJetTable;
import org.tmatesoft.sqljet.core.table.SqlJetDb;

/**
 *
 * @author diogo.bohm
 */
public class Database {

    private static final String DATABASE_FILE = "timed.db";

    private SqlJetDb connection;

    Database() {
        initializeDatabase();
    }

    public void createTable(String query) throws DatabaseAccessException {
        try {
            connection.createTable(query);
        } catch (SqlJetException exception) {
            throw new DatabaseAccessException(exception, "Error creating table!");
        }
    }

    public Integer writeObject(DatabaseObject object) throws DatabaseAccessException {
        startTransaction();

        try {
            ISqlJetTable table = getTable(object.getTableName());
            long id = table.insertOr(SqlJetConflictAction.REPLACE, object.getSerializedValues());

            return Long.valueOf(id).intValue();
        } catch (SqlJetException exception) {
            throw new DatabaseAccessException(exception, "Error writing object at " + object.getTableName());
        } finally {
            closeTransaction();
        }
    }

    public void removeObject(DatabaseObject object) throws DatabaseAccessException {
        startTransaction();

        try {
            ISqlJetTable table = getTable(object.getTableName());
            ISqlJetCursor cursor = table.scope("id", new Object[]{object.getId()}, new Object[]{object.getId()});

            while (!cursor.eof()) {
                cursor.delete();
            }

            cursor.close();
        } catch (SqlJetException exception) {
            throw new DatabaseAccessException(exception, "Error removing object at " + object.getTableName());
        } finally {
            closeTransaction();
        }
    }

    private void initializeDatabase() {
        File databaseFile = new File(DATABASE_FILE);
        boolean databaseExists = databaseFile.exists();

        try {
            connection = SqlJetDb.open(databaseFile, true);
        } catch (SqlJetException exception) {
            System.err.println("Error opening database!");
            exception.printStackTrace();
            System.exit(1);
        }

        if (!databaseExists) {
            try {
                createDatabase();
            } catch (SqlJetException exception) {
                System.err.println("Error creating database!");
                exception.printStackTrace();
                System.exit(1);
            }
        }
    }

    private void startTransaction() throws DatabaseAccessException {
        try {
            connection.beginTransaction(SqlJetTransactionMode.WRITE);
        } catch (SqlJetException exception) {
            throw new DatabaseAccessException(exception, "Error starting transaction!");
        }
    }

    private void closeTransaction() throws DatabaseAccessException {
        try {
            connection.commit();
        } catch (SqlJetException exception) {
            throw new DatabaseAccessException(exception, "Error starting transaction!");
        }
    }

    private ISqlJetTable getTable(String tableName) throws DatabaseAccessException {
        try {
            return connection.getTable(tableName);
        } catch (SqlJetException exception) {
            throw new DatabaseAccessException(exception, "Error getting table " + tableName);
        }
    }

    private void createDatabase() throws SqlJetException {
        connection.createTable(Activity.getCreateTableQuery());
        connection.createTable(Project.getCreateTableQuery());
        connection.createTable(Tag.getCreateTableQuery());
        connection.createTable(Task.getCreateTableQuery());
        connection.createTable(TaskTag.getCreateTableQuery());
    }
}
