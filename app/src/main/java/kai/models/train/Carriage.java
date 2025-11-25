package kai.models.train;

import java.util.List;

import kai.models.train.num.Status;
import kai.models.train.num.TrainClass;

public class Carriage extends Railcar {
    private List<String> seatNumbers; 

    public Carriage(String railcarId, String name, TrainClass trainClass, Status status, List<String> seatNumbers) {
        super(railcarId, name, trainClass, status);
        this.seatNumbers = seatNumbers;
    }

    public List<String> getSeatNumbers() {
        return seatNumbers;
    }

    public void setSeatNumbers(List<String> seatNumbers) {
        this.seatNumbers = seatNumbers;
    }

    
}
