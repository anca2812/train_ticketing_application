package BusinessLogic;

import DataModel.*;
import java.util.ArrayList;
import java.util.List;

public class RouteService {
    private int nextId = 1;
    private List<Route> routes = new ArrayList<>();

    //the ability to find a route by id
    public Route findRouteById(int routeId) {
        for(Route route: routes) {
            if(route.getId() == routeId) {
                return route;
            }
        }
        return null; // route not found
    }

    //the ability to add a new route
    public Route addRoute(String name, List<Station> stations) {
        Route route = new Route(nextId++, name, stations);
        routes.add(route);

        return route;
    }

    //the ability to remove a route
    public String removeRoute(int routeId) {
        Route routeToRemove = findRouteById(routeId);

        if(routeToRemove != null) {
            routes.remove(routeToRemove);
            return "Route removed successfully.";
        }
        return "Route not found.";
    }

    //the ability to modify route details
    public String modifyRoute(int routeId, String newName, List<Station> newStations) {
        Route route = findRouteById(routeId);

        if(route != null) {
            route.setName(newName);
            route.setStations(newStations);
            return "Route details updated successfully.";
        }
        return "Route not found.";
    }

    //return all routes
    public List<Route> getAllRoutes() {
        return routes;
    }

    //the ability to find a route by station
    public List<Route> findRouteByStation(Station station) {
        List<Route> allRoutes = new ArrayList<>();
        for(Route route: routes) {
            if(route.getStations().contains(station)) {
                allRoutes.add(route);
            }
        }
        
        return allRoutes;
    }

    //the ability to verify if two station are on the same route
    public boolean areStationsOnSameRoute(Station from, Station to) {
        for (Route route : routes) {
            List<Station> stations = route.getStations();
            if (stations.contains(from) && stations.contains(to)) {
                return stations.indexOf(from) < stations.indexOf(to);
            }
        }
        return false;
    }
}