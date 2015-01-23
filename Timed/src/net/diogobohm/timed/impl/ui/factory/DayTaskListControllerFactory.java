/**
 * TODO: define a license.
 */
package net.diogobohm.timed.impl.ui.factory;

import com.google.common.collect.Lists;
import java.util.List;
import net.diogobohm.timed.api.ui.domain.DayTaskList;
import net.diogobohm.timed.api.ui.domain.Overview;
import net.diogobohm.timed.api.ui.mvc.controller.DomainEditor;
import net.diogobohm.timed.impl.ui.daytasklist.DayTaskListController;

/**
 *
 * @author diogo.bohm
 */
public class DayTaskListControllerFactory {

    public List<DayTaskListController> createControllers(List<DayTaskList> dayTaskLists) {
        List<DayTaskListController> controllers = Lists.newArrayList();

        for (DayTaskList dayTasks : dayTaskLists) {
            DayTaskListController controller = new DayTaskListController();
            controller.setDomain(dayTasks);

            controllers.add(controller);
        }

        return controllers;
    }

}
