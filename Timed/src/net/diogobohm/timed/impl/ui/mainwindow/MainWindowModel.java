/**
 * TODO: define a license.
 */
package net.diogobohm.timed.impl.ui.mainwindow;

import com.google.common.base.Optional;
import com.google.common.collect.Sets;
import java.util.List;
import net.diogobohm.timed.api.ui.mvc.MVCModel;
import net.diogobohm.timed.api.ui.mvc.model.DashboardTaskValueHolder;
import net.diogobohm.timed.api.ui.mvc.model.LabeledBeanHolder;
import net.diogobohm.timed.api.ui.mvc.model.TagSetHolder;
import net.diogobohm.timed.api.ui.mvc.model.ToStringValueHolder;
import net.diogobohm.timed.api.ui.mvc.model.TypedValueHolder;
import net.diogobohm.timed.api.ui.mvc.model.TypedValueModel;
import net.diogobohm.timed.api.domain.Dashboard;
import net.diogobohm.timed.api.domain.Tag;
import net.diogobohm.timed.api.domain.Task;

/**
 *
 * @author diogo.bohm
 */
public class MainWindowModel implements MVCModel<Dashboard> {

    private final TagSetHolder currentTaskTagsHolder;
    private final DashboardTaskValueHolder currentTaskHolder;
    private final TypedValueHolder<List<Task>> taskListHolder;

    public MainWindowModel() {
        currentTaskTagsHolder = new TagSetHolder();
        currentTaskHolder = new DashboardTaskValueHolder();
        taskListHolder = new TypedValueHolder<>();
    }

    public DashboardTaskValueHolder getCurrentTaskHolder() {
        return currentTaskHolder;
    }

    public TagSetHolder getCurrentTaskTagsHolder() {
        return currentTaskTagsHolder;
    }

    public TypedValueHolder getTaskListHolder() {
        return taskListHolder;
    }

    @Override
    public Dashboard getDomainBean() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void setDomainBean(Dashboard domainBean) {
        Optional<Task> currentTask = domainBean.getCurrentTask();

        getCurrentTaskHolder().setValue(domainBean.getCurrentTask());

        if (currentTask.isPresent()) {
            getCurrentTaskTagsHolder().setValue(domainBean.getCurrentTask().get().getTags());
        } else {
            getCurrentTaskTagsHolder().setValue(Sets.<Tag>newHashSet());
        }
        getTaskListHolder().setValue(domainBean.getTasks());
    }

}
