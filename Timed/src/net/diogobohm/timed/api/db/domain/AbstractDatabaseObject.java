/**
 * TODO: define a license.
 */
package net.diogobohm.timed.api.db.domain;

import net.diogobohm.timed.api.db.access.Database;
import net.diogobohm.timed.api.db.access.DatabaseObject;
import net.diogobohm.timed.api.db.exception.DatabaseAccessException;

/**
 *
 * @author diogo.bohm
 */
public abstract class AbstractDatabaseObject implements DatabaseObject {

    private Integer id;

    @Override
    public Integer getId() {
        return id;
    }

    @Override
    public void write(Database database) throws DatabaseAccessException {
        Integer objectId = database.writeObject(this);

        setId(objectId);
    }

    @Override
    public void remove(Database database) throws DatabaseAccessException {
        database.removeObject(this);
    }

    private void setId(Integer id) {
        this.id = id;
    }
}
