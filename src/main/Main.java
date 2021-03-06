package main;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.Parent;
import helper.JDBC;
import model.FirstLevelDiv;
import query.AppointmentQuery;
import query.CustomerQuery;
import query.FirstLevelDivQuery;
import view_controller.ReportController;

import java.sql.SQLException;
import java.util.Locale;

/**
 * Class where the main method is located.
 */
public class Main extends Application{
    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/view_controller/login.fxml"));
        primaryStage.setTitle("Appointment System");
        primaryStage.setScene(new Scene(root, 800, 600));
        primaryStage.show();

    }

    public static void main(String[] args) throws SQLException {
        JDBC.openConnection();
        launch(args);
        JDBC.closeConnection();




    }
}
