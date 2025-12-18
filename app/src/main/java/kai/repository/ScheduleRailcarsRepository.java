package kai.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import kai.database.DbConnect;
import kai.models.train.Carriage;
import kai.models.train.Railcar;
import kai.models.train.Wagon;
import kai.models.train.num.RailcarType;
import kai.models.train.num.Status;
import kai.models.train.num.TrainClass;

public class ScheduleRailcarsRepository {

    public void addRailcarToSchedule(
            String scheduleId,
            String railcarId,
            int order) {
        String sql = "INSERT INTO schedule_railcars" +
                    "(schedule_id, railcar_id, railcar_order)" +
                    "VALUES (?, ?, ?)";

        try (Connection conn = DbConnect.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, scheduleId);
            ps.setString(2, railcarId);
            ps.setInt(3, order);
            ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<Railcar> getRailcarsBySchedule(String scheduleId) {
        String sql = " SELECT r.* FROM schedule_railcars sr JOIN railcar r ON sr.railcar_id = r.railcar_id WHERE sr.schedule_id = ? ORDER BY sr.railcar_order";

        List<Railcar> railcars = new ArrayList<>();

        try (Connection conn = DbConnect.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, scheduleId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                railcars.add(mapRailcar(rs));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return railcars;
    }

    private Railcar mapRailcar(ResultSet rs) throws SQLException {
        String type = rs.getString("type");

        if ("CARRIAGE".equalsIgnoreCase(type)) {
            return new Carriage(
                    rs.getString("railcar_id"),
                    TrainClass.valueOf(rs.getString("train_class")),
                    rs.getString("name"),
                    Status.valueOf(rs.getString("status"))
                    // rs.getInt("capacity")
            );
        }

        if ("WAGON".equalsIgnoreCase(type)) {
            return new Wagon(
                    rs.getString("railcar_id"),
                    rs.getString("name"),
                    Status.valueOf(rs.getString("status")),
                    rs.getDouble("cargoCapacity"));
        }

        throw new IllegalArgumentException("Unknown railcar type");
    }
}