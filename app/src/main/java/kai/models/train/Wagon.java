package kai.models.train;

import java.util.List;

import kai.models.train.num.Status;
import kai.models.train.num.TrainClass;

public class Wagon extends Railcar {
    private double cargoCapacity; // kapasitas kargo dalam ton
    public Wagon(String railcarId, String name, TrainClass trainClass,Status status, double cargoCapacity) {
        super(railcarId, name, trainClass, status);
        this.cargoCapacity = cargoCapacity;
        //TODO Auto-generated constructor stub
    }
    
    public double getCargoCapacity() {
        return cargoCapacity;
    }
    
    public void setCargoCapacity(double cargoCapacity) {
        this.cargoCapacity = cargoCapacity;
    }
}
