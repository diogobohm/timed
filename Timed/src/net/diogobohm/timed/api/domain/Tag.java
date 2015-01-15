/**
 * TODO: define a license.
 */
package net.diogobohm.timed.api.domain;

import com.google.common.collect.Sets;
import java.util.Set;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 *
 * @author diogo
 */
public class Tag {

    private final String name;

    public Tag(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    @Override
    public int hashCode() {
        return HashCodeBuilder.reflectionHashCode(this);
    }

    @Override
    public boolean equals(Object object) {
        return EqualsBuilder.reflectionEquals(this, object);
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

    public static String buildTagString(Set<Tag> tags) {
        StringBuilder build = new StringBuilder();

        for (Tag tag : tags) {
            build.append(String.format("#%s ", tag.getName()));
        }

        return build.toString();
    }

    public static Set<Tag> parseTagsFromString(String tagExpression) {
        String trimmedTags = tagExpression.replaceAll(" ", "").replaceAll(",", "").replaceAll(";", "");
        Set<Tag> tagSet = Sets.newHashSet();

        for (String tagName : trimmedTags.split("#")) {
            if (!tagName.isEmpty()) {
                tagSet.add(new Tag(tagName));
            }
        }

        return tagSet;
    }

}
