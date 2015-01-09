/**
 * TODO: define a license.
 */
package net.diogobohm.timed.api.db.domain;

import net.diogobohm.timed.api.db.access.configuration.DBObjectConfiguration;
import static net.diogobohm.timed.api.db.domain.DBTaskTag.CONFIGURATION;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 *
 * @author diogo
 */
public class DBTag extends AbstractDatabaseObject {

    public static final DBObjectConfiguration CONFIGURATION = DBObjectConfiguration.TAG;

    private final String name;

    public DBTag(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    @Override
    public DBObjectConfiguration getConfiguration() {
        return CONFIGURATION;
    }

    @Override
    public Object getIndexValue() {
        return getName();
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
