/**
 * TODO: define a license.
 */
package net.diogobohm.timed.api.db.access;

import com.google.common.collect.Lists;
import java.io.File;
import java.util.List;
import java.util.Map;
import net.diogobohm.timed.api.db.access.configuration.DBTableConfiguration;
import net.diogobohm.timed.api.db.access.configuration.DBObjectConfiguration;
import net.diogobohm.timed.api.db.exception.DatabaseAccessException;
import net.diogobohm.timed.api.db.serializer.DBSerializer;
import org.tmatesoft.sqljet.core.SqlJetException;
import org.tmatesoft.sqljet.core.SqlJetTransactionMode;
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
    
    public void write(DatabaseObject object) throws DatabaseAccessException {
        DBObjectConfiguration configuration = object.getConfiguration();
        DBTableConfiguration tableConfuration = configuration.getTableConfiguration();
        DBSerializer serializer = configuration.getSerializer();

        ISqlJetTable table = getTable(tableConfuration.getTableName());
        ISqlJetCursor cursor = null;

        String indexName = tableConfuration.getIndexName();
        Object indexValue = object.getIndexValue();

        try {
            cursor = table.lookup(indexName, indexValue);
        } catch (SqlJetException exception) {
            throw new DatabaseAccessException(exception,
                    "Error on object lookup at " + tableConfuration.getTableName());
        }

        Integer id;

        try {
            if (cursor.eof()) {
                // Register does not exist, create
                id = insertRecord(table, serializer.serialize(object));
            } else {
                object.setId(Long.valueOf(cursor.getRowId()).intValue());
                DatabaseObject currObject = serializer.deserialize(cursor.getRowValues());
                
                if (!currObject.equals(object)) {
                    id = updateRecord(cursor, serializer.serialize(object));
                    System.out.println("Updating " + currObject + " to " + object);
                } else {
                    id = Long.valueOf(cursor.getRowId()).intValue();
                }
            }

            cursor.close();
        } catch (SqlJetException insertException) {
            throw new DatabaseAccessException(insertException,
                    "Error writing object at " + tableConfuration.getTableName());
        }

        object.setId(id);
    }

    public void remove(DatabaseObject object) throws DatabaseAccessException {
        DBObjectConfiguration configuration = object.getConfiguration();
        DBTableConfiguration tableConfuration = configuration.getTableConfiguration();

        try {
            ISqlJetTable table = getTable(tableConfuration.getTableName());
            ISqlJetCursor cursor = table.lookup("id", object.getId());

            while (!cursor.eof()) {
                cursor.delete();
            }

            cursor.close();
        } catch (SqlJetException exception) {
            throw new DatabaseAccessException(exception, "Error removing object at " + tableConfuration.getTableName());
        }
    }

    public void loadIdFromAttributes(DatabaseObject object) throws DatabaseAccessException {
        DBObjectConfiguration configuration = object.getConfiguration();
        DBTableConfiguration tableConfuration = configuration.getTableConfiguration();

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
        }
    }

    public <T extends DatabaseObject> List<T> loadObjects(DBObjectConfiguration configuration) throws DatabaseAccessException {
        List<T> objects = Lists.newArrayList();

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
            } catch (DatabaseAccessException exception) {
                exception.printStackTrace();
                System.exit(1);
            }
        }
    }

    public void startTransaction() throws DatabaseAccessException {
        try {
            connection.beginTransaction(SqlJetTransactionMode.WRITE);
        } catch (SqlJetException exception) {
            throw new DatabaseAccessException(exception, "Error starting transaction!");
        }
    }

    public void closeTransaction() throws DatabaseAccessException {
        try {
            connection.commit();
        } catch (SqlJetException exception) {
            throw new DatabaseAccessException(exception, "Error closing transaction!");
        }
    }
    
    public void rollbackTransaction() throws DatabaseAccessException {
        try {
            connection.rollback();
        } catch (SqlJetException exception) {
            throw new DatabaseAccessException(exception, "Error rolling back transaction!");
        }
    }

    private Integer insertRecord(ISqlJetTable table, Map<String, Object> serializedObject) throws DatabaseAccessException {
        long id = 0;

        try {
            id = table.insertByFieldNames(serializedObject);
        } catch (SqlJetException insertException) {
            throw new DatabaseAccessException(insertException, "Error inserting object!");
        }

        return Long.valueOf(id).intValue();
    }

    private Integer updateRecord(ISqlJetCursor cursor, Map<String, Object> serializedObject) throws DatabaseAccessException {
        long id = 0;

        try {
            id = cursor.getRowId();
            cursor.updateByFieldNames(serializedObject);
        } catch (SqlJetException insertException) {
            throw new DatabaseAccessException(insertException, "Error updating object!");
        }

        return Long.valueOf(id).intValue();
    }

    private ISqlJetTable getTable(String tableName) throws DatabaseAccessException {
        try {
            return connection.getTable(tableName);
        } catch (SqlJetException exception) {
            throw new DatabaseAccessException(exception, "Error getting table " + tableName);
        }
    }

    private void createDatabase() throws DatabaseAccessException {
        createTableAndIndex(DBObjectConfiguration.ACTIVITY);
        createTableAndIndex(DBObjectConfiguration.PROJECT);
        createTableAndIndex(DBObjectConfiguration.TAG);
        createTableAndIndex(DBObjectConfiguration.TASK);
        createTableAndIndex(DBObjectConfiguration.TASK_TAG);
    }

    private void createTableAndIndex(DBObjectConfiguration configuration) throws DatabaseAccessException {
        DBTableConfiguration tableConfiguration = configuration.getTableConfiguration();

        try {
            connection.createTable(tableConfiguration.getCreateTableQuery());
        } catch (SqlJetException exception) {
            throw new DatabaseAccessException(exception, "Error creating table!");
        }

        try {
            connection.createIndex(tableConfiguration.getCreateIndexQuery());
        } catch (SqlJetException exception) {
            throw new DatabaseAccessException(exception, "Error creating index!");
        }
    }

}
