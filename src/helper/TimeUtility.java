package helper;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Appointment;
import model.User;
import query.AppointmentQuery;

import java.time.*;
import java.util.ArrayList;

/**
 * Assists the program in converting timezones and validating that a requested appointment time is available.
 */
public class TimeUtility {
//    private static ObservableList<LocalTime> startTimes = FXCollections.observableArrayList();
    private static LocalTime easternStartTime = LocalTime.of(8,0);
    private static LocalTime easternEndTime = LocalTime.of(22,0);

    /**
     *User is unable to select unauthorized appointment times outside of 08:00-22:00 EST.
     * @return This Method returns the available start times in the users specific Locale.
     */
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
    /**
     * The user is unable to select times that are out of the specified times in EST.
     * @return available end times in the users specific Locale.
     */

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

    /**
     *
     * @return If the user that is logged in has any upcoming appointments within 15 minutes.
     */
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

    /**
     *
     * @param datePicked The day the user has selected for an appointment
     * @param customerID The customerID that will be used in the appointment
     * @param desireStart The desired start time for an appointment.
     * @param desireEnd The desired end time for an appointment.
     * @return True if the appointment is available or false if there are appointments already scheduled during the times requested.
     */
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

