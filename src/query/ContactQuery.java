package query;

import helper.JDBC;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Contact;
import model.Customer;
import model.FirstLevelDiv;

import java.sql.*;

public class ContactQuery {

    private static Connection conn = JDBC.getConnection();
    private static Statement mystmt = null;
    private static PreparedStatement ps = null;
    private static ResultSet result = null;

    public static ObservableList<Contact> getAllContacts(){
        ObservableList<Contact> allContacts = FXCollections.observableArrayList();
        try {
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
    public static Contact createContactByID(int id){
        int contactID = 0;
        String contactName = null;
        try{
            ps = conn.prepareStatement("SELECT * FROM contacts WHERE Contact_ID = ?");
            ps.setInt(1, id);
            result = ps.executeQuery();
            if(result.next()){
                contactID = result.getInt("Contact_ID");
                contactName = result.getString("Contact_Name");
            }
            Contact contact = new Contact(contactName,contactID);
            return contact;

        }catch (SQLException e){
            System.out.println(" error in create contactByID");
            return null;
        } }


}
