package kai.models.user;

import kai.models.train.Station;
import kai.models.user.num.Position;

public class Conductor extends Employee {

    public Conductor(String userId, String nik, String namaLengkap, String nomorTelepon, String email, String password,
            Position passenger, String employeeId, double salary) {
        super(userId, nik, namaLengkap, nomorTelepon, email, password, passenger, employeeId, salary);
        //TODO Auto-generated constructor stub
    }

    // private Station stationAssigned;


}
