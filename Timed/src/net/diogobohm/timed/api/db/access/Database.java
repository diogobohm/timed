/**
 * TODO: define a license.
 */
package net.diogobohm.timed.api.db.access;

import com.google.common.collect.Lists;
import java.io.File;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.security.auth.login.Configuration;
import net.diogobohm.timed.api.db.access.configuration.DBTableConfiguration;
import net.diogobohm.timed.api.db.access.configuration.DBObjectConfiguration;
import net.diogobohm.timed.api.db.domain.DBActivity;
import net.diogobohm.timed.api.db.exception.DatabaseAccessException;
import net.diogobohm.timed.api.db.serializer.DBSerializer;
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

    public void write(DatabaseObject object) throws DatabaseAccessException {
        DBObjectConfiguration configuration = object.getConfiguration();
        DBTableConfiguration tableConfuration = configuration.getTableConfiguration();
        DBSerializer serializer = configuration.getSerializer();

        startTransaction();

        ISqlJetTable table = getTable(tableConfuration.getTableName());
        ISqlJetCursor cursor;
        long id;

        try {
            cursor = table.lookup(tableConfuration.getIndexName(), object.getIndexValue());
        } catch (SqlJetException exception) {
            // Register does not exist, create
            try {
                id = table.insert(serializer.serialize(object));

                object.setId(Long.valueOf(id).intValue());
            } catch (SqlJetException insertException) {
                throw new DatabaseAccessException(insertException,
                        "Error writing object at " + tableConfuration.getTableName());
            } finally {
                closeTransaction();
                return;
            }
        }

        try {
            id = cursor.getRowId();
            cursor.update(serializer.serialize(object));
            cursor.close();

            object.setId(Long.valueOf(id).intValue());
        } catch (SqlJetException exception) {
            throw new DatabaseAccessException(exception, "Error updating object at " + tableConfuration.getTableName());
        } finally {
            closeTransaction();
        }
    }

    public void remove(DatabaseObject object) throws DatabaseAccessException {
        DBObjectConfiguration configuration = object.getConfiguration();
        DBTableConfiguration tableConfuration = configuration.getTableConfiguration();
        startTransaction();

        try {
            ISqlJetTable table = getTable(tableConfuration.getTableName());
            ISqlJetCursor cursor = table.lookup("id", object.getId());

            while (!cursor.eof()) {
                cursor.delete();
            }

            cursor.close();
        } catch (SqlJetException exception) {
            throw new DatabaseAccessException(exception, "Error removing object at " + tableConfuration.getTableName());
        } finally {
            closeTransaction();
        }
    }

    public void loadIdFromAttributes(DatabaseObject object) throws DatabaseAccessException {
        DBObjectConfiguration configuration = object.getConfiguration();
        DBTableConfiguration tableConfuration = configuration.getTableConfiguration();
        startTransaction();

        try {
            ISqlJetTable table = getTable(tableConfuration.getTableName());
            ISqlJetCursor cursor = table.lookup(tableConfuration.getIndexName(), object.getIndexValue());

            if (cursor.eof()) {
                throw new DatabaseAccessException(null, "Could not find object at " + tableConfuration.getTableName());
            }
            Integer id = Long.valueOf(cursor.getInteger("id")).intValue();
            object.setId(id);

            cursor.close();
        } catch (SqlJetException exception) {
            throw new DatabaseAccessException(exception, "Error selecting object at " + tableConfuration.getTableName());
        } finally {
            closeTransaction();
        }
    }

    public <T extends DatabaseObject> List<T> loadObjects(DBObjectConfiguration configuration) throws DatabaseAccessException {
        List<T> objects = Lists.newArrayList();
        startTransaction();

        DBSerializer<T> serializer = configuration.getSerializer();
        ISqlJetTable table = getTable(configuration.getTableConfiguration().getTableName());

        try {
            ISqlJetCursor cursor = table.open();

            if (!cursor.eof()) {
                do {
                    objects.add(serializer.deserialize(cursor.getRowValues()));
                } while (cursor.next());
            }

            cursor.close();
        } catch (SqlJetException exception) {
            throw new DatabaseAccessException(exception, "Error selecting activities!");
        } finally {
            closeTransaction();
        }

        return objects;
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
        createTable(DBObjectConfiguration.ACTIVITY);
        createTable(DBObjectConfiguration.PROJECT);
        createTable(DBObjectConfiguration.TAG);
        createTable(DBObjectConfiguration.TASK);
        createTable(DBObjectConfiguration.TASK_TAG);
    }

    private void createTable(DBObjectConfiguration objectConfiguration) throws SqlJetException {
        connection.createTable(objectConfiguration.getTableConfiguration().getCreateTableQuery());
    }
}
