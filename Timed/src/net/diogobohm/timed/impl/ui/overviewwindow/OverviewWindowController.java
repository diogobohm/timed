/**
 * TODO: define a license.
 */
package net.diogobohm.timed.impl.ui.overviewwindow;

import com.google.common.base.Optional;
import com.google.common.collect.Lists;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import net.diogobohm.timed.api.db.access.Database;
import net.diogobohm.timed.api.db.access.DatabaseConnection;
import net.diogobohm.timed.api.db.exception.DatabaseAccessException;
import net.diogobohm.timed.api.db.serializer.DBPersistenceOrchestrator;
import net.diogobohm.timed.api.ui.mvc.MVCController;
import net.diogobohm.timed.api.domain.Task;
import net.diogobohm.timed.api.ui.domain.Overview;
import net.diogobohm.timed.api.ui.domain.builder.OverviewBuilder;
import net.diogobohm.timed.api.ui.mvc.controller.DomainEditor;
import net.diogobohm.timed.impl.ui.factory.DayTaskListControllerFactory;
import org.apache.commons.lang3.time.DateUtils;
import org.apache.commons.lang3.time.FastDateFormat;

/**
 *
 * @author diogo.bohm
 */
public class OverviewWindowController extends MVCController<OverviewWindowModel, OverviewWindowView> implements DomainEditor<Overview> {

    private static final FastDateFormat DAY_FORMATTER = FastDateFormat.getInstance("yyyy-MM-dd");

    private final OverviewBuilder overviewBuilder;
    private final DayTaskListControllerFactory dayTaskListControllerFactory;

    private OverviewWindowModel model;
    private OverviewWindowView view;

    public OverviewWindowController() {
        this.overviewBuilder = new OverviewBuilder();
        this.dayTaskListControllerFactory = new DayTaskListControllerFactory();
    }

    public void showDefaultOverview() {
        Overview overview = fetchDefaultOverview();

        getModel().setDomainBean(getView(), overview);
        getView().setVisible(true);
    }

    @Override
    protected OverviewWindowModel getModel() {
        if (model == null) {
            model = new OverviewWindowModel(dayTaskListControllerFactory);
        }

        return model;
    }

    @Override
    protected OverviewWindowView getView() {
        if (view == null) {
            view = new OverviewWindowView(getModel(), createFilterAction());
        }

        return view;
    }

    private ActionListener createFilterAction() {
        return new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                Date startDate = getModel().getStartDate();
                Date endDate = getModel().getEndDate();

                Overview overview = fetchOverviewFor(startDate, endDate);
                getModel().setDomainBean(getView(), overview);
                getModel().setDates(startDate, endDate);
            }
        };
    }

    private Overview fetchDefaultOverview() {
        Date today = new Date();
        Date lastSunday = getLastSunday(today);
        Overview overview = fetchOverviewFor(lastSunday, today);

        getModel().setDates(lastSunday, today);

        return overview;
    }

    private Overview fetchOverviewFor(Date startDate, Date endDate) {
        List<Task> tasks = fetchTasksBetween(startDate, endDate);

        return overviewBuilder.build(startDate, endDate, tasks);
    }

    @Override
    public void updateDomain(Optional<Overview> oldValue, Optional<Overview> newValue) {
        
    }

    private Date getLastSunday(Date start) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(start);

        while (calendar.get(Calendar.DAY_OF_WEEK) != Calendar.SUNDAY) {
            calendar.setTime(DateUtils.addDays(calendar.getTime(), -1));
        }

        return calendar.getTime();
    }

    private List<Task> fetchTasksBetween(Date startDate, Date endDate) {
        String startDay = DAY_FORMATTER.format(startDate);
        String endDay = DAY_FORMATTER.format(endDate);
        List<Task> tasks = Lists.newArrayList();

        Database db = DatabaseConnection.getConnection();
        DBPersistenceOrchestrator orchestrator = DBPersistenceOrchestrator.getInstance();

        try {
            tasks.addAll(orchestrator.loadTasks(db, startDay + " 00:00", endDay + " 23:59"));
        } catch (DatabaseAccessException exception) {
            exception.printStackTrace();
        }

        return tasks;
    }
}
