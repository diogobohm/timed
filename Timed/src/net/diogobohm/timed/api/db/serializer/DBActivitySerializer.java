/**
 * TODO: define a license.
 */
package net.diogobohm.timed.api.db.serializer;

import net.diogobohm.timed.api.db.domain.DBActivity;

/**
 *
 * @author diogo.bohm
 */
public class DBActivitySerializer implements DBSerializer<DBActivity> {

    @Override
    public DBActivity deserialize(Object[] data) {
        Integer id = ((Long) data[0]).intValue();
        String name = (String) data[1];

        DBActivity object = new DBActivity(name);
        object.setId(id);

        return object;
    }

    @Override
    public Object[] serialize(DBActivity object) {
        return new Object[]{object.getName()};
    }

}
