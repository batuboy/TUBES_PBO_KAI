package kai.models.user;

import kai.models.user.num.Position;

public class Machinist extends Employee {

    private String licenseNumber;

    public Machinist(String nik, String namaLengkap, String nomorTelepon, String email, String password,
            String employeeId, double salary, String licenseNumber) {
        super(nik, namaLengkap, nomorTelepon, email, password, employeeId, Position.CONDUCTOR, salary);
        //TODO Auto-generated constructor stub
        this.licenseNumber = licenseNumber;
    }

    public String getLicenseNumber() {
        return licenseNumber;
    }

    public void setLicenseNumber(String licenseNumber) {
        this.licenseNumber = licenseNumber;
    }
    
    
}
