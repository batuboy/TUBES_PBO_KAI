package kai.controller;

import kai.models.user.Admin;
import kai.models.user.User;
import kai.repository.EmployeeRepository;
import kai.repository.UserRepository;

public class LoginController {

    private UserRepository userRepo;
    private EmployeeRepository employRepo;

    public LoginController() {
        this.userRepo = new UserRepository();
    }

    public User login(String email, String password) {
        User user = userRepo.login(email, password); 
        if(user == null) return null;

        Admin admin = employRepo.getAdminByEmail(email); 
        if(admin != null) {
            return admin;
        }
        return user; 
    }
}



