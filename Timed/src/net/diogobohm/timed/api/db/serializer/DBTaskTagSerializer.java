/**
 * TODO: define a license.
 */
package net.diogobohm.timed.api.db.serializer;

import net.diogobohm.timed.api.db.domain.DBTaskTag;

/**
 *
 * @author diogo.bohm
 */
public class DBTaskTagSerializer implements DBSerializer<DBTaskTag> {

    @Override
    public DBTaskTag deserialize(Object[] data) {
        Integer id = ((Long) data[0]).intValue();
        Integer taskId = ((Long) data[1]).intValue();
        // FIXME: está criando registro null.
        Object dbTag = data[2];
        Integer tagId = null;

        if (dbTag != null) {
            tagId = ((Long) data[2]).intValue();
        }

        DBTaskTag object = new DBTaskTag(taskId, tagId);
        object.setId(id);

        return object;
    }

    @Override
    public Object[] serialize(DBTaskTag object) {
        return new Object[]{object.getTaskId(), object.getTagId()};
    }

}
