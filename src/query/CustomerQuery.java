package query;

import helper.JDBC;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Customer;
import java.sql.PreparedStatement;
import java.sql.*;

public class CustomerQuery {

    private static Connection conn = null;
    private static Statement mystmt = null;
    private static PreparedStatement ps = null;
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


public static boolean deleteCustomer(Customer c){
        int id = c.getId();
        String sqlDelete = "DELETE FROM customers WHERE Customer_ID = ?";
        try{
            conn = JDBC.getConnection();
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




}
