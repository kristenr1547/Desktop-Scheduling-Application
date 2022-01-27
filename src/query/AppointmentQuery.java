package query;

import helper.JDBC;
import helper.MonthlyView;
import helper.WeeklyView;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Appointment;
import model.Customer;
import model.User;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;

public class AppointmentQuery {
    private static Connection conn = JDBC.getConnection();
    private static Statement mystmt = null;
    private static PreparedStatement ps = null;
    private static ResultSet result = null;

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
            System.out.println("something went wrong with insert into appointment");
        }
    }

}
