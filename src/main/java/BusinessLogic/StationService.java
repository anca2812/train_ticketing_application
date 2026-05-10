package BusinessLogic;

import DataModel.*;
import java.util.ArrayList;
import java.util.List;

public class StationService {
    private int nextId = 1;
    private List<Station> stations = new ArrayList<>(); 

    //the ability to find a station by id
    public Station findStationById(int stationId) {
        for(Station station: stations) {
            if(station.getId() == stationId) {
                return station;
            }
        }
        return null; // station not found
    }

    //the ability to add a new station
    public Station addStation(String name, String city) {
        Station station = new Station(nextId++, name, city);
        stations.add(station);

        return station;
    }

    //the ability to remove a station
    public String removeStation(int stationId) {
        Station stationToRemove = findStationById(stationId);

        if(stationToRemove != null) {
            stations.remove(stationToRemove);
            return "Station removed successfully.";
        }
        return "Station not found.";
    }

    //the ability to modify station details
    public String modifyStation(int stationId, String newName, String newCity) {
        Station station = findStationById(stationId);

        if(station != null) {
            station.setName(newName);
            station.setCity(newCity);
            return "Station details updated successfully.";
        }
        return "Station not found.";
    }
    

    //return all stations
    public List<Station> getAllStations() {
        return stations;
    }
}