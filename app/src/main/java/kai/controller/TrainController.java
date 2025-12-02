package kai.controller;

import kai.models.train.Locomotive;

import java.util.ArrayList;
import java.util.List;
import kai.models.train.*;
import kai.repository.*;

public class TrainController {
    // private List<Locomotive> locomotiveList;
    // private List<Railcar> railcarsList;
    // private List<Station> stationsList;
    // private List<Route> routesList;

    private final ITrainRepository trainRepo;

    public TrainController() {
        this.trainRepo = new TrainRepositoryImpl();
    }


    public void addTrain(Locomotive train){
        trainRepo.addLocomotive(train);
    }
    public Locomotive getTrainById(String id){
        return trainRepo.getLocomotiveById(id);
    }

    public List<Locomotive> getAllTrains(){
        return trainRepo.getAllLocomotive();
    }
    public void updateTrain(Locomotive train){
        trainRepo.updateLocomotive(train);
    }
    public void deleteTrain(String id){
        trainRepo.deletelocomotive(id);
    }
}
