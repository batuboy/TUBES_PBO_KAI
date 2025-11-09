package kai.models.train;

import java.util.List;

import kai.models.train.num.Status;

public abstract class Locomotive {
    private String trainId;
    private String name;
    private List<Railcar> coaches;  // daftar gerbong
    private Status status; //maintenance or available
    // private int totalCapacity;      // total kursi seluruh gerbong
    
    public Locomotive(String trainId, String name, List<Railcar> coaches, Status status) {
        this.trainId = trainId;
        this.name = name;
        this.coaches = coaches;
        this.status = status;
    }

    public String getTrainId() {
        return trainId;
    }

    public void setTrainId(String trainId) {
        this.trainId = trainId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Railcar> getCoaches() {
        return coaches;
    }

    public void setCoaches(List<Railcar> coaches) {
        this.coaches = coaches;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    
}
