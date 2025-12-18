package kai.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.mysql.cj.xdevapi.Statement;

import kai.database.DbConnect;
import kai.models.user.Admin;
import kai.models.user.Passenger;
import kai.models.user.User;

public class UserRepository {

    public boolean register(User user) {
        String getLastIdSql = "SELECT userId FROM userdata ORDER BY userId DESC LIMIT 1";
        String insertSql = "INSERT INTO userdata (userId, nik, fname, phone, email, password) VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection conn = DbConnect.getConnection();
                PreparedStatement psGet = conn.prepareStatement(getLastIdSql);
                PreparedStatement psInsert = conn.prepareStatement(insertSql)) {

            ResultSet rs = psGet.executeQuery();
            String nextUserId;
            if (rs.next()) {
                String lastUserId = rs.getString("userId");
                String numericPart = lastUserId.replaceAll("\\D", "");
                int num = Integer.parseInt(numericPart);
                num++;
                // Keep the original prefix (letters)
                String prefix = lastUserId.replaceAll("\\d", ""); // "SR"
                nextUserId = prefix + String.format("%03d", num); // "SR004"
            } else {
                nextUserId = "UR001"; // first user
            }

            // 2️⃣ Set parameters for insert
            psInsert.setString(1, nextUserId);
            psInsert.setString(2, user.getNik());
            psInsert.setString(3, user.getNamaLengkap());
            psInsert.setString(4, user.getNomorTelepon());
            psInsert.setString(5, user.getEmail());
            psInsert.setString(6, user.getPassword());

            psInsert.executeUpdate();
            return true;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public User login(String email, String password) {
        String sql = "SELECT * FROM userdata WHERE email = ? AND password = ?";

        try (Connection conn = DbConnect.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, email);
            ps.setString(2, password);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                String userId = rs.getString("userId");
                String nik = rs.getString("nik");
                String fname = rs.getString("fname");
                String phone = rs.getString("phone");
                String pass = rs.getString("password");

                // check email domain
                if (email.endsWith("@kai.id")) {
                    // Admin object (salary can be default 0 or fetched from admin table)
                    return new Admin(userId, nik, fname, phone, email, pass);
                } else {
                    // Passenger object
                    return new Passenger(userId, nik, fname, phone, email, pass);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null; // login failed
    }

    public boolean isEmailRegistered(String email) {
        String sql = "SELECT * FROM userdata WHERE email = ?";

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

    public User getPassengerById(String id) {
        String sql = "SELECT * FROM userdata WHERE userId = ?";

        try (Connection conn = DbConnect.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, id);

            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return new Passenger(
                        rs.getString("userId"),
                        rs.getString("nik"),
                        rs.getString("fname"),
                        rs.getString("phone"),
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
