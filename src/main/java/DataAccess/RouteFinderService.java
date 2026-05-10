package DataAccess;
import DataModel.*;
import BusinessLogic.*;
import java.util.ArrayList;
import java.util.List;

public class RouteFinderService {
    private ScheduleService scheduleService;
    private RouteService routeService;

    public RouteFinderService(ScheduleService scheduleService, RouteService routeService) {
        this.scheduleService = scheduleService;
        this.routeService = routeService;
    }

    //find all routes between two stations, including direct and with one changeover
    public List<RouteResult> findRoutes(Station from, Station to) {
        List<RouteResult> results = new ArrayList<>();

        //direct routes
        List<RouteResult> directRoutes = findDirectRoutes(from, to);
        results.addAll(directRoutes);

        //routes with one changeover
        List<RouteResult> changeoverRoutes = findChangeoverRoutes(from, to);
        results.addAll(changeoverRoutes);

        return results;
    }

    //find direct routes between two stations
    private List<RouteResult> findDirectRoutes(Station from, Station to) {
        List<RouteResult> results = new ArrayList<>();
        List<Schedule> allSchedules = scheduleService.getAllSchedules();

        for (Schedule schedule : allSchedules) {
            Route route = schedule.getTrain().getRoute();
            List<Station> stations = route.getStations();

            int fromIndex = -1;
            int toIndex = -1;

            for (int i = 0; i < stations.size(); i++) {
                if (stations.get(i).getId() == from.getId()) fromIndex = i;
                if (stations.get(i).getId() == to.getId()) toIndex = i;
            }

            if (fromIndex != -1 && toIndex != -1 && fromIndex < toIndex) {
                List<Schedule> schedules = new ArrayList<>();
                schedules.add(schedule);
                results.add(new RouteResult(schedules, new ArrayList<>()));
            }
        }
        return results;
    }

    //find routes with one train changeover
    private List<RouteResult> findChangeoverRoutes(Station from, Station to) {
        List<RouteResult> results = new ArrayList<>();
        List<Schedule> allSchedules = scheduleService.getAllSchedules();

        for (Schedule firstSchedule : allSchedules) {
            Route firstRoute = firstSchedule.getTrain().getRoute();
            List<Station> firstStations = firstRoute.getStations();

            int fromIndex = -1;
            for (int i = 0; i < firstStations.size(); i++) {
                if (firstStations.get(i).getId() == from.getId()) {
                    fromIndex = i;
                    break;
                }
            }

            if (fromIndex == -1) continue;

            for (int i = fromIndex + 1; i < firstStations.size(); i++) {
                Station changeover = firstStations.get(i);

                for (Schedule secondSchedule : allSchedules) {
                    if (secondSchedule.getId() == firstSchedule.getId()) continue;

                    Route secondRoute = secondSchedule.getTrain().getRoute();
                    List<Station> secondStations = secondRoute.getStations();

                    int changeoverIndex = -1;
                    int toIndex = -1;

                    for (int j = 0; j < secondStations.size(); j++) {
                        if (secondStations.get(j).getId() == changeover.getId()) changeoverIndex = j;
                        if (secondStations.get(j).getId() == to.getId()) toIndex = j;
                    }

                    if (changeoverIndex != -1 && toIndex != -1 && changeoverIndex < toIndex) {
                        if (secondSchedule.getDepartureTime().isAfter(firstSchedule.getArrivalTime())) {
                            List<Schedule> schedules = new ArrayList<>();
                            schedules.add(firstSchedule);
                            schedules.add(secondSchedule);

                            List<Station> changeovers = new ArrayList<>();
                            changeovers.add(changeover);

                            results.add(new RouteResult(schedules, changeovers));
                        }
                    }
                }
            }
        }
        return results;
    }

    public void printRoutes(Station from, Station to) {
        List<RouteResult> results = findRoutes(from, to);

        System.out.println("\nRoute found between " + from.getName() + " and " + to.getName() + ":");

        if (results.isEmpty()) {
            System.out.println("No routes found between " + from.getName() + " and " + to.getName());
            return;
        }

        for (int i = 0; i < results.size(); i++) {
            System.out.println("\nOption " + (i + 1) + ":");
            results.get(i).printResult();
        }
    }
}
