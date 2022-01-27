package main;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.Parent;
import helper.JDBC;
import model.FirstLevelDiv;
import query.FirstLevelDivQuery;
import java.sql.SQLException;
import java.util.Locale;


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
//        Locale.setDefault(new Locale("fr"));
        launch(args);
        JDBC.closeConnection();




    }
}
