package kai.models.user;

import kai.models.user.num.Position;

public class Conductor extends Employee {
    private String areaAssigned;

    public Conductor(String userId, String nik, String namaLengkap, String nomorTelepon, String email, String password,
            String employeeId, Position position, double salary, String areaAssigned) {
        super(userId, nik, namaLengkap, nomorTelepon, email, password, employeeId, position, salary);
        this.areaAssigned = areaAssigned;
    }

    public String getAreaAssigned() {
        return areaAssigned;
    }

    public void setAreaAssigned(String areaAssigned) {
        this.areaAssigned = areaAssigned;
    }

    
}
