/**
 * TODO: define a license.
 */
package net.diogobohm.timed.api.db.access.configuration;

import net.diogobohm.timed.api.db.domain.DBActivity;
import net.diogobohm.timed.api.db.serializer.DBActivitySerializer;
import net.diogobohm.timed.api.db.serializer.DBProjectSerializer;
import net.diogobohm.timed.api.db.serializer.DBSerializer;
import net.diogobohm.timed.api.db.serializer.DBTagSerializer;
import net.diogobohm.timed.api.db.serializer.DBTaskSerializer;
import net.diogobohm.timed.api.db.serializer.DBTaskTagSerializer;

/**
 *
 * @author diogo.bohm
 */
public enum DBObjectConfiguration {

    ACTIVITY(new DBActivityTableConfiguration(), new DBActivitySerializer(), DBActivity.class),
    PROJECT(new DBProjectTableConfiguration(), new DBProjectSerializer(), DBActivity.class),
    TAG(new DBTagTableConfiguration(), new DBTagSerializer(), DBActivity.class),
    TASK(new DBTaskTableConfiguration(), new DBTaskSerializer(), DBActivity.class),
    TASK_TAG(new DBTaskTagTableConfiguration(), new DBTaskTagSerializer(), DBActivity.class);

    private final DBTableConfiguration tableConfiguration;
    private final DBSerializer serializer;
    private final Class objectClass;

    private DBObjectConfiguration(DBTableConfiguration configuration, DBSerializer serializer, Class objectClass) {
        this.tableConfiguration = configuration;
        this.serializer = serializer;
        this.objectClass = objectClass;
    }

    public DBTableConfiguration getTableConfiguration() {
        return tableConfiguration;
    }

    public DBSerializer getSerializer() {
        return serializer;
    }

    public Class getObjectClass() {
        return objectClass;
    }
}
