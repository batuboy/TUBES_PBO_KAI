public class PassangerController{
    
    private final UserRepository userRepo;

    public PassangerController(){
        userRepo = new userRepo();
    }

     public boolean register(String nik, String fullName, String email, String phone, String password) {
        
        if (userRepo.isEmailRegistered(email)) {
            return false;
        }
        Passenger passenger = new Passenger(nik, fullName, email, phone, password);
        userRepo.register(passenger);
        return true;
    }

    public Passenger login(String email, String password) {
        return (Passenger) userRepo.login(email, password);
    }
}