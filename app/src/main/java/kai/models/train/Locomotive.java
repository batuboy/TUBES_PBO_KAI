package kai.models.train;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import kai.models.train.num.Status;
import kai.models.train.num.TrainType;

public abstract class Locomotive {
    private String locomotiveId;
    private String name;
    // private List<Railcar> coaches;  // daftar gerbong
    private TrainType trainType;
    private Status status; 
    private int traction;
    
    public Locomotive(String locomotoviId, String name, int traction, Status status, TrainType trainType) {
        this.trainType = trainType;
        this.locomotiveId = locomotoviId;
        this.name = name;
        this.traction = traction;
        this.status = status;
    }
    public String getLocomotiveId() {
        return locomotiveId;
    }

    public void setLocomotiveId(String locomotoviId) {
        this.locomotiveId = locomotoviId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
    public int getTraction() {
        return traction;
    }
    public void setTraction(int traction) {
        this.traction = traction;
    }

    
}
  