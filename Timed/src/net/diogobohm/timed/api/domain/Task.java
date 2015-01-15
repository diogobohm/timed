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

    private final Activity activity;
    private final Project project;
    private final Date start;
    private final Optional<Date> finish;
    private final String description;
    private final Set<Tag> tags;
    
    public Task(Activity activity, Project project, Date start, Optional<Date> finish, String description,
            Set<Tag> tags) {
        this.start = start;
        this.finish = finish;
        this.activity = activity;
        this.tags = tags;
        this.project = project;
        this.description = description;
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

    public String getDescription() {
        return description;
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
        String tags = Tag.buildTagString(getTags());
        return getActivity().getName() + "@" + getProject().getName() + " " + tags;
    }

}
