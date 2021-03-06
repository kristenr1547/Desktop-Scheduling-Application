package query;

import helper.JDBC;
import helper.MonthlyView;
import helper.WeeklyView;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Appointment;
import model.User;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;

/**
 * Class that queries the appointment table in the database
 */
public class AppointmentQuery {
    private static Connection conn = JDBC.getConnection();
    private static Statement mystmt = null;
    private static PreparedStatement ps = null;
    private static ResultSet result = null;

    /**
     *
     * @return all appointments that are scheduled
     */
    public static ObservableList<Appointment> getAllAppointments(){
        ObservableList<Appointment> allAppointments = FXCollections.observableArrayList();
        try {
            mystmt = conn.createStatement();
            result = mystmt.executeQuery("select * from appointments");
            while(result.next()){
                Appointment a = new Appointment(result.getInt("Appointment_ID"), result.getString("Title"),
                        result.getString("Description"), result.getString("Location"), result.getString("Type"),
                        result.getTimestamp("Start").toLocalDateTime(),result.getTimestamp("End").toLocalDateTime(),
                        result.getInt("Customer_ID"), result.getInt("Contact_ID"),result.getInt("User_ID"));
                allAppointments.add(a);
            }
            return allAppointments;
        }catch (SQLException e){
            e.printStackTrace();
        }
        return null;
    }
    /**
     * LAMBDA FUNCTION HERE--it adds a month to the users current day and then returns a list of all appointments within the month.
     * @return all appointments that are scheduled for the month from the current day.
     */

