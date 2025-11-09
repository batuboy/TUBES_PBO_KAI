package kai.models.reservation;

import kai.models.train.Railcar;
import kai.models.user.Passenger;

public class Ticket {
    private String ticketId;
    private Passenger passenger;
    private Schedule schedule;
    private Railcar gerbong;
    private String seatNumber;
    private double price;

    public Ticket(String ticketId, Passenger passenger, Schedule schedule, Railcar gerbong, String seatNumber,
            double price) {
        this.ticketId = ticketId;
        this.passenger = passenger;
        this.schedule = schedule;
        this.gerbong = gerbong;
        this.seatNumber = seatNumber;
        this.price = price;
    }

    public String getTicketId() {
        return ticketId;
    }

    public void setTicketId(String ticketId) {
        this.ticketId = ticketId;
    }

    public Passenger getPassenger() {
        return passenger;
    }

    public void setPassenger(Passenger passenger) {
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

    
    
}
