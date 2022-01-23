package query;

import helper.JDBC;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Appointment;
import model.Customer;

import java.sql.*;

public class AppointmentQuery {
    private static Connection conn = null;
    private static Statement mystmt = null;
    private static PreparedStatement ps = null;
    private static ResultSet result = null;

    public static ObservableList<Appointment> getAllAppointments(){
        ObservableList<Appointment> allAppointments = FXCollections.observableArrayList();
        try {
            conn = JDBC.getConnection();
            mystmt = conn.createStatement();
            result = mystmt.executeQuery("select * from appointments");
            while(result.next()){
                Appointment a = new Appointment(result.getInt("Appointment_ID"), result.getString("Title"), result.getString("Description"), result.getString("Location"), result.getString("Type"), result.getTimestamp("Start").toLocalDateTime(),result.getTimestamp("End").toLocalDateTime(), result.getInt("Customer_ID"), result.getInt("Contact_ID"),result.getInt("User_ID"));
                allAppointments.add(a);
            }
            return allAppointments;
        }catch (SQLException e){
            //nothing for now
            e.printStackTrace();
        }
        return null;
    }

    public static void insertCustomer(String title, String description, String location, String type,
                                      Timestamp start, Timestamp end,int customerID, int userID, int contactID){

        String sqlInsert = "INSERT INTO appointments(Title, Description,Location,Type,Start,End,Customer_ID,User_ID,Contact_ID)"
                             + "VALUES(?,?,?,?,?,?,?,?,?)";
        try{
            conn = JDBC.getConnection();
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

    //prepared statements ? for times localdate time to timestamp to timestampof
    //setTimeStamp ?, timestamp value
    //key to keys
    //15-30
    //change vm to us time zones est / pst
}
