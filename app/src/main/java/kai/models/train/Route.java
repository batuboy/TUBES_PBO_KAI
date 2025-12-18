package kai.models.train;

import java.util.List;

public class Route {
    //kayaknya engga deh jadi kan nanti di tabel route_stop ada route_id = 1 anggapannya dia Bandung, terus kita ada route_id yang ke 2 
    // stasiun yang dilewati 

    private String routeId;
    private Station origin;
    private Station destination;
    
    public Route(String routeId,
                 Station origin,
                 Station destination) {

        this.routeId = routeId;
        this.origin = origin;
        this.destination = destination;
        // this.stops = stops;
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


    @Override
    public String toString() {
        return origin + " -> " + destination;
    }

    

}
