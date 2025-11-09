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
    
    public Railcar(String railcarId, String name, TrainClass trainClass, int capacity, List<String> seatNumbers,
            Status status) {
        this.railcarId = railcarId;
        this.name = name;
        this.trainClass = trainClass;
        this.capacity = capacity;
        this.seatNumbers = seatNumbers;
        this.status = status;
    }

    public String getRailcarId() {
        return railcarId;
    }

    public void setRailcarId(String railcarId) {
        this.railcarId = railcarId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public TrainClass getTrainClass() {
        return trainClass;
    }

    public void setTrainClass(TrainClass trainClass) {
        this.trainClass = trainClass;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public List<String> getSeatNumbers() {
        return seatNumbers;
    }

    public void setSeatNumbers(List<String> seatNumbers) {
        this.seatNumbers = seatNumbers;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    } 

    
    
}
