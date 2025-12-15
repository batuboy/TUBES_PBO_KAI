package kai.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.mysql.cj.xdevapi.Statement;

import kai.database.DbConnect;
import kai.models.user.Passenger;
import kai.models.user.User;

public class UserRepository {

    public boolean register(User user) {
        String sql = "INSERT INTO userdata (nik, namaLengkap, nomorTelepon, email, password) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = DbConnect.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, user.getNik());
            ps.setString(2, user.getNamaLengkap());
            ps.setString(3, user.getNomorTelepon());
            ps.setString(4, user.getEmail());
            ps.setString(5, user.getPassword());

            ps.executeUpdate();
            return true;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public User login(String email, String password) {
        String sql = "SELECT * FROM user WHERE email = ? AND password = ?";

        try (Connection conn = DbConnect.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, email);
            ps.setString(2, password);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return new Passenger(
                        rs.getString("nik"),
                        rs.getString("namaLengkap"),
                        rs.getString("nomorTelepon"),
                        rs.getString("email"),
                        rs.getString("password"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean isEmailRegistered(String email) {
        String sql = "SELECT * FROM user WHERE email = ?";

        try (Connection conn = DbConnect.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, email);

            ResultSet rs = ps.executeQuery();
            return rs.next(); // true if email exists
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }


    public User getPassangerById(String id) {
        String sql = "SELECT * FROM user WHERE userId = ?";

        try (Connection conn = DbConnect.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, id);

            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return new Passenger(
                        rs.getString("nik"),
                        rs.getString("namaLengkap"),
                        rs.getString("nomorTelepon"),
                        rs.getString("email"),
                        rs.getString("password"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // public boolean updateUser(User user){

    // }

}
