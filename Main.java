import BusinessLogic.*;
import DataModel.*;
import java.time.LocalDateTime;
import java.util.List;

public class Main {
     public static void main(String[] args) {

        StationService stationService = new StationService();
        RouteService routeService = new RouteService();
        TrainService trainService = new TrainService();
        ScheduleService scheduleService = new ScheduleService();
        BookingService bookingService = new BookingService();

        Station s1 = stationService.addStation("Cluj-Napoca", "Cluj");
        Station s2 = stationService.addStation("Bucuresti Nord", "Bucuresti");
        Station s3 = stationService.addStation("Brasov", "Brasov");
        Station s4 = stationService.addStation("Sinaia", "Sinaia");

        System.out.println("\nAll stations:");
        for(Station station: stationService.getAllStations()) {
            System.out.println(station.getId() + ": " + station.getName() + " in " + station.getCity());
        }

        Route r1 = routeService.addRoute("Cluj - Bucuresti", List.of(s1, s2));
        System.out.println("\nRoute added: " + r1.getName());

        Route r2 = routeService.addRoute("Bucuresti - Brasov", List.of(s2, s3));
        System.out.println("Route added: " + r2.getName());

        Train t1 = trainService.addTrain("InterCity", r1, 200);
        Train t2 = trainService.addTrain("Regional", r1, 100);

        System.out.println("\nTrains added:");
        for(Train train: trainService.getAllTrains()) {
            System.out.println(train.getId() + ": " + train.getName() + " on route " + train.getRoute().getName());
        }

        Schedule schedule1 = scheduleService.addSchedule(t1, LocalDateTime.of(2024, 12, 1, 8, 0), LocalDateTime.of(2024, 12, 1, 14, 0));
        Schedule schedule2 = scheduleService.addSchedule(t2, LocalDateTime.of(2024, 12, 1, 10, 0), LocalDateTime.of(2024, 12, 1, 16, 0));

        System.out.println("\nSchedules added:");
        for(Schedule schedule: scheduleService.getAllSchedules()) {
            System.out.println(schedule.getId() + ": Train " + schedule.getTrain().getName() + " departs at " + schedule.getDepartureTime() + " and arrives at " + schedule.getArrivalTime());
        }

        System.out.println("\n");
        Booking b1 = bookingService.bookTicket(schedule1, "Ion Popescu", "ion@gmail.com", 2);
        Booking b2 = bookingService.bookTicket(schedule2, "Maria Ion", "maria@gmail.com", 999);
        Booking b3 = bookingService.bookTicket(schedule1, "Andrei Popa", "andrei@gmail.com", 3);

        System.out.println("\nBookings:");
        for(Booking booking: bookingService.getAllBookings()) {
            System.out.println(booking.getId() + ": " + booking.getCustomerName() + " booked " + booking.getSeatNumber() + " seats on train " + booking.getTrain().getName());
        }

        System.out.println("\nSchedules for train " + t1.getName() + ":");
        List<Schedule> found = scheduleService.findSchedulesBetweenStations(s1, s2);
        for(Schedule schedule: found) {
            System.out.println(schedule.getId() + ": " + schedule.getTrain().getName() + " departs at " + schedule.getDepartureTime() + " and arrives at " + schedule.getArrivalTime());
        }

    }
}