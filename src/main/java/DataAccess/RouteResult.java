package DataAccess;
import java.util.List;
import DataModel.*;

public class RouteResult {
    private List<Schedule> schedules;
    private List<Station> stations;

    public RouteResult(List<Schedule> schedules, List<Station> stations) {
        this.schedules = schedules;
        this.stations = stations;
    }

    public List<Schedule> getSchedules() { return schedules; }
    public List<Station> getStations() { return stations; }

    public void printResult() {
        if(schedules.isEmpty()) {
            System.out.println("No schedules found for the given route.");
            return;
        }
        else if(schedules.size() == 1) {
            Schedule schedule = schedules.get(0);
            System.out.println("Schedule found:");
            System.out.println(schedule.getId() + ": Train " + schedule.getTrain().getName() + " departs at " + schedule.getDepartureTime() + " and arrives at " + schedule.getArrivalTime());
        }
        else {
            System.out.println("Multiple schedules found for the given route:");
            for(Schedule schedule: schedules) {
                System.out.println(schedule.getId() + ": Train " + schedule.getTrain().getName() + " departs at " + schedule.getDepartureTime() + " and arrives at " + schedule.getArrivalTime());
            }
        }
    }
    
}
