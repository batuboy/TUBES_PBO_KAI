package kai.models.train;

import java.util.List;

public class Route {
    private String routeId; // 
    private String origin;
    private String destination;  // jadi mending tetep jadi objek di java tapi di sql kita ambil FK nya aja? jadi ntar tuh SELECT name FROM stops wherre id = 1 ?? ntar returnnya Stops or route
    //kayaknya engga deh jadi kan nanti di tabel route_stop ada route_id = 1 anggapannya dia Bandung, terus kita ada route_id yang ke 2 
    private List<String> stops;  // stasiun yang dilewati 
    
    public Route(String routeId, String origin, String destination, List<String> stops) {
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

    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public List<String> getStops() {
        return stops;
    }

    public void setStops(List<String> stops) {
        this.stops = stops;
    }

    
}
