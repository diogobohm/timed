/**
 * TODO: define a license.
 */
package net.diogobohm.timed.api.db.serializer;

import net.diogobohm.timed.api.db.domain.DBProject;

/**
 *
 * @author diogo.bohm
 */
public class DBProjectSerializer implements DBSerializer<DBProject> {

    @Override
    public DBProject deserialize(Object[] data) {
        Integer id = ((Long) data[0]).intValue();
        String name = (String) data[1];

        DBProject object = new DBProject(name);
        object.setId(id);

        return object;
    }

    @Override
    public Object[] serialize(DBProject object) {
        return new Object[]{object.getName()};
    }

}
