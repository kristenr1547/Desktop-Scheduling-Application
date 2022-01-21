package query;

import helper.JDBC;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Contact;
import model.Customer;

import java.sql.*;

public class ContactQuery {

    private static Connection conn = null;
    private static Statement mystmt = null;
    private static PreparedStatement ps = null;
    private static ResultSet result = null;

    public static ObservableList<Contact> getAllContacts(){
        ObservableList<Contact> allContacts = FXCollections.observableArrayList();
        try {
            conn = JDBC.getConnection();
            mystmt = conn.createStatement();
            result = mystmt.executeQuery("select * from contacts");
            while(result.next()){
                Contact c = new Contact(result.getString("Contact_Name"), result.getInt("Contact_ID"));
                allContacts.add(c);
            }
            return allContacts;
        }catch (SQLException e){
            e.printStackTrace();
            return null;
        }
    }

}
