package kai.controller;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import kai.models.reservation.Schedule;
import kai.models.reservation.Ticket;
import kai.models.reservation.num.TicketStatus;
import kai.models.user.User;
import kai.repository.TicketRepository;

public class TicketController {

    private final TicketRepository ticketRepo;

    public TicketController() {
        this.ticketRepo = new TicketRepository();
    }

    public void bookingTicket(User user, Schedule schedule, String seatNumber) {
        ticketRepo.addTicket(new Ticket("", user, schedule, seatNumber, TicketStatus.BOOKED));
    }

    public Ticket printTicket(String ticketId) {
        return ticketRepo.getTicketById(ticketId);
    }

    public List<Ticket> viewReservationHistory(String userId) {
        return ticketRepo.getTicketsByUser(userId);
    }

    public boolean cancelReservation(String ticketId) {
        Ticket t = ticketRepo.getTicketById(ticketId);
        if (LocalDateTime.now().isAfter(t.getSchedule().getDepartureTime().minusMinutes(30))) {
            return false; 
        }
        ticketRepo.updateTicketStatus(ticketId, TicketStatus.CANCELLED);
        return true;
    }

    public List<Map<String, String>> viewAvailableSeats(String scheduleId, String railcarId) {
        return ticketRepo.getAvailableSeats(scheduleId, railcarId);
    }
}