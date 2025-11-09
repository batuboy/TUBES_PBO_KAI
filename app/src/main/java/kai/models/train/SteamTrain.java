package kai.models.train;

import java.util.List;

import kai.models.train.num.Status;

public class SteamTrain extends Locomotive {
    private double boilerPressure; // tekanan uap
    private double waterCapacity;  // liter air untuk uap

    public SteamTrain(String trainId, String name, List<Railcar> coaches, Status status, double boilerPressure,
            double waterCapacity) {
        super(trainId, name, coaches, status);
        this.boilerPressure = boilerPressure;
        this.waterCapacity = waterCapacity;
    }

    public double getBoilerPressure() {
        return boilerPressure;
    }

    public void setBoilerPressure(double boilerPressure) {
        this.boilerPressure = boilerPressure;
    }

    public double getWaterCapacity() {
        return waterCapacity;
    }

    public void setWaterCapacity(double waterCapacity) {
        this.waterCapacity = waterCapacity;
    }

    
}
