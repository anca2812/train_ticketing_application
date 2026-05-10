package DataModel;
import java.time.LocalDateTime;

public class Booking {
    private int id;
    private Schedule schedule;
    private Train train;
    private String customerName;
    private String customerEmail;
    private int seatNumber;
    private LocalDateTime bookingTime;

    public Booking(int id, Schedule schedule, String customerName, String customerEmail, int seatNumber) {
        this.id = id;
        this.schedule = schedule;
        this.train = schedule.getTrain();
        this.customerName = customerName;
        this.customerEmail = customerEmail;
        this.seatNumber = seatNumber;
        this.bookingTime = LocalDateTime.now();
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public Schedule getSchedule() { return schedule; }
    public void setSchedule(Schedule schedule) { this.schedule = schedule; }

    public Train getTrain() { return train; }
    public void setTrain(Train train) { this.train = train; }

    public String getCustomerName() { return customerName; }
    public void setCustomerName(String customerName) { this.customerName = customerName; }

    public String getCustomerEmail() { return customerEmail; }
    public void setCustomerEmail(String customerEmail) { this.customerEmail = customerEmail; }

    public int getSeatNumber() { return seatNumber; }
    public void setSeatNumber(int seatNumber) { this.seatNumber = seatNumber; }
    
    public LocalDateTime getBookingTime() { return bookingTime; }
    public void setBookingTime(LocalDateTime bookingTime) { this.bookingTime = bookingTime; }

}