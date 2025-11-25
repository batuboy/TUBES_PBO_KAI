package kai.models.train;

import java.util.List;

import kai.models.train.num.Status;
import kai.models.train.num.TrainType;

public class DieselTrain extends Locomotive {
    private double fuelCapacity; // kapasitas bahan bakar liter
    private double fuelConsumptionPerKm; // liter per km
    
    public DieselTrain(String trainId, String name, List<Railcar> coaches, Status status, double fuelCapacity,
            double fuelConsumptionPerKm, TrainType trainType) {
        super(trainId, name, coaches, status, trainType);
        this.fuelCapacity = fuelCapacity;
        this.fuelConsumptionPerKm = fuelConsumptionPerKm;
    }

    
} 