    public static ObservableList<Appointment> getAllAppointmentsMonthly(){
        ObservableList<Appointment> allAppointmentsMonth = FXCollections.observableArrayList();
        try {
            MonthlyView monthView = m -> m.plusMonths(1);
            LocalDate lcldate = LocalDate.now();
            LocalDate monthAway = monthView.addMonth(lcldate);
            String sql = "select * from appointments WHERE CAST(Start AS DATE) >= ? AND CAST(Start AS DATE) <= ?";
            ps = conn.prepareStatement(sql);
            ps.setDate(1, Date.valueOf(lcldate));
            ps.setDate(2, Date.valueOf(monthAway));
            result = ps.executeQuery();
            while(result.next()){
                Appointment a = new Appointment(result.getInt("Appointment_ID"), result.getString("Title"),
                        result.getString("Description"), result.getString("Location"),
                        result.getString("Type"), result.getTimestamp("Start").toLocalDateTime(),
                        result.getTimestamp("End").toLocalDateTime(), result.getInt("Customer_ID"),
                        result.getInt("Contact_ID"),result.getInt("User_ID"));
                allAppointmentsMonth.add(a);
            }
            return allAppointmentsMonth;
        }catch (SQLException e){
            e.printStackTrace();
        }
        return null;
    }
    /**
     * LAMBDA FUNCTION HERE--it adds a month to the users current day and then returns a list of all appointments within the week.
     * @return all appointments that are scheduled for the week from the current day.
     */
    public static ObservableList<Appointment> getAllAppointmentsWeek(){
        ObservableList<Appointment> allAppointmentsWeekly = FXCollections.observableArrayList();
        try {
            WeeklyView weekView = n -> n.plusDays(6);
            LocalDate lcldate = LocalDate.now();
            LocalDate weekOut = weekView.addSix(lcldate);
            String sql = "select * from appointments WHERE CAST(Start AS DATE) >= ? AND CAST(Start AS DATE) <= ?";
            ps = conn.prepareStatement(sql);
            ps.setDate(1, Date.valueOf(lcldate));
            ps.setDate(2, Date.valueOf(weekOut));
            result = ps.executeQuery();
            while(result.next()){
                Appointment a = new Appointment(result.getInt("Appointment_ID"), result.getString("Title"),
                        result.getString("Description"),result.getString("Location"), result.getString("Type"),
                        result.getTimestamp("Start").toLocalDateTime(), result.getTimestamp("End").toLocalDateTime(),
                        result.getInt("Customer_ID"), result.getInt("Contact_ID"),result.getInt("User_ID"));
                allAppointmentsWeekly.add(a);
            }
            return allAppointmentsWeekly;
        }catch (SQLException e){
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Inserts an appointment into the database from the GUI
     */

    public static void insertAppt(String title, String description, String location, String type,
                                      Timestamp start, Timestamp end,int customerID, int userID, int contactID){

        String sqlInsert = "INSERT INTO appointments(Title, Description,Location,Type,Start,End,Customer_ID,User_ID,Contact_ID)"
                             + "VALUES(?,?,?,?,?,?,?,?,?)";
        try{
            ps = conn.prepareStatement(sqlInsert);
            ps.setString(1, title);
            ps.setString(2, description);
            ps.setString(3, location);
            ps.setString(4, type);
            ps.setTimestamp(5,start);
            ps.setTimestamp(6,end);
            ps.setInt(7,customerID);
            ps.setInt(8,userID);
            ps.setInt(9,contactID);
            ps.executeUpdate();

        }catch (SQLException e){
            //
            System.out.println("something went wrong with insert into appointment");
        }
    }

    public static ArrayList<Appointment> getApptUserLogin(User user){
        //arraylist because i am not going to display in a table or combobox
        ArrayList<Appointment> allAppointmentsOnDate = new ArrayList<>();
        int userID = user.getUserId();
        try {
            String sql = "select * from appointments WHERE User_ID = ?";
            ps = conn.prepareStatement(sql);
            ps.setInt(1, userID);
            result = ps.executeQuery();
            while(result.next()){
                Appointment a = new Appointment(result.getInt("Appointment_ID"), result.getString("Title"), result.getString("Description"), result.getString("Location"), result.getString("Type"), result.getTimestamp("Start").toLocalDateTime(),result.getTimestamp("End").toLocalDateTime(), result.getInt("Customer_ID"), result.getInt("Contact_ID"),result.getInt("User_ID"));
                allAppointmentsOnDate.add(a);
            }
            return allAppointmentsOnDate;
        }catch (SQLException e){
            e.printStackTrace();
        }
        return null;
    }
    /**
     * @return an arraylist that will be sent to be validated to ensure that a customer can't have appointments that overlap.
     */

    public static ArrayList<Appointment> validateAddAppointment(LocalDate datePicked, int customerID){
        //arraylist because i am not going to display in a table or combobox
        ArrayList<Appointment> allAppointmentsOnDate = new ArrayList<>();
        try {
            String sql = "SELECT * FROM client_schedule.appointments WHERE CAST(Start AS DATE) = ? AND Customer_ID = ?";
            ps = conn.prepareStatement(sql);
            ps.setDate(1, Date.valueOf(datePicked));
            ps.setInt(2,customerID);
            result = ps.executeQuery();
            while(result.next()){
                Appointment a = new Appointment(result.getInt("Appointment_ID"), result.getString("Title"),
                        result.getString("Description"), result.getString("Location"),
                        result.getString("Type"), result.getTimestamp("Start").toLocalDateTime(),
                        result.getTimestamp("End").toLocalDateTime(), result.getInt("Customer_ID"),
                        result.getInt("Contact_ID"),result.getInt("User_ID"));
                allAppointmentsOnDate.add(a);
            }
            return allAppointmentsOnDate;
        }catch (SQLException e){
            e.printStackTrace();
        }
        return null;
    }

    /**
     *
     * @param a The appointment desired to be deleted
     * @return true if the appointment was deleted successfully.
     */
    public static boolean deleteAppointment(Appointment a){
        int id = a.getApptID();
        String sqlDelete = "DELETE FROM appointments WHERE Appointment_ID = ?";
        try{
            ps = conn.prepareStatement(sqlDelete);
            ps.setInt(1, id);
            int confirmDeleted = ps.executeUpdate();
            if(confirmDeleted < 1){
                return false;
            }else{
                return true;
            }
        }catch (SQLException e){
            //
        }
        return false;
    }
    /**
     * @return an arraylist that will be sent to be validated to ensure that a customer can't have appointments that overlap.
     */
    public static ArrayList<Appointment> validateUpdateAppointment(LocalDate datePicked, int customerID, Appointment apptUpdated){
        //arraylist because i am not going to display in a table or combobox
        ArrayList<Appointment> allAppointmentsOnDate = new ArrayList<>();
        try {
            String sql = "SELECT * FROM client_schedule.appointments WHERE CAST(Start AS DATE) = ? AND Customer_ID = ?";
            ps = conn.prepareStatement(sql);
            ps.setDate(1, Date.valueOf(datePicked));
            ps.setInt(2,customerID);
            result = ps.executeQuery();
            while(result.next()){
                Appointment a = new Appointment(result.getInt("Appointment_ID"), result.getString("Title"),
                        result.getString("Description"), result.getString("Location"), result.getString("Type"),
                        result.getTimestamp("Start").toLocalDateTime(),result.getTimestamp("End").toLocalDateTime(),
                        result.getInt("Customer_ID"), result.getInt("Contact_ID"),result.getInt("User_ID"));
                if(apptUpdated.getApptID() != a.getApptID())
                    allAppointmentsOnDate.add(a);
            }
            return allAppointmentsOnDate;
        }catch (SQLException e){
            e.printStackTrace();
        }
        return null;
    }

    /**
     *
     * @param title Updated appointment title.
     * @param description Updated appointment description.
     * @param location Updated appointment location.
     * @param type Updated appointment type.
     * @param start Updated appointment start.
     * @param end Updated appointment end.
     * @param customerID Updated appointment customer information.
     * @param userID Updated appointment user information.
     * @param contactID Updated ccontact information.
     */
    public static void updateAppt(String title, String description, String location, String type,
                                  Timestamp start, Timestamp end,int customerID, int userID, int contactID, int apptID){

        String sqlUpdate = "UPDATE appointments SET Title = ?, Description = ?,Location = ?,Type = ?,Start = ?,End = ?," +
                "Customer_ID = ?,User_ID = ?,Contact_ID = ? WHERE Appointment_ID = ?";
        try{
            ps = conn.prepareStatement(sqlUpdate);
            ps.setString(1, title);
            ps.setString(2, description);
            ps.setString(3, location);
            ps.setString(4, type);
            ps.setTimestamp(5,start);
            ps.setTimestamp(6,end);
            ps.setInt(7,customerID);
            ps.setInt(8,userID);
            ps.setInt(9,contactID);
            ps.setInt(10, apptID);
            ps.executeUpdate();

        }catch (SQLException e){
            //
            e.printStackTrace();
            System.out.println("something went wrong with insert into appointment");
        }
    }

    /**
     *
     * @param customerID Customer desired to pull information report.
     * @return List that contains how many appointments of each type a customer has.
     */
    public static ObservableList<String>  returnTypeReport(int customerID){
        ObservableList<String> typeReport = FXCollections.observableArrayList();
        try {
            String sql = "SELECT COUNT(Appointment_ID), " +
                    "Type FROM appointments WHERE Customer_ID = ? GROUP BY Type";
            ps = conn.prepareStatement(sql);
            ps.setInt(1,customerID);
            result = ps.executeQuery();
            while(result.next()){
                String type=result.getString("Type");
                int numOfType=result.getInt(1);
                String statistic = type + ": " + numOfType;
                typeReport.add(statistic);
            }
            return typeReport;
        }catch (SQLException e){
            e.printStackTrace();
        }
        return null;
    }

    /**
     *
     * @param contactID Desired contact to return a schedule.
     * @return List that is ordered for the contact's schedule.
     */
    public static ObservableList<Appointment>  contactSchedule(int contactID){
        ObservableList<Appointment> contactSchedule = FXCollections.observableArrayList();
        try {
            String sql = "SELECT *" +
                    "FROM appointments WHERE Contact_ID = ? " +
                    "ORDER BY Start";
            ps = conn.prepareStatement(sql);
            ps.setInt(1,contactID);
            result = ps.executeQuery();
            while(result.next()){
                Appointment a = new Appointment(result.getInt("Appointment_ID"), result.getString("Title"),
                        result.getString("Description"), result.getString("Location"), result.getString("Type"),
                        result.getTimestamp("Start").toLocalDateTime(),result.getTimestamp("End").toLocalDateTime(),
                        result.getInt("Customer_ID"), result.getInt("Contact_ID"),result.getInt("User_ID"));
                contactSchedule.add(a);
            }
            return contactSchedule;
        }catch (SQLException e){
            e.printStackTrace();
        }
        return null;
    }
    /**
     *
     * @param customerID Customer desired to pull information report.
     * @return List that contains how many appointments of each month a customer has.
     */
    public static ObservableList<String>  returnMonthReport(int customerID){
        ObservableList<String> monthReport = FXCollections.observableArrayList();
        try {
            String sql = "SELECT COUNT(Appointment_ID), month(Start) " +
                    "FROM appointments WHERE Customer_ID = ? GROUP BY month(Start) ORDER BY month(Start)";
            ps = conn.prepareStatement(sql);
            ps.setInt(1,customerID);
            result = ps.executeQuery();
            while(result.next()){
                int monthCount=result.getInt(1);
                int monthNum=result.getInt(2);
                String monthName = "";
                switch (monthNum){
                    case 1:
                        monthName = "January";
                        break;
                    case 2:
                        monthName = "February";
                        break;
                    case 3:
                        monthName = "March";
                        break;
                    case 4:
                        monthName = "April";
                        break;
                    case 5:
                        monthName = "May";
                        break;
                    case 6:
                        monthName = "June";
                        break;
                    case 7:
                        monthName = "July";
                        break;
                    case 8:
                        monthName = "August";
                        break;
                    case 9:
                        monthName = "September";
                        break;
                    case 10:
                        monthName = "October";
                        break;
                    case 11:
                        monthName = "November";
                        break;
                    case 12:
                        monthName = "December";
                        break;
                }
                String statistic = monthName + ": " + monthCount;
                monthReport.add(statistic);
            }
            return monthReport;
        }catch (SQLException e){
            e.printStackTrace();
        }
        return null;
    }

}
