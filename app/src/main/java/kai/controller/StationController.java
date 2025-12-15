package kai.controller;

import java.util.List;

import kai.models.train.Station;
import kai.repository.StationRepository;

public class StationController {

    private final StationRepository stationRepo;

    public StationController(){
        stationRepo = new StationRepository();
    }

    public void addStation(Station station){
        stationRepo.addStation(station);                
    }

    public Station getStationById(String stationId){
        return stationRepo.getStationById(stationId);
    }

    public List<Station> getAllStations(){
        return stationRepo.getAllStations();
    }

    public void updateStation(Station station){
        stationRepo.updateStation(station);
    }

    public void deleteStation(String stationId){
        stationRepo.deleteStation(stationId);
    }
}
