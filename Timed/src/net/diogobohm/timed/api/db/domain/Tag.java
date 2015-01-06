/**
 * TODO: define a license.
 */
package net.diogobohm.timed.api.db.domain;

import net.diogobohm.timed.api.db.access.Database;
import net.diogobohm.timed.api.db.exception.DatabaseAccessException;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 *
 * @author diogo
 */
public class Tag extends AbstractDatabaseObject {

    private static final String TABLE_NAME = "tag";

    private final String name;

    public Tag(String name) {
        this.name = name;
    }

    public static String getCreateTableQuery() {
        return "CREATE TABLE " + TABLE_NAME + " ("
                + " id INTEGER PRIMARY KEY, "
                + " name TEXT NOT NULL UNIQUE);";
    }

    public String getName() {
        return name;
    }

    @Override
    public String getTableName() {
        return TABLE_NAME;
    }

    @Override
    public Object[] getSerializedValues() {
        return new Object[]{getName()};
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
