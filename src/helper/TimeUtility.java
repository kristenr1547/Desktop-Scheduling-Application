package helper;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Appointment;
import model.User;
import query.AppointmentQuery;

import java.time.*;
import java.util.ArrayList;

public class TimeUtility {
//    private static ObservableList<LocalTime> startTimes = FXCollections.observableArrayList();
    private static LocalTime easternStartTime = LocalTime.of(8,0);
    private static LocalTime easternEndTime = LocalTime.of(22,0);

    public static ObservableList<LocalTime> getStartTimes(){
        ObservableList<LocalTime> startTimes = FXCollections.observableArrayList();
        if(startTimes.size() == 0){
            ZonedDateTime easternStartZDT = ZonedDateTime.of(LocalDateTime.of(LocalDate.now(), easternStartTime), ZoneId.of("America/New_York"));
            ZonedDateTime easternEndZDT = ZonedDateTime.of(LocalDateTime.of(LocalDate.now(), easternEndTime), ZoneId.of("America/New_York"));
            LocalDateTime usersLocalStart = easternStartZDT.withZoneSameInstant(ZoneId.systemDefault()).toLocalDateTime();
            LocalDateTime usersLocalEnd = easternEndZDT.withZoneSameInstant(ZoneId.systemDefault()).toLocalDateTime();
            while(usersLocalStart.isBefore(usersLocalEnd)){
                startTimes.add(usersLocalStart.toLocalTime());
                usersLocalStart = usersLocalStart.plusMinutes(30);
            }
        }
        return startTimes;
    }


public static ObservableList<LocalTime> getEndTimes(LocalTime userStartSelect){
ObservableList<LocalTime> endTimes = FXCollections.observableArrayList();
    ZonedDateTime easternStartZDT = ZonedDateTime.of(LocalDateTime.of(LocalDate.now(), easternStartTime), ZoneId.of("America/New_York"));
    ZonedDateTime easternEndZDT = ZonedDateTime.of(LocalDateTime.of(LocalDate.now(), easternEndTime), ZoneId.of("America/New_York"));
    LocalDateTime usersLocalStart = easternStartZDT.withZoneSameInstant(ZoneId.systemDefault()).toLocalDateTime();
    LocalDateTime usersLocalEnd = easternEndZDT.withZoneSameInstant(ZoneId.systemDefault()).toLocalDateTime();
    while(userStartSelect.isBefore(LocalTime.from(usersLocalEnd))){
        userStartSelect = userStartSelect.plusMinutes(30);
        endTimes.add(userStartSelect);
    }
    endTimes.add(LocalTime.from(usersLocalEnd));
    return endTimes;
}

public static Appointment checkUserUpcomingAppointments(User user){
        //get user appointments where userID
    ArrayList<Appointment> userAppointments;
    userAppointments = AppointmentQuery.getApptUserLogin(user);
    //get current time to compare
    LocalDateTime loginTime = LocalDateTime.now();
    //loop through results to see if one starts in less than 15 minutes
        for(int i = 0; i < userAppointments.size(); i++){
           Appointment appt = userAppointments.get(i);
           if(appt.getStartTime().isAfter(loginTime) && appt.getStartTime().isBefore(loginTime.plusMinutes(15))){
               return appt;
           }
    }
       return null;
}

public static boolean apptAddVerification(LocalDate datePicked, int customerID, LocalTime desireStart, LocalTime desireEnd){
        ArrayList<Appointment> addAppointmentList;
        addAppointmentList = AppointmentQuery.validateAddAppointment(datePicked,customerID);
        for(int i = 0; i < addAppointmentList.size(); i++){
            Appointment appt = addAppointmentList.get(i);
            LocalTime apptStart = LocalTime.from(appt.getStartTime());//previously added appointment start times
            LocalTime apptEnd = LocalTime.from(appt.getEndTime());//previously added appointment end times
            if(apptStart.isAfter(desireStart) && apptStart.isBefore(desireEnd)){ //if desired start time spans over another appointment
                return false;
            }
            if(apptEnd.equals(desireEnd) || apptStart.equals(desireStart)){ //If the desired start is on the same start as another appointment or the end is equal to another's end
                return false;
            }
            if(apptStart.isBefore(desireStart) && apptEnd.isAfter(desireEnd)){//if desired appointment is inside another appt
                return false;
        }
            if(apptStart.isBefore(desireStart) && apptEnd.isAfter(desireStart)){
                return false;
            }
            if(apptStart.isAfter(desireStart) && apptStart.isBefore(desireEnd)){
                return false;
            }
        }
        return true; //good to book the desired appointment
}


    public static boolean apptUpdateVerification(LocalDate datePicked, int customerID, LocalTime desireStart, LocalTime desireEnd, Appointment appointment){
        ArrayList<Appointment> addAppointmentList;
        addAppointmentList = AppointmentQuery.validateUpdateAppointment(datePicked,customerID,appointment);
        for(int i = 0; i < addAppointmentList.size(); i++){
            Appointment appt = addAppointmentList.get(i);
            LocalTime apptStart = LocalTime.from(appt.getStartTime());//previously added appointment start times
            LocalTime apptEnd = LocalTime.from(appt.getEndTime());//previously added appointment end times
            if(apptStart.isAfter(desireStart) && apptStart.isBefore(desireEnd)){ //if desired start time spans over another appointment
                return false;
            }
            if(apptEnd.equals(desireEnd) || apptStart.equals(desireStart)){ //If the desired start is on the same start as another appointment or the end is equal to another's end
                return false;
            }
            if(apptStart.isBefore(desireStart) && apptEnd.isAfter(desireEnd)){//if desired appointment is inside another appt
                return false;
            }
            if(apptStart.isBefore(desireStart) && apptEnd.isAfter(desireStart)){
                return false;
            }
            if(apptStart.isAfter(desireStart) && apptStart.isBefore(desireEnd)){
                return false;
            }
        }
        return true; //good to book the desired appointment
    }




}

