package kai.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import kai.database.DbConnect;
import kai.models.reservation.Ticket;

public class TicketRepository {

    public void addTicket(Ticket ticket) {
        String sql = "INSERT INTO Ticket (ticketId, userId, scheduleId, railcarId, seatNumber, status) Values (?,?,?,?,?,?)";

        try (Connection conn = DbConnect.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, generateIdTicket(conn));
            ps.setString(2, ticket.getPassenger().getUserId());
            ps.setString(3, ticket.getSchedule().getScheduleId());
            ps.setString(4, ticket.getGerbong().getRailcarId());
            ps.setString(5, ticket.getSeatNumber());
            ps.setString(6, ticket.getStatus().name());

            ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private String generateIdTicket(Connection conn) throws Exception {
        String sql = "SELECT ticketId FROM Ticket ORDER BY ticketId DESC LIMIT 1";
        PreparedStatement ps = conn.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();

        if (rs.next()) {
            int num = Integer.parseInt(rs.getString(1).substring(1));
            num++;
            return "T" + String.format("%02d", num);
        } else {
            return "T01";
        }
    }

    
    public Ticket getTicketById(String ticketId) {
        String sql = "SELECT * FROM Ticket WHERE ticketId = ?";

        try (Connection conn = DbConnect.getConnection()){
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, ticketId);
            ResultSet rs = ps.executeQuery;
            if () {
                
            }
        } catch (Exception e) {
            // TODO: handle exception
        }
    }
}
