package BusinessLogic;
import DataModel.*;
import java.util.ArrayList;
import java.util.List;

public class TrainService {
    private int nextId = 1;
    private List<Train> trains = new ArrayList<>();

    //the ability to add a new train
    public Train addTrain(String name, Route route, int totalSeats) {
        Train train = new Train(nextId++, name, route, totalSeats);
        trains.add(train);

        return train;
    }

    //the ability to find a train by id
    public Train findTrainById(int trainId) {
        for(Train train: trains) {
            if(train.getId() == trainId) {
                return train;
            }
        }
        return null; // train not found
    }

    //the ability to remove a train
    public String removeTrain(int trainId) {
        Train trainToRemove = findTrainById(trainId);

        if(trainToRemove != null) {
            trains.remove(trainToRemove);
            return "Train removed successfully.";
        }
        return "Train not found.";
    }

    //the ability to modify train details
    public String modifyTrain(int trainId, String newName, Route newRoute, int newTotalSeats) {
        Train train = findTrainById(trainId);
        
        if(train != null) {
            train.setName(newName);
            train.setRoute(newRoute);
            train.setTotalSeats(newTotalSeats);
            return "Train details updated successfully.";
        }
        return "Train not found.";
    }

    //return all trains
    public List<Train> getAllTrains() {
        return trains;
    }

    //the ability to find trains by route 
    public List<Train> findTrainsByRoute(Route route) {
        List<Train> allTrains = new ArrayList<>();
        for(Train train: trains) {
            if(train.getRoute().getId() == route.getId()) {
                allTrains.add(train);
            }
        }

        return allTrains;
    }

    //mark a train as delayed
    public boolean markTrainAsDelayed(int trainId) {
        Train train = findTrainById(trainId);

        if(train != null) {
            train.setDelayed(true);
            return true;
        }
        return false;
    }

    //return all delayed trains
    public List<Train> getDelayedTrains() {
        List<Train> delayedTrains = new ArrayList<>();
        for(Train train: trains) {
            if(train.isDelayed()) {
                delayedTrains.add(train);
            }
        }
        return delayedTrains;
    }

    //mark a train as delayed
    public List<String> markAsDelayedAndGetCustomers(int trainId, BookingService bookingService) {
    Train train = findTrainById(trainId);
    List<String> emails = new ArrayList<>();

    if (train == null) {
        System.out.println("Train with ID " + trainId + " not found.");
        return emails;
    }

    train.setDelayed(true);
    System.out.println("Train " + train.getName() + " has been marked as delayed.");

    //find all customers with bookings on this train
    List<Booking> bookings = bookingService.findBookingsByTrain(train);
    for (Booking b : bookings) {
        emails.add(b.getCustomerEmail());
        System.out.println("Affected customer: " + b.getCustomerName() + ", email: " + b.getCustomerEmail());
    }

    return emails;
}
}