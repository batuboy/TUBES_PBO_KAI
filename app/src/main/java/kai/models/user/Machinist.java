package kai.models.user;

import kai.models.user.num.Position;

public class Machinist extends Employee {

    public Machinist(String userId, String nik, String namaLengkap, String nomorTelepon, String email, String password,
            Position passenger, String employeeId, double salary) {
        super(userId, nik, namaLengkap, nomorTelepon, email, password, passenger, employeeId, salary);
        //TODO Auto-generated constructor stub
    }

    private String licenseNumber;


    public String getLicenseNumber() {
        return licenseNumber;
    }

    public void setLicenseNumber(String licenseNumber) {
        this.licenseNumber = licenseNumber;
    }
    
    
}
