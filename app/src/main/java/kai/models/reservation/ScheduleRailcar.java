package kai.models.reservation;

public class ScheduleRailcar {
    private String scheduleId;
    private String railcarId;
    private int order;
    
    public ScheduleRailcar(String scheduleId, String railcarId, int order) {
        this.scheduleId = scheduleId;
        this.railcarId = railcarId;
        this.order = order;
    }


    public String getRailcarId() {
        return railcarId;
    }
    public int getOrder() {
        return order;
    }

    public String getScheduleId() {
        return scheduleId;
    }
    
}

