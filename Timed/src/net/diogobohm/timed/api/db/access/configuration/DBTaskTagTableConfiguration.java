/**
 * TODO: define a license.
 */
package net.diogobohm.timed.api.db.access.configuration;

/**
 *
 * @author diogo.bohm
 */
public class DBTaskTagTableConfiguration implements DBTableConfiguration {

    private static final String TABLE_NAME = "task_tag";

    @Override
    public String getTableName() {
        return TABLE_NAME;
    }

    @Override
    public String getCreateTableQuery() {
        return "CREATE TABLE " + getTableName() + " ("
                + " id INTEGER PRIMARY KEY,"
                + " task_id REFERENCES task(id),"
                + " tag_id REFERENCES tag(id));";
    }

    @Override
    public String getIndexName() {
        return "task_id";
    }

}
