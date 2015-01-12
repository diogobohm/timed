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
    private static final String INDEX_NAME = "task_tag_task_id";

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
                + " task_id REFERENCES task(id) NOT NULL,"
                + " tag_id REFERENCES tag(id) NOT NULL);";
    }

    @Override
    public String getCreateIndexQuery() {
        return "CREATE INDEX " + getIndexName() + " ON " + getTableName() + "(task_id);";
    }

}
