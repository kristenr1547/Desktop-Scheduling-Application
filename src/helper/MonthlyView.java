package helper;

import java.time.LocalDate;

public interface MonthlyView {
    LocalDate addMonth(LocalDate todaysDate); //creates a month view for appointments
}
