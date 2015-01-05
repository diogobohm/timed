/**
 * TODO: define a license.
 */
package net.diogobohm.timed.api.ui.mvc;

/**
 *
 * @author diogo.bohm
 */
public abstract class MVCController<MVCModel, MVCView> {

    abstract protected MVCModel getModel();

    abstract protected MVCView getView();
}
