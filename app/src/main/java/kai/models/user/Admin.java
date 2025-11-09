package kai.models.user;

import kai.models.user.num.Position;

public class Admin extends Employee {
    private String adminID;

    public Admin(String userId, String nik, String namaLengkap, String nomorTelepon, String email, String password,
            String employeeId, Position position, double salary, String adminID) {
        super(userId, nik, namaLengkap, nomorTelepon, email, password, employeeId, position, salary);
        this.adminID = adminID;
    }

    public String getAdminID() {
        return adminID;
    }

    public void setAdminID(String adminID) {
        this.adminID = adminID;
    }

    
    
}
