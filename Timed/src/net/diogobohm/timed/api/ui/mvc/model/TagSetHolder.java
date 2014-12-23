/**
 * TODO: define a license.
 */
package net.diogobohm.timed.api.ui.mvc.model;

import java.util.Set;
import net.diogobohm.timed.api.domain.Tag;

/**
 *
 * @author diogo.bohm
 */
public class TagSetHolder extends AbstractTypedValueModel<Set<Tag>> {

    @Override
    public String buildRenderedView() {
        Set<Tag> tagSet = getValue();

        if (tagSet.isEmpty()) {
            return "No tags.";
        }

        return buildHashTags(tagSet);
    }

    private String buildHashTags(Set<Tag> tagSet) {
        StringBuilder build = new StringBuilder();
        boolean first = true;

        for (Tag tag : tagSet) {
            if (!first) {
                build.append(String.format(", #%s", tag.getName()));
            } else {
                build.append(String.format("#%s", tag.getName()));
                first = false;
            }
        }

        return build.toString();
    }

}
