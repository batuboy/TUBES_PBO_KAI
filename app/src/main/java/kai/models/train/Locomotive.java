package kai.models.train;

import java.util.List;

import kai.models.train.num.Status;
import kai.models.train.num.LocomotiveType;

public class Locomotive {
    private String locomotiveId;
    private String name;
    private LocomotiveType locomotiveType;
    private Status status; 
    
    public Locomotive(String locomotiveId, String name, Status status, LocomotiveType locomotiveType) {
        this.locomotiveType = locomotiveType;
        this.locomotiveId = locomotiveId;
        this.name = name;
       this.status = status;
    }

    public Locomotive(String locomotiveId, String name, Status status) {
        //TODO Auto-generated constructor stub
        this.locomotiveId = locomotiveId;
        this.name = name;
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

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }    

    public String getLocomotiveType() {
        return locomotiveType.toString();
    }

    public void setLocmotiveType(LocomotiveType trainType) {
        this.locomotiveType = trainType;
    }

    @Override
    public String toString() {
        return locomotiveId + " | " + name;
    }

}
  