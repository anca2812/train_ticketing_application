package BusinessLogic;
import DataModel.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class ScheduleService {
    private int nextId = 1;
    private List<Schedule> schedules = new ArrayList<>();

    //the ability to find a schedule by id
    public Schedule findScheduleById(int scheduleId) {
        for(Schedule schedule: schedules) {
            if(schedule.getId() == scheduleId) {
                return schedule;
            }
        }
        return null; // schedule not found
    }

    //the ability to add schedule
    public Schedule addSchedule(Train train, LocalDateTime departureTime, LocalDateTime arrivalTime) {
        Schedule schedule = new Schedule(nextId++, train, departureTime, arrivalTime);
        schedules.add(schedule);
        return schedule;
    }

    //the ability to remove a schedule
    public String removeSchedule(int scheduleId) {
        Schedule scheduleToRemove = findScheduleById(scheduleId);

        if(scheduleToRemove != null) {
            schedules.remove(scheduleToRemove);
            return "Schedule removed successfully.";
        }
        return "Schedule not found.";
    }

    //the ability to modify schedule details
    public String modifySchedule(int scheduleId, Train newTrain, LocalDateTime newDepartureTime, LocalDateTime newArrivalTime) {
        Schedule schedule = findScheduleById(scheduleId);

        if(schedule != null) {
            schedule.setTrain(newTrain);
            schedule.setDepartureTime(newDepartureTime);
            schedule.setArrivalTime(newArrivalTime);
            return "Schedule details updated successfully.";
        }
        return "Schedule not found.";
    }

    //return all schedules
    public List<Schedule> getAllSchedules() {
        return schedules;
    }   

    //find shedules by train
    public List<Schedule> findSchedulesByTrain(Train train) {
        List<Schedule> allSchedules = new ArrayList<>();
        for(Schedule schedule: schedules) {
            if(schedule.getTrain().getId() == train.getId()) {
                allSchedules.add(schedule);
            }
        }

        return allSchedules;
    }

    //find schedules between two stations
    public List<Schedule> findSchedulesBetweenStations(Station departureStation, Station arrivalStation) {
        List<Schedule> allSchedules = new ArrayList<>();
        for(Schedule schedule: schedules) {
            Route route = schedule.getTrain().getRoute();
            if(route.getStations().contains(departureStation) && route.getStations().contains(arrivalStation)) {
                allSchedules.add(schedule);
            }
        }

        return allSchedules;
    }

    //find schedules after a certain time
    public List<Schedule> findSchedulesAfterTime(LocalDateTime time) {
        List<Schedule> allSchedules = new ArrayList<>();
        for(Schedule schedule: schedules) {
            if(schedule.getDepartureTime().isAfter(time)) {
                allSchedules.add(schedule);
            }
        }

        return allSchedules;
    }
}