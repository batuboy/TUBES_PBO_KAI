package kai.models.train;

import java.util.List;

public class Route {
    private String routeId; // 
    private int origin;
    private int destination;  // jadi mending tetep jadi objek di java tapi di sql kita ambil FK nya aja? jadi ntar tuh SELECT name FROM stops wherre id = 1 ?? ntar returnnya Stops or route
    //kayaknya engga deh jadi kan nanti di tabel route_stop ada route_id = 1 anggapannya dia Bandung, terus kita ada route_id yang ke 2 
    private List<Integer> stops;  // stasiun yang dilewati 
    
    public Route(String routeId, int origin, int destination, List<Integer> stops) {
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

    public int getOrigin() {
        return origin;
    }

    public void setOrigin(int origin) {
        this.origin = origin;
    }

    public int getDestination() {
        return destination;
    }

    public void setDestination(int destination) {
        this.destination = destination;
    }

    public List<Integer> getStops() {
        return stops;
    }

    public void setStops(List<Integer> stops) {
        this.stops = stops;
    }

    
}
