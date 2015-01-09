/**
 * TODO: define a license.
 */
package net.diogobohm.timed.api.db.access;

/**
 *
 * @author diogo.bohm
 */
public class DatabaseConnection {

    private static Database database;

    public static Database getConnection() {
        return getDatabase();
    }

    private static Database getDatabase() {
        if (database == null) {
            database = new Database();
        }

        return database;
    }
}
