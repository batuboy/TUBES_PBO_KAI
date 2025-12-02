package kai.models.train;

import java.util.List;

import kai.models.train.num.Status;
import kai.models.train.num.TrainType;

public class Locomotive {
    private String locomotiveId;
    private String name;
    private List<Railcar> coaches;  // daftar gerbong
    private TrainType trainType;
    private Status status; 
    
    public Locomotive(String locomotoviId, String name, List<Railcar> coaches, Status status, TrainType trainType) {
        this.trainType = trainType;
        this.locomotiveId = locomotoviId;
        this.name = name;
        this.coaches = coaches;
        this.status = status;
    }

    public String getLocotomiveId() {
        return locomotiveId;
    }

    public void setLocotomiveId(String locomotoviId) {
        this.locomotiveId = locomotoviId;
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

    public String getStatus() {
        return status.toString();
    }

    public void setStatus(Status status) {
        this.status = status;
    }    


    public String getTrainType() {
        return trainType.toString();
    }

    public void setTrainType(TrainType trainType) {
        this.trainType = trainType;
    }
}
  