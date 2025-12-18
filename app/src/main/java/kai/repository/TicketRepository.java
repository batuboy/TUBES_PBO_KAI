package kai.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import kai.database.DbConnect;
import kai.models.reservation.Ticket;
import kai.models.reservation.num.TicketStatus;

public class TicketRepository {

    public void addTicket(Ticket ticket) {
        String sql = "INSERT INTO Ticket (ticketId, userId, status, scheduleId, seatId) Values (?,?,?,?,?)";

        try (Connection conn = DbConnect.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, generateIdTicket(conn));
            ps.setString(2, ticket.getPassenger().getUserId());
            ps.setString(3, ticket.getStatus().name());
            ps.setString(4, ticket.getSchedule().getScheduleId());
            ps.setString(5, ticket.getSeatNumber());

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
            int num = Integer.parseInt(rs.getString("ticketId").substring(1));
            num++;
            return "T" + String.format("%02d", num);
        } else {
            return "T01";
        }
    }

    public Ticket getTicketById(String ticketId) {
        String sql = "SELECT * FROM Ticket WHERE ticketId = ?";

        try (Connection conn = DbConnect.getConnection()) {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, ticketId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                UserRepository userRepo = new UserRepository();
                ScheduleRepository scheduleRepo = new ScheduleRepository();

                return new Ticket(
                        rs.getString("ticketId"),
                        userRepo.getPassengerById(rs.getString("userId")),
                        scheduleRepo.getScheduleById(rs.getString("scheduleId")),
                        rs.getString("seatId"),
                        TicketStatus.valueOf(rs.getString("status")));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<Ticket> getTicketsByUser(String userId) {
        List<Ticket> tickets = new ArrayList<>();

        String sql = "SELECT ticketId, userId, status, scheduleId, seatId FROM Ticket WHERE userId = ? ORDER BY ticketId";

        try (Connection conn = DbConnect.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, userId);
            ResultSet rs = ps.executeQuery();

            UserRepository userRepo = new UserRepository();
            ScheduleRepository scheduleRepo = new ScheduleRepository();

            while (rs.next()) {
                tickets.add(new Ticket(
                        rs.getString("ticketId"),
                        userRepo.getPassengerById(rs.getString("userId")),
                        scheduleRepo.getScheduleById(rs.getString("scheduleId")),
                        rs.getString("seatId"),
                        TicketStatus.valueOf(rs.getString("status"))));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return tickets;
    }

    public List<Map<String, String>> getAvailableSeats(String scheduleId, String railcarId) {
        String sql = """
                    SELECT s.seatId, s.seatNo
                    FROM seat s
                    WHERE s.railcarId = ?
                    AND s.seatId NOT IN (
                        SELECT t.seatId
                        FROM Ticket t
                        WHERE t.scheduleId = ?
                          AND t.status = 'BOOKED'
                    )
                """;

        List<Map<String, String>> seats = new ArrayList<>();

        try (Connection conn = DbConnect.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, railcarId);
            ps.setString(2, scheduleId);

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Map<String, String> seat = new HashMap<>();
                seat.put("seatId", rs.getString("seatId"));
                seat.put("seatNo", rs.getString("seatNo"));
                seats.add(seat);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return seats;
    }

    public void updateTicketStatus(String ticketId, TicketStatus status) {
        String sql = "UPDATE Ticket SET status = ? WHERE ticketId = ?";

        try (Connection conn = DbConnect.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, status.name());
            ps.setString(2, ticketId);
            ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}