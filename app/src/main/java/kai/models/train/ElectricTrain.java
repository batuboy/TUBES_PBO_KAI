package kai.models.train;

import java.util.List;

import kai.models.train.num.Status;

public class ElectricTrain extends Locomotive {
    private int voltage;           // misal 1500 V
    private boolean airConditioned; // AC / non-AC

    public ElectricTrain(String trainId, String name, List<Railcar> coaches, Status status, int voltage,
            boolean airConditioned) {
        super(trainId, name, coaches, status);
        this.voltage = voltage;
        this.airConditioned = airConditioned;
    }

    public int getVoltage() {
        return voltage;
    }

    public void setVoltage(int voltage) {
        this.voltage = voltage;
    }

    public boolean isAirConditioned() {
        return airConditioned;
    }

    public void setAirConditioned(boolean airConditioned) {
        this.airConditioned = airConditioned;
    }

    
    
}
