package kai.models.reservation;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import kai.models.train.Locomotive;
import kai.models.train.Route;
import kai.models.train.Railcar;
import kai.models.train.num.RailcarType;

public class Schedule {
    private String scheduleId;
    private Locomotive locomotive;
    private List<Railcar> railcars;
    private Route route;
    private double price;
    private LocalDateTime departureTime;
    private LocalDateTime arrivalTime;
    private String serviceName;
    private String serviceNo;
    
    public Schedule(String scheduleId, Locomotive locomotive, List<Railcar> railcars,  Route route, double price, LocalDateTime departureTime,
            LocalDateTime arrivalTime, String serviceName, String serviceNo) {
        this.scheduleId = scheduleId;
        this.locomotive = locomotive;
        this.railcars = new ArrayList<>();
        this.route = route;
        this.price = price;
        this.departureTime = departureTime;
        this.arrivalTime = arrivalTime;
        this.serviceName = serviceName;
        this.serviceNo = serviceNo;
    }

    public String getScheduleId() {
        return scheduleId;
    }

    public void setScheduleId(String scheduleId) {
        this.scheduleId = scheduleId;
    }

    public Locomotive getLocomotive() {
        return locomotive;
    }

    public void setLocomotive(Locomotive locomotive) {
        this.locomotive = locomotive;
    }

    public Route getRoute() {
        return route;
    }

    public void setRoute(Route route) {
        this.route = route;
    }

    public List<Railcar> getRailcars() {
        return railcars;
    }

    public void setRailcars(List<Railcar> railcars) {
        this.railcars = railcars;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public LocalDateTime getDepartureTime() {
        return departureTime;
    }

    public void setDepartureTime(LocalDateTime departureTime) {
        this.departureTime = departureTime;
    }

    public LocalDateTime getArrivalTime() {
        return arrivalTime;
    }

    public void setArrivalTime(LocalDateTime arrivalTime) {
        this.arrivalTime = arrivalTime;
    }

    public String getClassType() {
        return railcars.get(0).getRailcarType();
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public String getServiceNo() {
        return serviceNo;
    }

    public void setServiceNo(String serviceNo) {
        this.serviceNo = serviceNo;
    }
    
}
