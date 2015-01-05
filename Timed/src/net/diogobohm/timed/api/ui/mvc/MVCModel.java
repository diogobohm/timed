/**
 * TODO: define a license.
 */
package net.diogobohm.timed.api.ui.mvc;

/**
 *
 * @author diogo.bohm
 */
public interface MVCModel<T> {

    T getDomainBean();

    void setDomainBean(T domainBean);
}
