package kai.controller;

import kai.models.user.Passenger;
import kai.repository.UserRepository;

public class PassangerController {

    TicketController ticketController;

    public PassangerController(){
        ticketController = new TicketController();
    }
    
}