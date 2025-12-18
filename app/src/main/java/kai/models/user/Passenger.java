package kai.models.user;

import kai.models.user.num.Position;

public class Passenger extends User {

    public Passenger(String userId, String nik, String namaLengkap, String nomorTelepon, String email, String password) {
        super(userId, nik, namaLengkap, nomorTelepon, email, password, Position.PASSENGER);
        //TODO Auto-generated constructor stub
    }

    

}
