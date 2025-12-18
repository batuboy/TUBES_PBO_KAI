package kai.models.train;

import java.util.List;

import kai.models.train.num.Status;
import kai.models.train.num.LocomotiveType;

public class DieselTrain extends Locomotive {
    private double fuelCapacity; // kapasitas bahan bakar liter
    private double fuelConsumptionPerKm; // liter per km
    
    public DieselTrain(String locomotiveId, String name, Status status,
            LocomotiveType trainType, double fuelCapacity, double fuelConsumptionPerKm) {

        super(locomotiveId, name, status, trainType);
        this.fuelCapacity = fuelCapacity;
        this.fuelConsumptionPerKm = fuelConsumptionPerKm;
    }

    public double getFuelCapacity() {
        return fuelCapacity;
    }

    public void setFuelCapacity(double fuelCapacity) {
        this.fuelCapacity = fuelCapacity;
    }

    public double getFuelConsumptionPerKm() {
        return fuelConsumptionPerKm;
    }

    public void setFuelConsumptionPerKm(double fuelConsumptionPerKm) {
        this.fuelConsumptionPerKm = fuelConsumptionPerKm;
    }

    
} 