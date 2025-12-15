package kai.models.train;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import kai.models.train.num.Status;
import kai.models.train.num.TrainType;


public class ElectricTrain extends Locomotive {
    private int voltage;           // misal 1500 V
    private boolean airConditioned; // AC / non-AC

    public ElectricTrain(String trainId, String name, int traction, Status status, int voltage,
            boolean airConditioned) {
        super(trainId, name, traction, status, TrainType.ELECTRIC);
        this.voltage = voltage;
        this.airConditioned = airConditioned;
    }

    public int getVoltage() {
        return voltage;
    }

    public void setVoltage(int voltage) {
        this.voltage = voltage;
    }

    public boolean getAirConditioned() {
        return airConditioned;
    }

    public void setAirConditioned(boolean airConditioned) {
        this.airConditioned = airConditioned;
    }

    
    
}
