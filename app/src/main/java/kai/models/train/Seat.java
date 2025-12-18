package kai.models.train;

import kai.models.train.num.SeatStatus;
import kai.models.train.num.TrainClass;

public class Seat {
    private String seatNumber;
    private TrainClass tc;
    private SeatStatus ss;

    public Seat(String seatNumber, TrainClass tc, SeatStatus ss) {
        this.seatNumber = seatNumber;
        this.tc = tc;
        this.ss = ss;
    }

    public void setSeatStatus(SeatStatus ss){
        this.ss = ss;
    }

    public String getSeatStatus(){
        return ss.toString();
    }
    
    public String getSeatNumber() {
        return seatNumber;
    }

    public void setSeatNumber(String seatNumber) {
        this.seatNumber = seatNumber;
    }

    public String getTc() {
        return tc.toString();
    }

    public void setTc(TrainClass tc) {
        this.tc = tc;
    }

    

    
}
