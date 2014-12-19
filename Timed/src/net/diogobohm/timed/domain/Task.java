package net.diogobohm.timed.domain;

import com.google.common.base.Optional;
import java.util.Date;
import java.util.Set;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * Class that represents a given task.
 *
 * @author diogo
 */
public class Task {

    private final Date start;
    private final Optional<Date> finish;
    private final String name;
    private final Set<Tag> tags;
    private final Project project;

    public Task(Date start, Optional<Date> finish, String name, Set<Tag> tags, Project project) {
        this.start = start;
        this.finish = finish;
        this.name = name;
        this.tags = tags;
        this.project = project;
    }

    public Date getStart() {
        return start;
    }

    public Optional<Date> getFinish() {
        return finish;
    }

    public String getName() {
        return name;
    }

    public Set<Tag> getTags() {
        return tags;
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
