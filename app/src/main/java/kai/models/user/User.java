package kai.models.user;

import kai.models.user.num.Position;

public abstract class User {
    private String userId;
    private String nik;
    private String namaLengkap;
    private String nomorTelepon;
    private String email;
    private String password;
    private Position position;

    public User(String userId, String nik, String namaLengkap, String nomorTelepon, String email, String password,
            Position passenger) {
        this.userId = userId;
        this.nik = nik;
        this.namaLengkap = namaLengkap;
        this.nomorTelepon = nomorTelepon;
        this.email = email;
        this.password = password;
        this.position = passenger;
    }

    

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getNik() {
        return nik;
    }

    public void setNik(String nik) {
        this.nik = nik;
    }

    public String getNamaLengkap() {
        return namaLengkap;
    }

    public void setNamaLengkap(String namaLengkap) {
        this.namaLengkap = namaLengkap;
    }

    public String getNomorTelepon() {
        return nomorTelepon;
    }

    public void setNomorTelepon(String nomorTelepon) {
        this.nomorTelepon = nomorTelepon;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }


    public Position getPosition() {
        return position;
    }
 
}
