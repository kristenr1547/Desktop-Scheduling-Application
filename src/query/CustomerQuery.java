package query;

import helper.JDBC;
import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Customer;
import model.User;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class CustomerQuery {

    private static Connection conn = null;
    private static Statement mystmt = null;
    private static ResultSet result = null;

    public static ObservableList<Customer> getAllCustomers(){
        ObservableList<Customer> allCustomers = FXCollections.observableArrayList();
        try {
            conn = JDBC.getConnection();
            mystmt = conn.createStatement();
            result = mystmt.executeQuery("select * from customers");
            while(result.next()){
                    Customer c = new Customer(result.getInt("Customer_ID"), result.getString("Customer_Name"), result.getString("Address"), result.getString("Postal_Code"), result.getString("Phone"), result.getInt("Division_ID"));
                    allCustomers.add(c);
                }
            return allCustomers;
        }catch (SQLException e){
            //nothing for now
        }
        return null;
    }




}
