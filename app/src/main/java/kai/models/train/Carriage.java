package kai.models.train;

import java.util.List;

import kai.models.train.num.Status;
import kai.models.train.num.TrainClass;

public class Carriage extends Railcar {

    public Carriage(String railcarId, String name, TrainClass trainClass, int capacity, List<String> seatNumbers,
            Status status) {
        super(railcarId, name, trainClass, capacity, seatNumbers, status);
    }
    
}
