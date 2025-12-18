package kai.models.reservation;

import java.time.LocalDateTime;
import kai.models.train.Locomotive;
import kai.models.train.Route;

public class Schedule {
    private String scheduleId;
    private Locomotive locomotive;
    private Route route;
    private LocalDateTime departureTime;
    private LocalDateTime arrivalTime;
    private String serviceName;
    private String serviceNo;
    private double price;
    private String status; // "ACTIVE" or "CANCELED"

    public Schedule(String scheduleId, Locomotive locomotive, Route route,
            LocalDateTime departureTime, LocalDateTime arrivalTime,
            String serviceName, String serviceNo, double price) {
        this.scheduleId = scheduleId;
        this.locomotive = locomotive;
        this.route = route;
        this.departureTime = departureTime;
        this.arrivalTime = arrivalTime;
        this.serviceName = serviceName;
        this.serviceNo = serviceNo;
        this.price = price;
        this.status = "ACTIVE"; // default
    }

    // Getters & Setters for all fields
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

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
