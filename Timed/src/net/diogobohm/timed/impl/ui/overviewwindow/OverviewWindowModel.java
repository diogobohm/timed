/**
 * TODO: define a license.
 */
package net.diogobohm.timed.impl.ui.overviewwindow;

import com.google.common.collect.Lists;
import java.awt.Window;
import java.text.ParseException;
import java.util.Date;
import java.util.List;
import net.diogobohm.timed.api.ui.mvc.MVCModel;
import net.diogobohm.timed.api.ui.domain.DayTaskList;
import net.diogobohm.timed.api.ui.domain.Overview;
import net.diogobohm.timed.api.ui.mvc.model.NewTypedValueModel;
import net.diogobohm.timed.impl.ui.daytasklist.DayTaskListController;
import net.diogobohm.timed.impl.ui.factory.DayTaskListControllerFactory;
import org.apache.commons.lang3.time.FastDateFormat;

/**
 *
 * @author diogo.bohm
 */
public class OverviewWindowModel {

    private static final FastDateFormat DAY_FORMATTER = FastDateFormat.getInstance("yyyy-MM-dd");

    private final DayTaskListControllerFactory itemFactory;
    private final NewTypedValueModel<String> startDateHolder;
    private final NewTypedValueModel<String> endDateHolder;
    private final NewTypedValueModel<List<DayTaskListController>> taskListsHolder;
    
    public OverviewWindowModel(DayTaskListControllerFactory itemFactory) {
        this.itemFactory = itemFactory;

        startDateHolder = new NewTypedValueModel();
        endDateHolder = new NewTypedValueModel();
        taskListsHolder = new NewTypedValueModel(Lists.newArrayList());
    }

    public void setDomainBean(Window owner, Overview domainBean) {
        List<DayTaskListController> controllers = createDayTaskListControllers(owner, domainBean.getDayTaskLists());

        getTaskListsHolder().setTypedValue(controllers);
    }

    public Date getStartDate() {
        try {
            return DAY_FORMATTER.parse(getStartDateHolder().getValue());
        } catch (ParseException ex) {
            ex.printStackTrace();
        }

        return new Date();
    }

    public Date getEndDate() {
        try {
            return DAY_FORMATTER.parse(getEndDateHolder().getValue());
        } catch (ParseException ex) {
            ex.printStackTrace();
        }

        return new Date();
    }

    public void setDates(Date startDate, Date endDate) {
        getStartDateHolder().setTypedValue(DAY_FORMATTER.format(startDate));
        getEndDateHolder().setTypedValue(DAY_FORMATTER.format(endDate));
    }

    protected NewTypedValueModel<String> getStartDateHolder() {
        return startDateHolder;
    }

    protected NewTypedValueModel<String> getEndDateHolder() {
        return endDateHolder;
    }

    protected NewTypedValueModel<List<DayTaskListController>> getTaskListsHolder() {
        return taskListsHolder;
    }

    private List<DayTaskListController> createDayTaskListControllers(Window owner, List<DayTaskList> dayTaskLists) {
        return itemFactory.createControllers(owner, dayTaskLists);
    }

}
