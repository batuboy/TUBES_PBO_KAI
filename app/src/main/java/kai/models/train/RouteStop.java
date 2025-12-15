package kai.models.train;

public class RouteStop {
    private Station station;
    private int stopOrder;

    public RouteStop(Station station, int stopOrder) {
        this.station = station;
        this.stopOrder = stopOrder;
    }
}
