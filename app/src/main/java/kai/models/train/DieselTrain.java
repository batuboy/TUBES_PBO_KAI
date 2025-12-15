package kai.models.train;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import kai.models.train.num.Status;
import kai.models.train.num.TrainType;

public class DieselTrain extends Locomotive {
    private double fuelCapacity; // kapasitas bahan bakar liter
    private double fuelConsumptionPerKm; // liter per km
    
    public DieselTrain(String trainId, String name, int traction, Status status, double fuelCapacity,
            double fuelConsumptionPerKm) {
        super(trainId, name, traction, status, TrainType.DIESEL);
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