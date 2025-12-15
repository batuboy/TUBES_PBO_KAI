package kai.models.user;

import kai.models.user.num.Position;

public class TrainDispatcher extends Employee {

    public TrainDispatcher(String nik, String namaLengkap, String nomorTelepon, String email, String password,
            String employeeId, double salary) {
        super(nik, namaLengkap, nomorTelepon, email, password, employeeId, Position.CONDUCTOR, salary);
        //TODO Auto-generated constructor stub
    }

    
}
