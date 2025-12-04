package kai.models.user;

import kai.models.user.num.Position;

public class Machinist extends Employee {
    private String licenseNumber;

    public Machinist(String userId, String nik, String namaLengkap, String nomorTelepon, String email, String password,
            String employeeId, Position position, double salary, String licenseNumber) {
        super(nik, namaLengkap, nomorTelepon, email, password, employeeId, position, salary);
        this.licenseNumber = licenseNumber;
    }

    public String getLicenseNumber() {
        return licenseNumber;
    }

    public void setLicenseNumber(String licenseNumber) {
        this.licenseNumber = licenseNumber;
    }
    
    
}
