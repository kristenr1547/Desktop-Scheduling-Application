package view_controller;

import helper.TimeUtility;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model.Appointment;
import model.User;
import query.UserQuery;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;
import java.io.*;

public class LoginController implements Initializable {
    ResourceBundle rb;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try{
            rb = ResourceBundle.getBundle("view_controller/resourceFile", Locale.getDefault());
            if(Locale.getDefault().getLanguage().equals("fr")){
                loginButton.setText(rb.getString("LOGINBTN"));
                loginLabel.setText(rb.getString("LOGINLBL"));
                passwordTF.setPromptText(rb.getString("PASSWORD"));
                userNameTF.setPromptText(rb.getString("USERNAME"));
                zoneIDLabel.setText(rb.getString("ZONEID"));

            }
        }catch (MissingResourceException e){
            e.printStackTrace();
        }
        identifiedZonelbl.setText(String.valueOf(Locale.getDefault()) +" | " + ZoneId.systemDefault());
    }

    @FXML
    private Button loginButton;

    @FXML
    private Label loginLabel;

    @FXML
    private TextField passwordTF;

    @FXML
    private TextField userNameTF;

    @FXML
    private Label identifiedZonelbl;

    @FXML
    private Label zoneIDLabel;

    private Parent scene;
    private Stage stage;

    private void alertUser(){
        Alert incorrectLogin = new Alert(Alert.AlertType.WARNING);
        incorrectLogin.setTitle(rb.getString("alertTitle"));
        incorrectLogin.setHeaderText(rb.getString("alertHeader"));
        incorrectLogin.showAndWait();
        incorrectLogin.setGraphic(null);
    }

    @FXML
    void logOn(ActionEvent event) throws IOException {
        Timestamp time = Timestamp.valueOf(LocalDateTime.now());
        String fileName = "login_activity.txt";
        FileWriter fw = new FileWriter(fileName,true);
        PrintWriter pw = new PrintWriter(fw);
        User currentUser = UserQuery.verifyUser(userNameTF.getText(), passwordTF.getText());
        if( currentUser!= null){
                pw.println("Successful login USERNAME ENTERED: " + currentUser.getUserName() + " TimeStamp: " + time);
                pw.close();

            Appointment appointment = TimeUtility.checkUserUpcomingAppointments(currentUser);
            if(appointment!=null){
                LocalTime apptTime = LocalTime.from(appointment.getStartTime());
                LocalDate apptDate = LocalDate.from(appointment.getStartTime());
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setHeaderText(rb.getString("appointmentTitle"));
                alert.setTitle(rb.getString("appointmentTitle"));
                alert.setGraphic(null);

                alert.setContentText(rb.getString("appointmentInFifteen") + "\n" +
                        rb.getString("id") + ": " + appointment.getApptID() + " " +
                        //date is the exact same in french and english.
                        "Date: " + apptDate +  rb.getString("apptTime")+ ": " + apptTime);

                alert.showAndWait();
            }else{
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setHeaderText(rb.getString("appointmentTitle"));
                alert.setTitle(rb.getString("appointmentTitle"));
                alert.setGraphic(null);
                alert.setContentText(rb.getString("noApointment"));
                alert.showAndWait();
            }
            DashboardController.setCurrentuser(currentUser);
            stage = (Stage)((Button)event.getSource()).getScene().getWindow();
            scene = FXMLLoader.load(getClass().getResource("/view_controller/dashboard.fxml"));
            stage.setScene(new Scene(scene));
            stage.show();

        }else{
            alertUser();
            pw.println("Unsuccessful login USERNAME ENTERED: "+userNameTF.getText() + " TimeStamp: " + time);
            pw.close();

        }


    }

}
