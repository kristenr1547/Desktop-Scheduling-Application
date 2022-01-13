package main;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.Parent;
import jdbcHelper.JDBC;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;


public class Main extends Application{
    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/view_controller/login.fxml"));
        primaryStage.setTitle("Appointment System");
        primaryStage.setScene(new Scene(root, 800, 600));
        primaryStage.show();

    }
    public static void main(String[] args) throws SQLException {
	// write your code here
        JDBC.openConnection();
        Connection conn = JDBC.getConnection();
        Statement mystmt = conn.createStatement();
        ResultSet result = mystmt.executeQuery("select * from users");
        while(result.next()){
            if(result.getString("User_Name").equals("test")){
                System.out.println(result.getString("User_Name"));
                System.out.println("you are logged in");
                break;
            }
        }

//        Locale.setDefault(new Locale("fr"));
        launch(args);

        JDBC.closeConnection();



    }
}
