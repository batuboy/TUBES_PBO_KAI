package kai.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import kai.database.DbConnect;

public class ReportRepository {

    public int getTotalTicketsSold() {
        String sql = "SELECT COUNT(*) AS total FROM ticket";
        try (Connection conn = DbConnect.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql)) {
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt("total");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public double getTotalRevenue() {
        String sql = "SELECT SUM(price) AS revenue " +
                     "FROM ticket t " +
                     "JOIN schedule s ON t.scheduleId = s.scheduleId";
        try (
            Connection conn = DbConnect.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql)) {
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt("revenue");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }
    public int getTotalSchedules() {
        String sql = "SELECT COUNT(*) AS total FROM schedule";
        try (
            Connection conn = DbConnect.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql)) {
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt("total");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }
}

