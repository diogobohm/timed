package net.diogobohm.timed.api.db.domain;

import com.google.common.base.Optional;
import java.util.Date;
import java.util.Set;
import net.diogobohm.timed.api.db.access.Database;
import net.diogobohm.timed.api.db.access.configuration.DBObjectConfiguration;
import net.diogobohm.timed.api.db.exception.DatabaseAccessException;
import net.diogobohm.timed.api.ui.mvc.model.bean.LabeledBean;
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

    private final String startDateTime;
    private final String finishDateTime;
    private final Integer activityId;
    private final Integer projectId;
    private final String description;

    public DBTask(String startDateTime, String finishDateTime, Integer activityId, Integer projectId,
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
