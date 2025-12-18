package kai.controller;

import java.util.Random;

import kai.models.user.Passenger;
import kai.models.user.User;
import kai.models.user.num.Position;
import kai.repository.UserRepository;

public class UserController {

    private UserRepository userRepository;

    public UserController() {
        this.userRepository = new UserRepository();
    }

    public boolean register(String fname, String phone, String email, String password) {

        String nik = generateRandomNik();

        Passenger passenger = new Passenger(
                null,
                nik,
                fname,
                phone,
                email,
                password);

        return userRepository.register(passenger);
    }

    private String generateRandomNik() {
        Random rand = new Random();
        int number = 100000 + rand.nextInt(900000);
        return String.valueOf(number);
    }

    public User login(String email, String password) {
        return userRepository.login(email, password);
    }

}
