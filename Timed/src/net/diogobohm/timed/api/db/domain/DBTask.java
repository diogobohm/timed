package net.diogobohm.timed.api.db.domain;

import net.diogobohm.timed.api.db.access.configuration.DBObjectConfiguration;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * Class that represents a given task.
 *
 * @author diogo
 */
public class DBTask extends AbstractDatabaseObject {

    public static final DBObjectConfiguration CONFIGURATION = DBObjectConfiguration.TASK;

    private final Integer activityId;
    private final Integer projectId;
    private final String startDateTime;
    private final String finishDateTime;
    private final String description;

    public DBTask(Integer activityId, Integer projectId, String startDateTime, String finishDateTime,
            String description) {
        this.startDateTime = startDateTime;
        this.finishDateTime = finishDateTime;
        this.activityId = activityId;
        this.projectId = projectId;
        this.description = description;
    }

    public String getStartDateTime() {
        return startDateTime;
    }

    public String getFinishDateTime() {
        return finishDateTime;
    }

    public Integer getActivityId() {
        return activityId;
    }

    public Integer getProjectId() {
        return projectId;
    }

    public String getDescription() {
        return description;
    }

    @Override
    public DBObjectConfiguration getConfiguration() {
        return CONFIGURATION;
    }

    @Override
    public Object getIndexValue() {
        return getStartDateTime();
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
