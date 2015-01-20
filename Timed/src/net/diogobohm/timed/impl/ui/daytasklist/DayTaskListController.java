/**
 * TODO: define a license.
 */
package net.diogobohm.timed.impl.ui.daytasklist;

import net.diogobohm.timed.api.ui.mvc.MVCController;
import net.diogobohm.timed.api.ui.domain.DayTaskList;
import net.diogobohm.timed.api.ui.domain.Overview;
import net.diogobohm.timed.api.ui.mvc.controller.DomainEditor;

/**
 *
 * @author diogo.bohm
 */
public class DayTaskListController extends MVCController<DayTaskListModel, DayTaskListView> implements DomainEditor<DayTaskList> {

    private final DomainEditor<Overview> overviewEditor;

    private DayTaskListModel model;
    private DayTaskListView view;

    public DayTaskListController(DomainEditor<Overview> overviewEditor) {
        this.overviewEditor = overviewEditor;
    }

    @Override
    protected DayTaskListModel getModel() {
        if (model == null) {
            model = new DayTaskListModel();
        }

        return model;
    }

    @Override
    public DayTaskListView getView() {
        if (view == null) {
            view = new DayTaskListView(getModel());
        }

        return view;
    }

    @Override
    public void updateDomain(DayTaskList oldValue, DayTaskList newValue) {
        overviewEditor.updateDomain(null, null);
    }

}
