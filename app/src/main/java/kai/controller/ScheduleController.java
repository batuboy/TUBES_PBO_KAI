package kai.controller;

import kai.models.reservation.Schedule;
import kai.models.reservation.ScheduleRailcar;
import kai.models.train.Locomotive;
import kai.models.train.Route;
import kai.models.train.Railcar;
import kai.repository.*;

import javax.swing.table.DefaultTableModel;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class ScheduleController {

    private RouteRepository routeRepo;
    private LocomotiveRepository locoRepo;
    private RailCarRepository railcarRepo;
    private ScheduleRepository scheduleRepo;

    public ScheduleController() {
        this.routeRepo = new RouteRepository();
        this.locoRepo = new LocomotiveRepository();
        this.railcarRepo = new RailCarRepository();
        this.scheduleRepo = new ScheduleRepository();
    }

    // 1️⃣ Return all routes for dropdown
    public Route[] getAllRoutes() {
        return routeRepo.getAllRoutes().toArray(new Route[0]);
    }

    // 2️⃣ Return available locomotives
    public Locomotive[] getAvailableLocomotives() {
        return locoRepo.getLocomotivesAvailable().toArray(new Locomotive[0]);
    }

    // 3️⃣ Return railcar table model for selection with editable order column
    public DefaultTableModel getRailcarTableModel() {
        List<Railcar> railcars = railcarRepo.findAvailableAllRailCar();
        String[] columns = { "Select", "ID", "Name", "Status", "Order" };
        Object[][] data = new Object[railcars.size()][5];

        for (int i = 0; i < railcars.size(); i++) {
            Railcar r = railcars.get(i);
            data[i][0] = false; // checkbox
            data[i][1] = r.getRailcarId();
            data[i][2] = r.getName();
            data[i][3] = r.getStatus().name();
            data[i][4] = i + 1; // default order
        }

        return new DefaultTableModel(data, columns) {
            @Override
            public Class<?> getColumnClass(int columnIndex) {
                if (columnIndex == 0)
                    return Boolean.class;
                if (columnIndex == 4)
                    return Integer.class; // order column
                return String.class;
            }

            @Override
            public boolean isCellEditable(int row, int column) {
                return column == 0 || column == 4; // checkbox & order editable
            }
        };
    }

    // 4️⃣ Convert form input → Schedule object
    public Schedule createScheduleFromForm(
            Route route,
            Locomotive loco,
            LocalDateTime departure,
            LocalDateTime arrival,
            String serviceName,
            String serviceNo,
            double price) {
        return new Schedule(null, loco, route, departure,arrival, serviceName, serviceNo, price);
    }

    public List<ScheduleRailcar> createScheduleRailcars(String scheduleId, List<RailcarSelection> selectedRailcars) {
        List<ScheduleRailcar> railcarList = new ArrayList<>();
        for (RailcarSelection rs : selectedRailcars) {
            railcarList.add(new ScheduleRailcar(scheduleId, rs.getRailcarId(), rs.getOrder()));
        }
        return railcarList;
    }

    
    // 6️⃣ Add schedule + railcars to DB
    public boolean addSchedule(Schedule schedule, List<ScheduleRailcar> railcars) {
        return scheduleRepo.addSchedule(schedule, railcars);
    }

    // Helper class to hold selected railcar info from table
    public static class RailcarSelection {
        private String railcarId;
        private int order;

        public RailcarSelection(String railcarId, int order) {
            this.railcarId = railcarId;
            this.order = order;
        }

        public String getRailcarId() {
            return railcarId;
        }

        public int getOrder() {
            return order;
        }
    }

    public List<Schedule> getAllSchedules() {
        return scheduleRepo.getAllSchedules();
    }

    public List<ScheduleRailcar> getRailcarsForSchedule(String scheduleId) {
        return scheduleRepo.getRailcarsForSchedule(scheduleId);
    }

    // 5️⃣ Update schedule
    public boolean updateSchedule(Schedule schedule, List<ScheduleRailcar> railcars) {
        if (schedule == null || railcars == null)
            return false;
        return scheduleRepo.updateSchedule(schedule, railcars);
    }
}
