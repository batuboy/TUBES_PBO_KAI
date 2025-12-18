package kai.controller;

import java.util.List;

import kai.models.reservation.Schedule;
import kai.models.train.Railcar;
import kai.repository.ScheduleRailcarsRepository;
import kai.repository.ScheduleRepository;

public class ScheduleController {
    private final ScheduleRepository scheduleRepository;
    private final ScheduleRailcarsRepository scheduleRailcarsRepository;

    public ScheduleController() {
        this.scheduleRepository = new ScheduleRepository();
        this.scheduleRailcarsRepository = new ScheduleRailcarsRepository();
    }

    public void addSchedule(Schedule schedule) {
        scheduleRepository.addSchedule(schedule);

        List<Railcar> railcars = schedule.getRailcars();
        if (railcars != null) {
            int order = 1;
            for (Railcar railcar : railcars) {
                scheduleRailcarsRepository.addRailcarToSchedule(
                    schedule.getScheduleId(), railcar.getRailcarId(), order++);
            }
        }
    }

    public List<Schedule> getAllSchedules() {
        return scheduleRepository.getAllSchedule();
    } 

    public Schedule getScheduleById(String scheduleId) {
        return scheduleRepository.getScheduleById(scheduleId);
    }
}