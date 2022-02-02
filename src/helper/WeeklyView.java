package helper;

import java.time.LocalDate;
/**
 * LAMBDA INTERFACE HERE
 *Assists the program in creating a week view from the current LocalDate.
 */
public interface WeeklyView {

    LocalDate addSix(LocalDate todaysDate);//makes a 7 day appointment view
}
