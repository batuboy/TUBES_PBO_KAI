package kai.repository;

import kai.models.reservation.Schedule;
import kai.models.train.*;
import kai.database.DbConnect;


import java.sql.Connection;
import java.sql.PreparedStatement;  
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.ArrayList;
public class ScheduleRepository {

    private DbConnect dbConnect;

    public ScheduleRepository() {
        dbConnect = new DbConnect();
    }

    public void addSchedule(Schedule schedule) {
        String sqlSchedule = "INSERT INTO Schedule (schedule_id, locomotive_id, route_id, departureTime, arrivalTime) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = dbConnect.getConnection()) {
            PreparedStatement psSchedule = conn.prepareStatement(sqlSchedule);
            psSchedule.setString(1, schedule.getScheduleId());
            psSchedule.setString(2, schedule.getLocomotive().getLocomotiveId());
            psSchedule.setString(3, schedule.getRoute().getRouteId());
            psSchedule.setTimestamp(4, java.sql.Timestamp.valueOf(schedule.getDepartureTime()));
            psSchedule.setTimestamp(5, java.sql.Timestamp.valueOf(schedule.getArrivalTime()));
            psSchedule.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // public Schedule getScheduleById(String scheduleId){
    //     String sql = "SELECT * FROM station WHERE station_id = ?";

    //     try (Connection conn = dbConnect.getConnection()){
    //         PreparedStatement ps = conn.prepareStatement(sql);
    //         ps.setString(1, scheduleId);
    //         ResultSet rs = ps.executeQuery();      

    //         if(rs.next()){
    //             return new Schedule(
    //                 rs.getString("schedule_id"),
    //                 rs.getString("locomotive_id"),
    //                 rs.getString("route_id"),
    //                 rs.getTimestamp("departureTime"),
    //                 rs.getTimestamp("arrivalTime")
    //             );
    //         }     
    //     } catch (SQLException e) {
    //         e.printStackTrace();
    //     }

    //     return null;
    // }

    private List<String> convertStationToId(List<Station> stations) {
        List<String> ids = new ArrayList<>();
        for (Station s : stations) {
            ids.add(s.getStationId());
        }
        return ids;
    }
}