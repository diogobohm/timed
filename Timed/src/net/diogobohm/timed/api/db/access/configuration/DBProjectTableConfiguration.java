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
    private static final String INDEX_NAME = "project_name";

    @Override
    public String getTableName() {
        return TABLE_NAME;
    }

    @Override
    public String getIndexName() {
        return INDEX_NAME;
    }

    @Override
    public String getCreateTableQuery() {
        return "CREATE TABLE " + getTableName() + " ("
                + " id INTEGER PRIMARY KEY, "
                + " name TEXT NOT NULL UNIQUE);";
    }

    @Override
    public String getCreateIndexQuery() {
        return "CREATE INDEX " + getIndexName() + " ON " + getTableName() + "(name);";
    }

    @Override
    public boolean isUniqueIndex() {
        return true;
    }

}
