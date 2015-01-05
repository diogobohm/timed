package net.diogobohm.timed.api.domain;

import com.google.common.base.Optional;
import java.util.Date;
import java.util.Set;
import net.diogobohm.timed.api.ui.mvc.model.bean.LabeledBean;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * Class that represents a given task.
 *
 * @author diogo
 */
public class Task implements LabeledBean {

    private final Date start;
    private final Optional<Date> finish;
    private final Activity activity;
    private final Set<Tag> tags;
    private final Project project;

    public Task(Date start, Optional<Date> finish, Activity activity, Set<Tag> tags, Project project) {
        this.start = start;
        this.finish = finish;
        this.activity = activity;
        this.tags = tags;
        this.project = project;
    }

    public Date getStart() {
        return start;
    }

    public Optional<Date> getFinish() {
        return finish;
    }

    public Activity getActivity() {
        return activity;
    }

    public Set<Tag> getTags() {
        return tags;
    }

    public Project getProject() {
        return project;
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

    @Override
    public String getLabel() {
        return getActivity().getName() + "@" + getProject().getName();
    }

}
