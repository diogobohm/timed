/**
 * TODO: define a license.
 */
package net.diogobohm.timed.api.db.serializer;

import net.diogobohm.timed.api.db.access.DatabaseObject;

/**
 *
 * @author diogo.bohm
 */
public interface DBSerializer<T extends DatabaseObject> {

    T deserialize(Object[] data);

    Object[] serialize(T object);
}
