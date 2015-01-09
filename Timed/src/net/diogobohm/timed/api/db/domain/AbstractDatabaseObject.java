/**
 * TODO: define a license.
 */
package net.diogobohm.timed.api.db.domain;

import net.diogobohm.timed.api.db.access.DatabaseObject;

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
    public void setId(Integer id) {
        this.id = id;
    }
}
