/**
 * TODO: define a license.
 */
package net.diogobohm.timed.impl.ui.daytasklist;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.Date;
import net.diogobohm.timed.api.ui.mvc.MVCModel;
import net.diogobohm.timed.api.ui.domain.DayTaskList;
import net.diogobohm.timed.api.ui.mvc.model.NewTypedValueModel;

/**
 *
 * @author diogo.bohm
 */
public class DayTaskListModel implements MVCModel<DayTaskList> {

    private final NewTypedValueModel<Boolean> expandHolder;
    private final NewTypedValueModel<String> expandLabelHolder;
    private final NewTypedValueModel<String> dayWorkedTimeHolder;
    private final NewTypedValueModel<String> dateNameHolder;
    private final NewTypedValueModel<Date> dateHolder;
    
    public DayTaskListModel() {
        expandHolder = new NewTypedValueModel(Boolean.TRUE);
        expandLabelHolder = new NewTypedValueModel("\\/");
        dayWorkedTimeHolder = new NewTypedValueModel();
        dateNameHolder = new NewTypedValueModel();
        dateHolder = new NewTypedValueModel();

        expandHolder.addPropertyChangeListener(createExpandChangeListener());
    }

    @Override
    public DayTaskList getDomainBean() {
        return null;
    }

    @Override
    public void setDomainBean(DayTaskList dayTaskList) {
        getDateHolder().setTypedValue(dayTaskList.getDate());
        getDateNameHolder().setTypedValue(dayTaskList.getDate().toString());
        getDayWorkedTimeHolder().setTypedValue(String.valueOf(dayTaskList.getTasks().size()));
        getExpandHolder().setTypedValue(!dayTaskList.getTasks().isEmpty());
    }

    protected NewTypedValueModel<Boolean> getExpandHolder() {
        return expandHolder;
    }

    protected NewTypedValueModel<String> getExpandLabelHolder() {
        return expandLabelHolder;
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
        getExpandHolder().setTypedValue(!getExpandHolder().booleanValue());
    }

    protected Date getDay() {
        return getDateHolder().getValue();
    }

    private PropertyChangeListener createExpandChangeListener() {
        return new PropertyChangeListener() {

            @Override
            public void propertyChange(PropertyChangeEvent evt) {
                getExpandLabelHolder().setTypedValue(getExpandHolder().booleanValue() ? "\\/" : ">");
            }
        };
    }

}
