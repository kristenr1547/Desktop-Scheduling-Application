package helper;

import java.time.LocalDate;

/**
 * LAMBDA INTERFACE HERE
 *Assists the program in creating a month view from the current LocalDate.
 */
public interface MonthlyView {
    LocalDate addMonth(LocalDate todaysDate); //creates a month view for appointments
}
