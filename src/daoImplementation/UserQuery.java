package daoImplementation;

import jdbcHelper.JDBC;
import model.User;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class UserQuery {

    public static Connection conn = null;
    public static Statement mystmt = null;
   public static  ResultSet result = null;

    public static User verifyUser(String username, String password){
        try{
            conn = JDBC.getConnection();
            mystmt = conn.createStatement();
            result = mystmt.executeQuery("select * from users");
            while(result.next()){
                if(result.getString("User_Name").equals(username) && result.getString("Password").equals(password)){
                    User u = new User(result.getInt("User_ID"), result.getString("User_Name"), result.getString("Password"));
                    System.out.println(result.getString("User_Name"));
                    System.out.println("you are logged in");
                    return u;
                }
        }

        }catch(Exception e){
            //do nothing here

        } return null;
    }

}
