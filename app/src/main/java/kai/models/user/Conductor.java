package kai.models.user;

import kai.models.user.num.Position;
import kai.models.train.*;

public class Conductor extends Employee {
    private Station stationAssigned;

    public Conductor(String userId, String nik, String namaLengkap, String nomorTelepon, String email, String password,
            String employeeId, Position position, double salary, Station stationAssigned) {
        super(userId, nik, namaLengkap, nomorTelepon, email, password, employeeId, position, salary);
        this.stationAssigned = stationAssigned;
    }

    public Station getAreaAssigned() {
        return stationAssigned;
    }

    public void setAreaAssigned(Station stationAssigned) {
        this.stationAssigned = stationAssigned;
    }    
}
