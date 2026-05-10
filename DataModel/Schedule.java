package DataModel;

import java.time.LocalDateTime;

public class Schedule {
    private int id;
    private Train train;
    private LocalDateTime departureTime;
    private LocalDateTime arrivalTime;

    public Schedule(int id, Train train, LocalDateTime departureTime, LocalDateTime arrivalTime) {
        this.id = id;
        this.train = train;
        this.departureTime = departureTime;
        this.arrivalTime = arrivalTime;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; } 

    public Train getTrain() { return train; }
    public void setTrain(Train train) { this.train = train; }

    public LocalDateTime getDepartureTime() { return departureTime; }
    public void setDepartureTime(LocalDateTime departureTime) { this.departureTime = departureTime; }

    public LocalDateTime getArrivalTime() { return arrivalTime; }
    public void setArrivalTime(LocalDateTime arrivalTime) { this.arrivalTime = arrivalTime; }
}