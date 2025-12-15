package kai.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import kai.database.DbConnect;
import kai.models.user.Passenger;
import kai.models.user.User;

public class UserRepository {
    
    public boolean register(User user) {
        String sql = "INSERT INTO user (nik, namaLengkap, nomorTelepon, email,  password) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = DbConnect.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, user.getNik());
            ps.setString(2, user.getNamaLengkap());
            ps.setString(3, user.getNomorTelepon());
            ps.setString(4, user.getEmail());
            ps.setString(5, user.getPassword());

            ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return true;
    }

    public User login(String email, String password) {
        String sql = "SELECT * FROM users WHERE email = ? AND password = ?" ;

        try {
            PreparedStatement ps = DbConnect.getConnection().prepareStatement(sql);
            ps.setString(1, email);
            ps.setString(2, password);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return new Passenger(
                    rs.getString("nik"),
                    rs.getString("namaLengkap"),
                    rs.getString("email"),
                    rs.getString("telepon"),
                    rs.getString("password")
                );
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null; // login failed
    }

    public boolean isEmailRegistered(String email) {
        String sql = "SELECT * FROM user WHERE email = ?";
        try (Connection conn = DbConnect.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, email);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next(); // true if email exists
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    

}