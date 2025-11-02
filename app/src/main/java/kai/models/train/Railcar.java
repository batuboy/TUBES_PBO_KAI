package kai.models.train;

import java.util.List;

import kai.models.train.num.TrainClass;
import kai.models.train.num.Status;

public abstract class Railcar {
    private String railcarId; 
    private String name;
    private TrainClass trainClass; 
    private int capacity;
    private List<String> seatNumbers; 
    private Status status; 

    
}
