/**
 * TODO: define a license.
 */
package net.diogobohm.timed.impl.codec;

import com.google.common.collect.Sets;
import java.util.Set;
import net.diogobohm.timed.api.codec.TagCodec;
import net.diogobohm.timed.api.db.domain.DBTag;
import net.diogobohm.timed.api.domain.Tag;

/**
 *
 * @author diogo.bohm
 */
public class TagCodecImpl implements TagCodec {

    @Override
    public DBTag encode(Tag tag) {
        return new DBTag(tag.getName());
    }

    @Override
    public Tag decode(DBTag tag) {
        return new Tag(tag.getName());
    }

    @Override
    public Set<Tag> decode(Set<DBTag> tags) {
        Set<Tag> decodedTags = Sets.newHashSet();

        for (DBTag tag : tags) {
            decodedTags.add(decode(tag));
        }

        return decodedTags;
    }

}
