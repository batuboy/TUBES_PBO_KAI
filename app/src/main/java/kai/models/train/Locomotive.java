package kai.models.train;

import java.util.List;

import kai.models.train.num.Status;

public abstract class Locomotive {
    private String trainId;
    private String name;
    private List<Railcar> coaches;  // daftar gerbong
    private Status status; //maintenance or available
    // private int totalCapacity;      // total kursi seluruh gerbong

}
