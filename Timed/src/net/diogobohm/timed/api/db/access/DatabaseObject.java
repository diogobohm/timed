/**
 * TODO: define a license.
 */
package net.diogobohm.timed.api.db.access;

import net.diogobohm.timed.api.db.exception.DatabaseAccessException;

/**
 *
 * @author diogo.bohm
 */
public interface DatabaseObject {

    Integer getId();

    String getTableName();

    Object[] getSerializedValues();

    void write(Database database) throws DatabaseAccessException;

    void load(Database database) throws DatabaseAccessException;

    void remove(Database database) throws DatabaseAccessException;
}
