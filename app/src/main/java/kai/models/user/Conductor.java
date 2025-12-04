package kai.models.user;

import kai.models.train.Station;
import kai.models.user.num.Position;

public class Conductor extends Employee {

    private Station stationAssigned;

    public Conductor(String nik, String namaLengkap, String nomorTelepon, String email, String password,
            String employeeId, Position position, double salary, Station stationAssigned) {
        super(nik, namaLengkap, nomorTelepon, email, password, employeeId, position, salary);
        this.stationAssigned = stationAssigned;
    }

    public Station getStationAssigned(){
        return stationAssigned;
    }

    public void setStationAssigned(Station stationAssigned){
        this.stationAssigned = stationAssigned;
    }
}
