package kai.models.user;

import kai.models.user.num.Position;

public class Admin extends Employee {

    public Admin(String nik, String namaLengkap, String nomorTelepon, String email, String password, String employeeId, double salary) {
        super(nik, namaLengkap, nomorTelepon, email, password, employeeId, Position.ADMIN, salary);
        //TODO Auto-generated constructor stub
    }
}
