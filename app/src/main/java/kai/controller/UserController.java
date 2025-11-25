import kai.models.user.User;
import kai.repository.UserRepository;

public class UserController {
    
    private UserRepository userRepository;

    public UserController() {
        userRepository = new UserRepository();
    }

    public void register(String nik, String namaLengkap, String nomorTelepon, String email, String password) {
        this.userRepository.register(new User(nik, namaLengkap, nomorTelepon, email, password));
    }

    public User login(String email, String password) {
       User user = this.userRepository.login(email, password);
       return user;
    }
}