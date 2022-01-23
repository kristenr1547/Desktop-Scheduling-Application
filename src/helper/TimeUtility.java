package helper;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.time.*;

public class TimeUtility {
    private static ObservableList<LocalTime> startTimes = FXCollections.observableArrayList();
//    private static ObservableList<LocalTime> endTimes = FXCollections.observableArrayList();
    private static LocalTime easternStartTime = LocalTime.of(8,0);
    private static LocalTime easternEndTime = LocalTime.of(22,0);

    public static ObservableList<LocalTime> getStartTimes(){
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
    return endTimes;
}
}
//endtimes.add(usersLoalStart.toLocalTime());
//need another method the same way and call it getEndtimes always call getStartTime first
//localdate out of the picker and time from combobox need to be of local