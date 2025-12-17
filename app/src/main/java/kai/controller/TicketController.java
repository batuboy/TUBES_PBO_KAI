package kai.controller;

import java.util.List;

import kai.models.reservation.Ticket;
import kai.models.reservation.num.TicketStatus;
import kai.repository.TicketRepository;

public class TicketController {

    private final TicketRepository ticketRepo;

    public TicketController() {
        this.ticketRepo = new TicketRepository();
    }

    public void bookingTicket(Ticket ticket) {
        ticketRepo.addTicket(ticket);
    }

    public Ticket printTicket(String ticketId) {
        return ticketRepo.getTicketById(ticketId);
    }

    public List<Ticket> viewReservationHistory(String userId) {
        return ticketRepo.getTicketsByUser(userId);
    }

    public void cancelReservation(String ticketId) {
        ticketRepo.updateTicketStatus(ticketId, TicketStatus.CANCELLED);
    }

    public List<String> viewAvailableSeats(String scheduleId, String railcarId) {
        return ticketRepo.getAvailableSeats(scheduleId, railcarId);
    }
}
