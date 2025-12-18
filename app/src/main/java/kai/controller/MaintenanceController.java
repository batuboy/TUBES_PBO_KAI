package kai.controller;

import java.util.List;

import kai.models.train.Locomotive;
import kai.models.train.Railcar;
import kai.models.train.num.Status;
import kai.repository.LocomotiveRepository;
import kai.repository.RailCarRepository;

public class MaintenanceController {
    private LocomotiveRepository locoRepo;
    private RailCarRepository railRepo;

    public MaintenanceController() {
        locoRepo = new LocomotiveRepository();
        railRepo = new RailCarRepository();
    }

    public List<Locomotive> getAllLocomotives() {
        return locoRepo.findAllLocomotive();
    }

    public List<Railcar> getAllRailCars() {
        return railRepo.findAllRailCar();
    }

    public void toggleLocomotiveStatus(Locomotive loco) {
        Status current = loco.getStatus();

        Status newStatus = (current == Status.AVAILABLE)
                ? Status.MAINTENANCE
                : Status.AVAILABLE;

        loco.setStatus(newStatus); // update object
        locoRepo.updateStatusLocomotive(loco.getLocomotiveId(), newStatus);
    }

    public void toggleRailCarStatus(Railcar railCar) {
        Status current = railCar.getStatus();

        Status newStatus = (current == Status.AVAILABLE)
                ? Status.MAINTENANCE
                : Status.AVAILABLE;

        railCar.setStatus(newStatus);
        railRepo.updateStatusRailCar(railCar.getRailcarId(), newStatus);
    }
}
