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

    //prepared statements ? for times localdate time to timestamp to timestampof
    //setTimeStamp ?, timestamp value
    //key to keys
    //15-30
    //change vm to us time zones est / pst
}
