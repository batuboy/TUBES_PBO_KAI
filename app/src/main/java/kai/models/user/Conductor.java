package kai.models.user;

import kai.models.user.num.Position;

public class Conductor extends Employee {
    private Station stationAssigned;

    public Conductor(String userId, String nik, String namaLengkap, String nomorTelepon, String email, String password,
            String employeeId, Position position, double salary, Station areaAssigned) {
        super(userId, nik, namaLengkap, nomorTelepon, email, password, employeeId, position, salary);
        this.areaAssigned = areaAssigned;
    }

    public Station getAreaAssigned() {
        return areaAssigned;
    }

    public void setAreaAssigned(Station areaAssigned) {
        this.areaAssigned = areaAssigned;
    }    
}
