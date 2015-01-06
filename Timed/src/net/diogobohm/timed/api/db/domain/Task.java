package net.diogobohm.timed.api.db.domain;

import com.google.common.base.Optional;
import java.util.Date;
import java.util.Set;
import net.diogobohm.timed.api.db.access.Database;
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
public class Task extends AbstractDatabaseObject {

    private static final String TABLE_NAME = "task";

    private final String startDateTime;
    private final String finishDateTime;
    private final Integer activityId;
    private final Integer projectId;
    private final String description;

    public Task(String startDateTime, String finishDateTime, Integer activityId, Integer projectId,
            String description) {
        this.startDateTime = startDateTime;
        this.finishDateTime = finishDateTime;
        this.activityId = activityId;
        this.projectId = projectId;
        this.description = description;
    }

    public static String getCreateTableQuery() {
        return "CREATE TABLE " + TABLE_NAME + " ("
                + " id INTEGER PRIMARY KEY, "
                + " start_time DATETIME NOT NULL,"
                + " end_time DATETIME,"
                + " activity_id REFERENCES activity(id),"
                + " project_id REFERENCES project(id),"
                + " description TEXT);";
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
    public String getTableName() {
        return TABLE_NAME;
    }

    @Override
    public Object[] getSerializedValues() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void load(Database database) throws DatabaseAccessException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
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
