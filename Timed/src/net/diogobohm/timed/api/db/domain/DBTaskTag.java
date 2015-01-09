/**
 * TODO: define a license.
 */
package net.diogobohm.timed.api.db.domain;

import net.diogobohm.timed.api.db.access.configuration.DBObjectConfiguration;
import static net.diogobohm.timed.api.db.domain.DBTask.CONFIGURATION;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 *
 * @author diogo
 */
public class DBTaskTag extends AbstractDatabaseObject {

    public static final DBObjectConfiguration CONFIGURATION = DBObjectConfiguration.TASK_TAG;

    private final Integer taskId;
    private final Integer tagId;

    public DBTaskTag(Integer taskId, Integer tagId) {
        this.taskId = taskId;
        this.tagId = tagId;
    }

    public Integer getTaskId() {
        return taskId;
    }

    public Integer getTagId() {
        return tagId;
    }

    @Override
    public DBObjectConfiguration getConfiguration() {
        return CONFIGURATION;
    }

    @Override
    public Object getIndexValue() {
        return getTaskId();
    }

    @Override
    public int hashCode() {
        return HashCodeBuilder.reflectionHashCode(this);
    }

    @Override
    public boolean equals(Object object) {
        return EqualsBuilder.reflectionEquals(this, object);
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}
