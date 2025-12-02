public class StationController {

    private final StationRepository stationRepo;

    public StationController(){
        stationRepo = new StationRepository();
    }
    // Add a new station
    public void addStation(Station station){
        stationRepo.addStation(station);                
    }

    // Get a station by ID
    public Station getStationById(String stationId){
        return stationRepo.getStationById(stationId);
    }

    // Get all stations
    public List<Station> getAllStations(){
        return stationRepo.getAllStations();
    }

    // Update an existing station
    public void updateStation(Station station){
        stationRepo.updateStation(station);
    }

    // Delete a station by ID
    public void deleteStation(String stationId){
        stationRepo.deleteStation(stationId);
    }
}
