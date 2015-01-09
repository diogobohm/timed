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

    @Override
    public String getTableName() {
        return TABLE_NAME;
    }

    @Override
    public String getCreateTableQuery() {
        return "CREATE TABLE " + getTableName() + " ("
                + " id INTEGER PRIMARY KEY, "
                + " start_time DATETIME NOT NULL,"
                + " end_time DATETIME,"
                + " activity_id REFERENCES activity(id),"
                + " project_id REFERENCES project(id),"
                + " description TEXT);";
    }

    @Override
    public String getIndexName() {
        return "start_time";
    }

}
