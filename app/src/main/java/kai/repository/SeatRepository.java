package kai.repository;

import kai.database.*;
import kai.models.train.*;
import java.sql.Connection;
import java.util.List;
import kai.database.DbConnect;

public class SeatRepository {
    private Connection conn;

    public SeatRepository() {
        this.conn = DbConnect.getConnection();
    }

    // public List<Seat> getAllSeats() {
    //     String sql = "SELECT * FROM seats";

    //     try {
    //         var stmt = conn.prepareStatement(sql);
    //         var rs = stmt.executeQuery();
    //         var seats = new java.util.ArrayList<Seat>();

    //         while (rs.next()) {
    //             Seat seat = new Seat(
    //                 rs.getInt("id"),
    //                 rs.getString("coach"),
    //                 rs.getString("seat_number"),
    //                 rs.getBoolean("is_available")
    //             );
    //             seats.add(seat);
    //         }
    //         return seats;
    //     } catch (Exception e) {
    //         e.printStackTrace();
    //         return java.util.Collections.emptyList();
    //     }
    // }
}