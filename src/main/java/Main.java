import java.time.LocalDateTime;
import java.util.List;
import java.util.Scanner;

import BusinessLogic.BookingService;
import BusinessLogic.RouteService;
import BusinessLogic.ScheduleService;
import BusinessLogic.StationService;
import BusinessLogic.TrainService;
import DataModel.Booking;
import DataModel.Route;
import DataModel.Schedule;
import DataModel.Station;
import DataModel.Train;

public class Main {

    private static final StationService stationService = new StationService();
    private static final RouteService routeService = new RouteService();
    private static final TrainService trainService = new TrainService();
    private static final ScheduleService scheduleService = new ScheduleService();
    private static final BookingService bookingService = new BookingService();

    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {

        inputData();
        boolean running = true;

        System.out.println("---------------------------------");
        System.out.println("   TRAIN TICKETING APPLICATION");
        System.out.println("---------------------------------");

        while (running) {
            printMenu();
            String choice = scanner.nextLine();
            switch (choice) {
                case "1":
                    displayStations(); break;

                case "2":
                    searchRoutes(); break;

                case "3":
                    bookTicket(); break;

                case "4":
                    displayBookings(); break;

                case "5":
                    reportTrainDelay(); break;

                case "6":
                    modifyTrain(); break;

                case "7":
                    removeTrain(); break;

                case "8":
                    running = false;
                    System.out.println("\nApplication closed successfully."); break;

                default:
                    System.out.println("\nInvalid option.");
            }
        }
    }

    private static void printMenu() {

        System.out.println("\n---- Menu Train Ticketing Application ----");
        System.out.println("1. Display Stations");
        System.out.println("2. Search Routes");
        System.out.println("3. Book Ticket");
        System.out.println("4. Display All Bookings");
        System.out.println("5. Report Train Delay");
        System.out.println("6. Modify Train");
        System.out.println("7. Remove Train");
        System.out.println("8. Exit");
        System.out.print("Choose option: ");
    }

    private static void inputData() {

        Station s1 = stationService.addStation("Cluj-Napoca", "Cluj");
        Station s2 = stationService.addStation("Bucuresti Nord", "Bucuresti");
        Station s3 = stationService.addStation("Brasov", "Brasov");

        Route r1 = routeService.addRoute("Cluj - Bucuresti", List.of(s1, s2));
        Route r2 = routeService.addRoute("Bucuresti - Brasov", List.of(s2, s3));

        Train t1 = trainService.addTrain("InterCity", r1, 200);
        Train t2 = trainService.addTrain("Regional", r2, 100);

        scheduleService.addSchedule(
                t1,
                LocalDateTime.of(2024, 12, 1, 8, 0),
                LocalDateTime.of(2024, 12, 1, 14, 0)
        );

        scheduleService.addSchedule(
                t2,
                LocalDateTime.of(2024, 12, 1, 10, 0),
                LocalDateTime.of(2024, 12, 1, 16, 0)
        );
    }

    private static void displayStations() {
        System.out.println("\n--- STATIONS ---");

        for (Station station : stationService.getAllStations()) {
            System.out.println( station.getId() + " | " + station.getName() + " | " + station.getCity());
        }
    }

    private static void searchRoutes() {
        try {
            System.out.print("\nDeparture station ID: ");
            int startId = Integer.parseInt(scanner.nextLine());

            System.out.print("Arrival station ID: ");
            int endId = Integer.parseInt(scanner.nextLine());

            Station start = stationService.findStationById(startId);
            Station end = stationService.findStationById(endId);

            List<Schedule> found = scheduleService.findSchedulesBetweenStations(start, end);

            if (found.isEmpty()) {
                System.out.println("\nNo route found.");
            } 
            else {
                System.out.println("\n--- AVAILABLE ROUTES ---");
                for (Schedule schedule : found) {
                    System.out.println(
                            schedule.getTrain().getName()
                                    + " | Departure: "
                                    + schedule.getDepartureTime()
                                    + " | Arrival: "
                                    + schedule.getArrivalTime()
                    );
                }
            }

        } catch (Exception e) {
            System.out.println("\nInvalid input.");
        }
    }

    private static void bookTicket() {
        try {
            System.out.println("\n--- AVAILABLE SCHEDULES ---");
            for (Schedule schedule : scheduleService.getAllSchedules()) {
                System.out.println(
                        schedule.getId()
                                + " | "
                                + schedule.getTrain().getName()
                                + " | "
                                + schedule.getDepartureTime()
                );
            }

            System.out.print("\nSchedule ID: ");
            int scheduleId = Integer.parseInt(scanner.nextLine());

            Schedule selected = scheduleService.findScheduleById(scheduleId);

            System.out.print("Customer name: ");
            String name = scanner.nextLine();

            System.out.print("Customer email: ");
            String email = scanner.nextLine();

            System.out.print("Seats: ");
            int seats = Integer.parseInt(scanner.nextLine());

            Booking booking = bookingService.bookTicket(selected, name, email, seats);

            if (booking == null) {
                System.out.println("\nBooking failed. Train capacity exceeded.");
            } 
            else {
                System.out.println("\nBooking successful!");
                System.out.println("Booking ID: " + booking.getId());
            }

        } catch (Exception e) {
            System.out.println("\nInvalid booking data.");
        }
    }

    private static void displayBookings() {
        System.out.println("\n--- BOOKINGS ---");
        for (Booking booking : bookingService.getAllBookings()) {
            System.out.println(
                    booking.getId()
                            + " | "
                            + booking.getCustomerName()
                            + " | "
                            + booking.getTrain().getName()
                            + " | Seats: "
                            + booking.getSeatNumber()
            );
        }
    }

    private static void reportTrainDelay() {
        try {
            System.out.print("\nTrain ID: ");
            int trainId = Integer.parseInt(scanner.nextLine());

            List<String> affected =
                    trainService.markAsDelayedAndGetCustomers(trainId, bookingService);

            System.out.println("\nTrain marked as delayed.");
            System.out.println("\nAffected customers:");
            for (String email : affected) {
                System.out.println(email);
            }
        } catch (Exception e) {
            System.out.println("\nInvalid train ID.");
        }
    }

    private static void modifyTrain() {
        try {
            System.out.print("\nTrain ID: ");
            int trainId = Integer.parseInt(scanner.nextLine());

            System.out.print("New train name: ");
            String newName = scanner.nextLine();

            Train train = trainService.findTrainById(trainId);

            trainService.modifyTrain(
                    trainId,
                    newName,
                    train.getRoute(),
                    train.getTotalSeats()
            );

            System.out.println("\nTrain updated successfully.");

        } catch (Exception e) {
            System.out.println("\nError updating train.");
        }
    }

    private static void removeTrain() {
        try {
            System.out.print("\nTrain ID: ");
            int trainId = Integer.parseInt(scanner.nextLine());

            trainService.removeTrain(trainId);

            System.out.println("\nTrain removed successfully.");
        } catch (Exception e) {
            System.out.println("\nError removing train.");
        }
    }
}