package kai.models.user;

import kai.models.train.Station;
import kai.models.user.num.Position;

public class Conductor extends Employee {

    // private Station stationAssigned;

    public Conductor(String nik, String namaLengkap, String nomorTelepon, String email, String password,
            String employeeId, double salary) {
        super(nik, namaLengkap, nomorTelepon, email, password, employeeId, Position.CONDUCTOR, salary);
        
    }

}
