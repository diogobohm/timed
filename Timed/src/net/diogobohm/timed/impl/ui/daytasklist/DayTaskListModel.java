/**
 * TODO: define a license.
 */
package net.diogobohm.timed.impl.ui.daytasklist;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.Date;
import javax.swing.ImageIcon;
import net.diogobohm.timed.api.domain.Task;
import net.diogobohm.timed.api.ui.mvc.MVCModel;
import net.diogobohm.timed.api.ui.domain.DayTaskList;
import net.diogobohm.timed.api.ui.domain.TaskList;
import net.diogobohm.timed.api.ui.image.ImageResource;
import net.diogobohm.timed.api.ui.mvc.model.NewTypedValueModel;
import org.apache.commons.lang3.time.FastDateFormat;

/**
 *
 * @author diogo.bohm
 */
public class DayTaskListModel implements MVCModel<DayTaskList> {

    private static final FastDateFormat DATE_NAME_FORMATTER = FastDateFormat.getInstance("EEEE, MMMM d, yyyy");

    private final NewTypedValueModel<DayTaskList> currentDayHolder;
    private final NewTypedValueModel<Boolean> expandedHolder;
    private final NewTypedValueModel<ImageResource> expandIconHolder;
    private final NewTypedValueModel<String> dayWorkedTimeHolder;
    private final NewTypedValueModel<String> dateNameHolder;
    private final NewTypedValueModel<Date> dateHolder;
    
    public DayTaskListModel() {
        currentDayHolder = new NewTypedValueModel();
        expandedHolder = new NewTypedValueModel(Boolean.FALSE);
        expandIconHolder = new NewTypedValueModel(ImageResource.ICON_EXPAND);
        dayWorkedTimeHolder = new NewTypedValueModel();
        dateNameHolder = new NewTypedValueModel();
        dateHolder = new NewTypedValueModel();
    }

    @Override
    public DayTaskList getDomainBean() {
        return getCurrentDayHolder().getValue();
    }

    @Override
    public void setDomainBean(DayTaskList dayTaskList) {
        TaskList taskList = dayTaskList.getTaskList();

        getCurrentDayHolder().setTypedValue(dayTaskList);

        getDateHolder().setTypedValue(dayTaskList.getDate());
        getDateNameHolder().setTypedValue(DATE_NAME_FORMATTER.format(dayTaskList.getDate()));
        getDayWorkedTimeHolder().setTypedValue(Task.convertWorkedTimeToString(taskList.getWorkedTime()));

        setExpanded(!taskList.getTasks().isEmpty());
    }

    protected NewTypedValueModel<DayTaskList> getCurrentDayHolder() {
        return currentDayHolder;
    }

    protected NewTypedValueModel<Boolean> getExpandedHolder() {
        return expandedHolder;
    }

    protected NewTypedValueModel<ImageResource> getExpandIconHolder() {
        return expandIconHolder;
    }

    protected NewTypedValueModel<String> getDayWorkedTimeHolder() {
        return dayWorkedTimeHolder;
    }

    protected NewTypedValueModel<String> getDateNameHolder() {
        return dateNameHolder;
    }

    protected NewTypedValueModel<Date> getDateHolder() {
        return dateHolder;
    }

    protected void toggleExpand() {
        setExpanded(!getExpandedHolder().booleanValue());
    }

    protected Date getDay() {
        return getDateHolder().getValue();
    }

    private void setExpanded(boolean expanded) {
        getExpandedHolder().setTypedValue(expanded);
        getExpandIconHolder().setTypedValue(expanded ? ImageResource.ICON_COLLAPSE : ImageResource.ICON_EXPAND);
    }
}
