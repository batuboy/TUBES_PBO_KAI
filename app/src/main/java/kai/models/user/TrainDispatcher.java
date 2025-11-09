package kai.models.user;

import kai.models.user.num.Position;

public class TrainDispatcher extends Employee {

    public TrainDispatcher(String userId, String nik, String namaLengkap, String nomorTelepon, String email,
            String password, String employeeId, Position position, double salary) {
        super(userId, nik, namaLengkap, nomorTelepon, email, password, employeeId, position, salary);
    }
    
}
