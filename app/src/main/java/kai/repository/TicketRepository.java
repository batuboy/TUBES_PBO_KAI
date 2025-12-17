package kai.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import kai.database.DbConnect;
import kai.models.reservation.Ticket;
import kai.models.reservation.num.TicketStatus;

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

        try (Connection conn = DbConnect.getConnection()) {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, ticketId);
            ResultSet rs = ps.executeQuery;
            if (rs.next()) {
                UserRepository userRepo = new UserRepository();
                ScheduleRepository scheduleRepo = new ScheduleRepository();
                RailCarRepository railcarRepo = new RailCarRepository();

                return new Ticket(
                        rs.getString("ticketId"),
                        userRepo.getUserById(rs.getString("userId")),
                        scheduleRepo.getScheduleById(rs.getString("scheduleId")),
                        railcarRepo.getRailcarById(rs.getString("railcarId")),
                        rs.getString("seatNumber"),
                        TicketStatus.valueOf(rs.getString("status")));
            }
        } catch (Exception e) {
            // TODO: handle exception
        }
    }

    public List<Ticket> getTicketsByUser(String userId) {
        List<Ticket> tickets = new ArrayList<>();

        String sql = "SELECT ticketId FROM Ticket WHERE userId = ? ORDER BY ticketId";

        try (Connection conn = DbConnect.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, userId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                tickets.add(getTicketById(rs.getString("ticketId")));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return tickets;
    }

    public List<String> getAvailableSeats(
            String scheduleId,
            String railcarId) {

        String sql = """
                    SELECT s.seat_number
                    FROM railcar_seats s
                    WHERE s.railcar_id = ?
                    AND s.seat_number NOT IN (
                        SELECT t.seatNumber
                        FROM Ticket t
                        WHERE t.scheduleId = ?
                          AND t.railcarId = ?
                          AND t.status = 'BOOKED'
                    )
                """;

        List<String> seats = new ArrayList<>();

        try (Connection conn = DbConnect.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, railcarId);
            ps.setString(2, scheduleId);
            ps.setString(3, railcarId);

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                seats.add(rs.getString("seat_number"));
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
