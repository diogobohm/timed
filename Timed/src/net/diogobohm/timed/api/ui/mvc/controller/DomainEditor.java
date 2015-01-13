/**
 * TODO: define a license.
 */
package net.diogobohm.timed.api.ui.mvc.controller;

/**
 *
 * @author diogo.bohm
 */
public interface DomainEditor<T> {

    void updateDomain(T oldValue, T newValue);
}
