package kai.models.user;

import kai.models.user.num.Position;

public class Admin extends Employee {

    public Admin(String userId, String nik, String namaLengkap, String nomorTelepon, String email, String password,
            String employeeId, double salary) {
        super(userId, nik, namaLengkap, nomorTelepon, email, password, Position.ADMIN, employeeId, salary);
        //TODO Auto-generated constructor stub
    }

    public Admin(String userId, String nik, String namaLengkap, String nomorTelepon, String email, String password) {
        super(userId, nik, namaLengkap, nomorTelepon, email, password, Position.ADMIN);
        //TODO Auto-generated constructor stub
    }


}
