package kai.models.reservation;

import kai.models.reservation.num.TicketStatus;
import kai.models.train.Railcar;
import kai.models.user.User;

public class Ticket {
    private String ticketId;
    private User passenger;
    private Schedule schedule;
    private Railcar gerbong;
    private String seatNumber;
    private double price;
    private TicketStatus status;

    public Ticket(String ticketId, User passenger, Schedule schedule, Railcar gerbong, String seatNumber,
            double price, TicketStatus status) {
        this.ticketId = ticketId;
        this.passenger = passenger;
        this.schedule = schedule;
        this.gerbong = gerbong;
        this.seatNumber = seatNumber;
        this.price = price;
        this.status = status;
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

    public Railcar getGerbong() {
        return gerbong;
    }

    public void setGerbong(Railcar gerbong) {
        this.gerbong = gerbong;
    }

    public String getSeatNumber() {
        return seatNumber;
    }

    public void setSeatNumber(String seatNumber) {
        this.seatNumber = seatNumber;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }    

    public TicketStatus getStatus() {
        return status;
    }
    public void setStatus(TicketStatus status) {
        this.status = status;
    }
}
