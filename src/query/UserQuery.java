package query;

import helper.JDBC;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.User;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class UserQuery {

    private static Connection conn = null;
    private static Statement mystmt = null;
    private static  ResultSet result = null;

    public static User verifyUser(String username, String password){
        try{
            conn = JDBC.getConnection();
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
            conn = JDBC.getConnection();
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


}
