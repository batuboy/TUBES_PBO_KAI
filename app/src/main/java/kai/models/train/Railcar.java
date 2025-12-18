package kai.models.train;

import java.util.List;

import kai.models.train.num.RailcarType;
import kai.models.train.num.TrainClass;
import kai.models.train.num.Status;
import kai.models.train.num.LocomotiveType;

public abstract class Railcar {
    private String railcarId; 
    private String name;
    private RailcarType type; 
    private Status status;
    
    public Railcar(String railcarId, String name, RailcarType type, Status status) {
        this.railcarId = railcarId;
        this.name = name;
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

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    } 

    public String getRailcarType(){
        return this.type.toString();
    }

    public void setRailcarType(RailcarType trainType){
        this.type = trainType;
    }
    
}
