package kai.controller;

import kai.models.train.Locomotive;

import java.util.ArrayList;
import java.util.List;
import kai.models.train.*;
import kai.repository.*;

public class TrainController {

    private final LocomotiveRepository locomotiveRepo;

    public TrainController() {
        this.locomotiveRepo = new LocomotiveRepository();
    }

    public void addTrain(Locomotive train){
        locomotiveRepo.addLocomotive(train);
    }

    public Locomotive getTrainById(String id){
        return locomotiveRepo.getLocomotiveById(id);
    }

    public List<Locomotive> getAllLocomotives(){
        return locomotiveRepo.getAllLocomotives();
    }

    public void updateTrain(Locomotive train){
        locomotiveRepo.updateLocomotive(train);
    }

    public void deleteTrain(String id){
        locomotiveRepo.deletelocomotive(id);
    }

    

}
