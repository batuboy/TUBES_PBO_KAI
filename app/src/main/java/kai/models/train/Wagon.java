package kai.models.train;

import java.util.List;

import kai.models.train.num.RailcarType;
import kai.models.train.num.Status;


public class Wagon extends Railcar {
    private double cargoCapacity; 

    public Wagon(String railcarId, String name, Status status, double cargoCapacity) {
        super(railcarId, name, RailcarType.WAGON, status);
        this.cargoCapacity = cargoCapacity;
    }
    
    public double getCargoCapacity() {
        return cargoCapacity;
    }
    
    public void setCargoCapacity(double cargoCapacity) {
        this.cargoCapacity = cargoCapacity;
    }
}
