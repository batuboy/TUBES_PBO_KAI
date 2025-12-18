package kai.controller;

import kai.models.train.Locomotive;

import java.util.ArrayList;
import java.util.List;
import kai.models.train.*;
import kai.repository.*;

public class TrainController {
    private final LocomotiveRepository locomotiveRepository;

    public TrainController() {
        this.locomotiveRepository = new LocomotiveRepository();
    }

    public List<Locomotive> getAllLocomotives() {
        return locomotiveRepository.getAllLocomotives();
    }

    public Locomotive getLocomotiveById(String id) {
        return locomotiveRepository.getLocomotiveById(id);
    }

    // public void addTrain(Locomotive train) {
    //     locomotiveRepository.addLocomotive(train);
    // }

    public void updateLocomotive(Locomotive train) {
        locomotiveRepository.updateLocomotive(train);
    }

    public void deleteTrain(String id) {
        locomotiveRepository.deletelocomotive(id);
    }
}
