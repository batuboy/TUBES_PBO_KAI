package kai.repository;

import kai.models.reservation.Schedule;
import kai.models.reservation.ScheduleRailcar;
import kai.models.train.*;
import kai.database.DbConnect;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.ArrayList;
import java.time.LocalDateTime;

public class ScheduleRepository {

    private DbConnect dbConnect;

    public ScheduleRepository() {
        dbConnect = new DbConnect();
    }

    public boolean addSchedule(Schedule schedule, List<ScheduleRailcar> railcars) {
        try {
            Connection conn = DbConnect.getConnection();

            // generate ID
            String scheduleId = generateNextScheduleId(conn);
            schedule.setScheduleId(scheduleId); // set generated ID

            // 1. Insert schedule
            String sqlSchedule = "INSERT INTO schedule " +
                    "(scheduleId, routeId, departureTime, arrivalTime, serviceName, serviceNo, price, locomotiveId) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement psSchedule = conn.prepareStatement(sqlSchedule);
            psSchedule.setString(1, schedule.getScheduleId());
            psSchedule.setString(2, schedule.getRoute().getRouteId());
            psSchedule.setTimestamp(3, java.sql.Timestamp.valueOf(schedule.getDepartureTime()));
            psSchedule.setTimestamp(4, java.sql.Timestamp.valueOf(schedule.getArrivalTime()));
            psSchedule.setString(5, schedule.getServiceName());
            psSchedule.setString(6, schedule.getServiceNo());
            psSchedule.setDouble(7, schedule.getPrice());
            psSchedule.setString(8, schedule.getLocomotive().getLocomotiveId());
            psSchedule.executeUpdate();

            // 2. Insert schedule_railcars
            String sqlRailcar = "INSERT INTO schedule_railcars (scheduleId, railcarId, `order`) VALUES (?, ?, ?)";
            PreparedStatement psRailcar = conn.prepareStatement(sqlRailcar);

            for (ScheduleRailcar sr : railcars) {
                psRailcar.setString(1, schedule.getScheduleId());
                psRailcar.setString(2, sr.getRailcarId());
                psRailcar.setInt(3, sr.getOrder());
                psRailcar.executeUpdate();
            }

            conn.close();
            return true;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    private String generateNextScheduleId(Connection conn) throws SQLException {
        String sql = "SELECT scheduleId FROM schedule ORDER BY CAST(SUBSTRING(scheduleId, 2) AS UNSIGNED) DESC LIMIT 1";
        PreparedStatement ps = conn.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();

        if (rs.next()) {
            String lastId = rs.getString("scheduleId").trim();
            String numericPart = lastId.substring(1).replaceAll("\\D", "");
            int num = 0;
            try {
                num = Integer.parseInt(numericPart);
            } catch (NumberFormatException e) {
                num = 0;
            }
            num++;
            return "S" + String.format("%03d", num);
        } else {
            return "S001";
        }
    }

    public List<Schedule> getAllSchedules() {
        List<Schedule> schedules = new ArrayList<>();

        String sql = "SELECT s.scheduleId, s.serviceNo, s.serviceName, s.price, " +
                "s.departureTime, s.arrivalTime, " +
                "r.routeId, o.stationId AS originId, o.name AS originName, " +
                "d.stationId AS destinationId, d.name AS destinationName, " +
                "l.locomotiveId, l.name AS locomotiveName " +
                "FROM schedule s " +
                "JOIN route r ON s.routeId = r.routeId " +
                "JOIN station o ON r.originId = o.stationId " +
                "JOIN station d ON r.destinationId = d.stationId " +
                "JOIN locomotive l ON s.locomotiveId = l.locomotiveId " +
                "ORDER BY s.departureTime ASC";

        try (Connection conn = DbConnect.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql);
                ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                // Route
                Station origin = new Station(rs.getString("originId"), rs.getString("originName"));
                Station destination = new Station(rs.getString("destinationId"), rs.getString("destinationName"));
                Route route = new Route(rs.getString("routeId"), origin, destination);

                // Locomotive
                Locomotive loco = new Locomotive(rs.getString("locomotiveId"), rs.getString("locomotiveName"), null);

                // Schedule
                Schedule schedule = new Schedule(
                        rs.getString("scheduleId"),
                        loco,
                        route,
                        rs.getTimestamp("departureTime").toLocalDateTime(),
                        rs.getTimestamp("arrivalTime").toLocalDateTime(),
                        rs.getString("serviceName"),
                        rs.getString("serviceNo"),
                        rs.getDouble("price"));

                schedules.add(schedule);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return schedules;
    }

    public List<ScheduleRailcar> getRailcarsForSchedule(String scheduleId) {
        List<ScheduleRailcar> list = new ArrayList<>();
        String sql = "SELECT * FROM schedule_railcars WHERE scheduleId = ? ORDER BY `order` ASC";
        try (Connection conn = DbConnect.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, scheduleId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                ScheduleRailcar sr = new ScheduleRailcar(
                        rs.getString("scheduleId"),
                        rs.getString("railcarId"),
                        rs.getInt("order"));
                list.add(sr);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public boolean updateSchedule(Schedule schedule, List<ScheduleRailcar> railcars) {
        try (Connection conn = DbConnect.getConnection()) {

            // 1️⃣ Update schedule table
            String sql = "UPDATE schedule SET routeId = ?, departureTime = ?, arrivalTime = ?, " +
                    "serviceName = ?, serviceNo = ?, price = ?, locomotiveId = ? " +
                    "WHERE scheduleId = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, schedule.getRoute().getRouteId());
            ps.setTimestamp(2, java.sql.Timestamp.valueOf(schedule.getDepartureTime()));
            ps.setTimestamp(3, java.sql.Timestamp.valueOf(schedule.getArrivalTime()));
            ps.setString(4, schedule.getServiceName());
            ps.setString(5, schedule.getServiceNo());
            ps.setDouble(6, schedule.getPrice());
            ps.setString(7, schedule.getLocomotive().getLocomotiveId());
            ps.setString(8, schedule.getScheduleId());
            ps.executeUpdate();

            // 2️⃣ Update schedule_railcars
            // Delete old railcars
            String deleteSql = "DELETE FROM schedule_railcars WHERE scheduleId = ?";
            PreparedStatement psDelete = conn.prepareStatement(deleteSql);
            psDelete.setString(1, schedule.getScheduleId());
            psDelete.executeUpdate();

            // Insert new railcars
            String insertSql = "INSERT INTO schedule_railcars (scheduleId, railcarId, `order`) VALUES (?, ?, ?)";
            PreparedStatement psInsert = conn.prepareStatement(insertSql);
            for (ScheduleRailcar sr : railcars) {
                psInsert.setString(1, schedule.getScheduleId());
                psInsert.setString(2, sr.getRailcarId());
                psInsert.setInt(3, sr.getOrder());
                psInsert.executeUpdate();
            }

            return true;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}