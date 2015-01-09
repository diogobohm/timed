/**
 * TODO: define a license.
 */
package net.diogobohm.timed.api.db.access.configuration;

/**
 *
 * @author diogo.bohm
 */
public class DBProjectTableConfiguration implements DBTableConfiguration {

    private static final String TABLE_NAME = "project";

    @Override
    public String getTableName() {
        return TABLE_NAME;
    }

    @Override
    public String getCreateTableQuery() {
        return "CREATE TABLE " + getTableName() + " ("
                + " id INTEGER PRIMARY KEY, "
                + " name TEXT NOT NULL UNIQUE);";
    }

    @Override
    public String getIndexName() {
        return "name";
    }

}
