package DataModel;

import java.util.ArrayList;
import java.util.List;

public class Train {
    private int id;
    private String name;
    private Route route;
    private int totalSeats;
    private boolean delayed;
    private List<Schedule> schedules;

    public Train(int id, String name, Route route, int totalSeats) {
        this.id = id;
        this.name = name;
        this.route = route;
        this.totalSeats = totalSeats;
        this.delayed = false;
        this.schedules = new ArrayList<>();
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }  

    public Route getRoute() { return route; }
    public void setRoute(Route route) { this.route = route; }

    public int getTotalSeats() { return totalSeats; }
    public void setTotalSeats(int totalSeats) { this.totalSeats = totalSeats; }

    public boolean isDelayed() { return delayed; }
    public void setDelayed(boolean delayed) { this.delayed = delayed; }

    public List<Schedule> getSchedules() { return schedules; }
    public void addSchedule(Schedule schedule) { this.schedules.add(schedule); }

}