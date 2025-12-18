package kai.models.reservation;

import kai.models.reservation.num.TicketStatus;
import kai.models.train.Railcar;
import kai.models.user.User;

public class Ticket {
    private String ticketId;
    private User passenger;
    private Schedule schedule;
    private TicketStatus status;
    private String seatNumber;

    public Ticket(String ticketId, User passenger, Schedule schedule, String seatNumber,
            TicketStatus status) {
        this.ticketId = ticketId;
        this.passenger = passenger;
        this.schedule = schedule;
        this.seatNumber = seatNumber;
        this.status = status;
    }

    public Ticket(String string, User passengerById, TicketStatus valueOf, Schedule scheduleById, String string2) {
        //TODO Auto-generated constructor stub
    }

    public String getTicketId() {
        return ticketId;
    }

    public void setTicketId(String ticketId) {
        this.ticketId = ticketId;
    }

    public User getPassenger() {
        return passenger;
    }

    public void setPassenger(User passenger) {
        this.passenger = passenger;
    }

    public Schedule getSchedule() {
        return schedule;
    }

    public void setSchedule(Schedule schedule) {
        this.schedule = schedule;
    }

    public String getSeatNumber() {
        return seatNumber;
    }

    public void setSeatNumber(String seatNumber) {
        this.seatNumber = seatNumber;
    }


    public TicketStatus getStatus() {
        return status;
    }
    public void setStatus(TicketStatus status) {
        this.status = status;
    }
}