/**
 * TODO: define a license.
 */
package net.diogobohm.timed.api.db.serializer;

import net.diogobohm.timed.api.db.domain.DBTag;

/**
 *
 * @author diogo.bohm
 */
public class DBTagSerializer implements DBSerializer<DBTag> {

    @Override
    public DBTag deserialize(Object[] data) {
        Integer id = ((Long) data[0]).intValue();
        String name = (String) data[1];

        DBTag object = new DBTag(name);
        object.setId(id);

        return object;
    }

    @Override
    public Object[] serialize(DBTag object) {
        return new Object[]{object.getName()};
    }

}
