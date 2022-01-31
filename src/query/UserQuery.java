package query;

import helper.JDBC;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Customer;
import model.User;

import java.sql.*;

public class UserQuery {

    private static Connection conn = JDBC.getConnection();
    private static Statement mystmt = null;
    private static  ResultSet result = null;
    private static PreparedStatement ps = null;

    public static User verifyUser(String username, String password){
        try{
            mystmt = conn.createStatement();
            result = mystmt.executeQuery("select * from users");
            while(result.next()){
                if(result.getString("User_Name").equals(username) && result.getString("Password").equals(password)){
                    User u = new User(result.getInt("User_ID"),
                                      result.getString("User_Name"), result.getString("Password"));
                    return u;
                }
        }

        }catch(Exception e){
            e.printStackTrace();
        } return null;
    }

    public static ObservableList<User> getAllUsers(){
        try{
            ObservableList<User> allUsers = FXCollections.observableArrayList();
            mystmt = conn.createStatement();
            result = mystmt.executeQuery("select * from users");
            while(result.next()){
                    User u = new User(result.getInt("User_ID"), result.getString("User_Name"),
                            result.getString("Password"));
                    allUsers.add(u);
            }
            return allUsers;
        }catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }
    public static User createUserByID(int id){
        int userID = 0;
        String userName = null;
        String userPassword = null;
        try{
            ps = conn.prepareStatement("SELECT * FROM users WHERE User_ID = ?");
            ps.setInt(1, id);
            result = ps.executeQuery();
            if(result.next()){
                userID = result.getInt("User_ID");
                userName = result.getString("User_Name");
                userPassword = result.getString("Password");

            }
            User user = new User(userID,userName,userPassword);
            return user;

        }catch (SQLException e){
            e.printStackTrace();
            return null;
        } }



}
