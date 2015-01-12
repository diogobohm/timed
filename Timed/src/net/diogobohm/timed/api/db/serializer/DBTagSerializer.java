/**
 * TODO: define a license.
 */
package net.diogobohm.timed.api.db.serializer;

import com.google.common.collect.Maps;
import java.util.Map;
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
    public Map<String, Object> serialize(DBTag object) {
        Map<String, Object> valueMap = Maps.newHashMap();

        valueMap.put("name", object.getName());

        return valueMap;
    }

}
