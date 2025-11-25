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
    
    public Schedule(String scheduleId, Locomotive locomotive, Route route, LocalDateTime departureTime,
            LocalDateTime arrivalTime) {
        this.scheduleId = scheduleId;
        this.locomotive = locomotive;
        this.route = route;
        this.departureTime = departureTime;
        this.arrivalTime = arrivalTime;
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
}
