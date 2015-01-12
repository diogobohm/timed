/**
 * TODO: define a license.
 */
package net.diogobohm.timed.api.db.serializer;

import java.util.Map;
import net.diogobohm.timed.api.db.access.DatabaseObject;

/**
 *
 * @author diogo.bohm
 */
public interface DBSerializer<T extends DatabaseObject> {

    T deserialize(Object[] data);

    Map<String, Object> serialize(T object);
}
