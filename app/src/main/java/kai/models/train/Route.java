package kai.models.train;

import java.util.List;

public class Route {
    private String routeId;
    private Station origin;
    private Station destination;  
    private List<Station> stops;  // stasiun yang dilewati 
    
    public Route(String routeId, Station origin, Station destination, List<Station> stops) {
        this.routeId = routeId;
        this.origin = origin;
        this.destination = destination;
        this.stops = stops;
    }

    public String getRouteId() {
        return routeId;
    }

    public void setRouteId(String routeId) {
        this.routeId = routeId;
    }

    public Station getOrigin() {
        return origin;
    }

    public void setOrigin(Station origin) {
        this.origin = origin;
    }

    public Station getDestination() {
        return destination;
    }

    public void setDestination(Station destination) {
        this.destination = destination;
    }

    public List<Station> getStops() {
        return stops;
    }

    public void setStops(List<Station> stops) {
        this.stops = stops;
    }

    
}
