package BusinessLogic;

import DataModel.*;
import java.util.ArrayList;
import java.util.List;

public class BookingService {
    private int nextId = 1;
    private List<Booking> bookings = new ArrayList<>();

    //the ability to find a booking by id
    public Booking findBookingById(int bookingId) {
        for(Booking booking: bookings) {
            if(booking.getId() == bookingId) {
                return booking;
            }
        }
        return null; // booking not found
    }

    //calculate total seats booked for a schedule
    private int calculateSeatsBooked(Schedule schedule) {
        int seatsBooked = 0;
        for(Booking booking: bookings) {
            if(booking.getSchedule().getId() == schedule.getId()) {
                seatsBooked += booking.getSeatNumber();
            }
        }
        return seatsBooked;
    }

    //calculate booked seats for a train
    public int getBookedSeats(Train train) {
        int booked = 0;
        for (Booking booking : bookings) {
            if (booking.getTrain().getId() == train.getId()) {
                booked += booking.getSeatNumber();
            }
        }
        return booked;
    }

    //calculate available seats for a train
    public int getAvailableSeats(Train train) {
        return train.getTotalSeats() - getBookedSeats(train);
    }

    //the ability to book tickets
    public Booking bookTicket(Schedule schedule, String customerName, String customerEmail, int seats) {
        if (seats <= 0) {
            System.out.println("Invalid number of seats.");
            return null;
        }

        Train train = schedule.getTrain();

        if (getAvailableSeats(train) < seats) {
            System.out.println("Not enough available seats. Available: " + getAvailableSeats(train));
            return null;
        }

        Booking booking = new Booking(nextId++, schedule, customerName, customerEmail, seats);
        bookings.add(booking);
        System.out.println("Booking successful. Booking ID: " + booking.getId());
        return booking;
    }

    //return all bookings
    public List<Booking> getAllBookings() {
        return bookings;
    }

    //find all bookings for a train
    public List<Booking> findBookingsByTrain(Train train) {
        List<Booking> allBookings = new ArrayList<>();
        for(Booking booking: bookings) {
            if(booking.getTrain().getId() == train.getId()) {
                allBookings.add(booking);
            }
        }

        return allBookings;
    }

    //find all bookings of a customer by email
    public List<Booking> findBookingsByCustomerEmail(String email) {
        List<Booking> allBookings = new ArrayList<>();
        for(Booking booking: bookings) {
            if(booking.getCustomerEmail().equalsIgnoreCase(email)) {
                allBookings.add(booking);
            }
        }

        return allBookings;
    }

    //print all bookings details
    public void printBookingForTrain(Train train) {
        List<Booking> bookingsForTrain = findBookingsByTrain(train);
        if(bookingsForTrain.isEmpty()) {
            System.out.println("No bookings found for this train.");
            return;
        }

        System.out.println("Bookings for Train: " + train.getName());
        for(Booking booking: bookingsForTrain) {
            System.out.println(" -> booking ID: " + booking.getId() + 
                                "\n -> customer: " + booking.getCustomerName() + 
                                "\n -> email: " + booking.getCustomerEmail() + 
                                "\n -> seats booked: " + booking.getSeatNumber() +
                                "\n -> booking time: " + booking.getBookingTime() + "\n");
        }
    }
}
