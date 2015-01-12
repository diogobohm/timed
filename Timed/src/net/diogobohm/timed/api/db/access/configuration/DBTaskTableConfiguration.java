/**
 * TODO: define a license.
 */
package net.diogobohm.timed.api.db.access.configuration;

/**
 *
 * @author diogo.bohm
 */
public class DBTaskTableConfiguration implements DBTableConfiguration {

    private static final String TABLE_NAME = "task";
    private static final String INDEX_NAME = "task_start_time";

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
                + " id INTEGER PRIMARY KEY,"
                + " activity_id REFERENCES activity(id) NOT NULL,"
                + " project_id REFERENCES project(id) NOT NULL,"
                + " start_time TIMESTAMP NOT NULL,"
                + " end_time TIMESTAMP,"
                + " description TEXT);";
    }

    @Override
    public String getCreateIndexQuery() {
        return "CREATE INDEX " + getIndexName() + " ON " + getTableName() + "(start_time);";
    }

}
