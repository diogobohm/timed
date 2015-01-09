/**
 * TODO: define a license.
 */
package net.diogobohm.timed.api.codec;

import java.util.Set;
import net.diogobohm.timed.api.db.domain.DBTag;
import net.diogobohm.timed.api.domain.Tag;

/**
 *
 * @author diogo.bohm
 */
public interface TagCodec {

    DBTag encode(Tag tag);

    Tag decode(DBTag tag);

    Set<Tag> decode(Set<DBTag> tags);
}
