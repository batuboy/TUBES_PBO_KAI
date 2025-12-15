package kai.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;

import com.mysql.cj.protocol.ResultsetRow;

import kai.database.DbConnect;
import kai.models.reservation.Schedule;
import kai.models.train.Locomotive;
import kai.models.train.Railcar;
import kai.models.train.Route;
import kai.models.train.Station;
import kai.repository.ScheduleRailcarsRepository;

public class ScheduleRepository {
    private final ScheduleRailcarsRepository scheduleRailcarRepository;

    public ScheduleRepository() {
        this.scheduleRailcarRepository = new ScheduleRailcarsRepository();
    }

    public void addSchedule(Schedule schedule) {
        String sql = "INSERT INTO schedule (locomotiveId, routeId, price, departureTime, arrivalTime, serviceName, serviceNo) VALUES ( ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = DbConnect.getConnection()) {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, generateIdSchedule(conn));
            ps.setString(2, schedule.getLocomotive().getLocotomiveId());
            ps.setString(3, schedule.getRoute().getRouteId());
            ps.setDouble(4, schedule.getPrice());
            ps.setTimestamp(5, Timestamp.valueOf(schedule.getDepartureTime()));
            ps.setTimestamp(6, Timestamp.valueOf(schedule.getArrivalTime()));
            ps.setString(7, schedule.getServiceName());
            ps.setString(8, schedule.getServiceNo());
            ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Schedule getScheduleById(String scheduleId) {
        String sql = "SELECT * FROM schedule WHERE scheduleId = ?";

        try (Connection conn = DbConnect.getConnection()) {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, scheduleId);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                LocomotiveRepository locoRepo = new LocomotiveRepository();
                RouteRepository routeRepo = new RouteRepository();

                Locomotive locomotive = locoRepo.getLocomotiveById(rs.getString("locomotiveId"));
                Route route = routeRepo.getRouteById(rs.getString("routeId"));
                List<Railcar> railcars = scheduleRailcarRepository.getRailcarsBySchedule(rs.getString("scheduleId"));

                return new Schedule(
                        rs.getString("scheduleId"),
                        locomotive,
                        railcars,
                        route,
                        rs.getDouble("price"),
                        rs.getTimestamp("departureTime").toLocalDateTime(),
                        rs.getTimestamp("arrivalTime").toLocalDateTime(),
                        rs.getString("serviceName"),
                        rs.getString("serviceNo"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    public List<Schedule> getAllSchedule() {
        List<Schedule> schedules = new ArrayList<>();

        String sql = "SELECT * FROM Schedule";      

        try (Connection conn = DbConnect.getConnection()) {
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                LocomotiveRepository locoRepo = new LocomotiveRepository();
                RouteRepository routeRepo = new RouteRepository();
    
                Locomotive locomotive = locoRepo.getLocomotiveById(rs.getString("locomotiveId"));
                Route route = routeRepo.getRouteById(rs.getString("routeId"));
                List<Railcar> railcars = scheduleRailcarRepository.getRailcarsBySchedule(rs.getString("scheduleId"));
                
                schedules.add(new Schedule(
                    rs.getString("scheduleId"), 
                    locomotive,
                    railcars, 
                    route, 
                    rs.getDouble("price"),
                    rs.getTimestamp("departureTime").toLocalDateTime(), 
                    rs.getTimestamp("arrivalTime").toLocalDateTime(),
                    rs.getString("serviceName"),
                    rs.getString("serviceNo")));
                
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return schedules;
    }
    private String generateIdSchedule(Connection conn) throws Exception {
        String sql = "SELECT scheduleId FROM schedule ORDER BY scheduleId DESC LIMIT 1";
        PreparedStatement ps = conn.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();

        if (rs.next()) {
            int num = Integer.parseInt(rs.getString(1).substring(1));
            num++;
            return "S" + String.format("%02d", num);
        } else {
            return "S01";
        }
    }
}
