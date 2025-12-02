package kai.models.train;

import java.util.List;

import kai.models.train.num.RailcarType;
import kai.models.train.num.Status;
import kai.models.train.num.TrainClass;

public class Carriage extends Railcar {
    private TrainClass trainClass;
    private List<Seat> seat; 

    public Carriage(String railcarId, TrainClass trainClass, String name, Status status) {
        super(railcarId, name, RailcarType.CARRIAGE, status);
        this.trainClass = trainClass;
    }

    public List<Seat> getSeat() {
        return this.seat;
    }

    public void setSeatNumbers(List<Seat> seat) {
        this.seat = seat;
    }

    public int getCapacity(){
        return seat.size();
    }
}
