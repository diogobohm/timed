/**
 * TODO: define a license.
 */
package net.diogobohm.timed.api.db.access;

import net.diogobohm.timed.api.db.access.configuration.DBObjectConfiguration;

/**
 *
 * @author diogo.bohm
 */
public interface DatabaseObject {

    Integer getId();

    void setId(Integer id);

    DBObjectConfiguration getConfiguration();

    Object getIndexValue();
}
