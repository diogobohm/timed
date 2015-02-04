/**
 * TODO: define a license.
 */
package net.diogobohm.timed.api.ui.mvc.controller;

import com.google.common.base.Optional;

/**
 *
 * @author diogo.bohm
 */
public interface DomainEditor<T> {

    void updateDomain(Optional<T> oldValue, Optional<T> newValue);
}
