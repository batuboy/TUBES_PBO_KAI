package kai.controller;

import kai.models.user.Passenger;
import kai.repository.UserRepository;

public class RegisterController {

    private UserRepository userRepo;

    public RegisterController() {
        this.userRepo = new UserRepository();
    }

    public boolean register(Passenger passenger) {
        if (userRepo.isEmailRegistered(passenger.getEmail())) {
            return false;
        }
        userRepo.register(passenger);
        return true;
    }
}

