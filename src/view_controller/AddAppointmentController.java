package view_controller;

import helper.TimeUtility;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import model.Contact;
import model.Customer;
import model.User;
import query.AppointmentQuery;
import query.ContactQuery;
import query.CustomerQuery;
import query.UserQuery;
import java.io.IOException;
import java.net.URL;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Optional;
import java.util.ResourceBundle;
/**
 * Class that creates an appointment based on what the user selects and enters into the text fields. Validates to ensure that there are no conflicts for a customer's schedule.
 */

public class AddAppointmentController implements Initializable {
    /**
     *Initializes the combo boxes for the user to select the specified options to create an appointment.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        customerIDCombo.setItems(CustomerQuery.getAllCustomers());
        userIDCombo.setItems(UserQuery.getAllUsers());
        contactCombo.setItems(ContactQuery.getAllContacts());
        customerIDTF.setText("AUTO-GEN DISABLED");
        customerIDTF.setDisable(true);
        //8AM-10PM EASTERN BUT THE TIMES ARE SUPPOSTED TO BE LOCAL
        //DON'T HARDCODE LIST HERE CALL A METHOD THAT CREATES AN OBSERVABLELIST BASED ON LOCAL TIME OF USER
        startTimeCombo.setItems(TimeUtility.getStartTimes());

    }

    /**
     * Start times that are displayed in the users local time zone the options available do not allow customer to select times outside of the specified EST times allowed.
     */
    @FXML
    private ComboBox<LocalTime> startTimeCombo;

    /**
     * End times that are displayed in the users local time zone the options available do not allow customer to select times outside of the specified EST times allowed.
     */
    @FXML
    private ComboBox<LocalTime> endTimeCombo;
    /**
     * Displays all contacts available to be added to the appointment.
     */
    @FXML
    private ComboBox<Contact> contactCombo;
    /**
     * All available customers to be added to the appointment.
     */
    @FXML
    private ComboBox<Customer> customerIDCombo;
    /**
     * Displays all users available to add to the appointment.
     */
    @FXML
    private ComboBox<User> userIDCombo;

    private Stage stage;
    private Parent scene;

    @FXML
    private TextField customerIDTF;
    @FXML
    private TextField descriptionTF;
    @FXML
    private TextField locationTF;
    @FXML
    private TextField titleTF;
    @FXML
    private TextField typeTF;
    @FXML
    private DatePicker datePicker;


    @FXML
    private Label contactErrorLbl;
    @FXML
    private Label typeErrorLbl;
    @FXML
    private Label userIDErrorLbl;
    @FXML
    private Label titleErrorLbl;
    @FXML
    private Label startTimeEmptyErrorLbl;
    @FXML
    private Label descriptionErrorLbl;
    @FXML
    private Label customerIDErrorLbl;
    @FXML
    private Label endTimeEmptyErrorLbl;
    @FXML
    private Label dateEmptyErrorLbl;
    @FXML
    private Label locationErrorLbl;
    @FXML
    private Label apptTimeLabel;

    /**
     * Clears all error messages so the user is not confused if an error remains.
     */
    private void clearErrorLabels(){
        locationErrorLbl.setVisible(false);
        dateEmptyErrorLbl.setVisible(false);
        endTimeEmptyErrorLbl.setVisible(false);
        customerIDErrorLbl.setVisible(false);
        descriptionErrorLbl.setVisible(false);
        startTimeEmptyErrorLbl.setVisible(false);
        titleErrorLbl.setVisible(false);
        userIDErrorLbl.setVisible(false);
        typeErrorLbl.setVisible(false);
        contactErrorLbl.setVisible(false);
        apptTimeLabel.setVisible(false);
    }

    /**
     *
     * @param event Start time is selected in the users local time. It creates a list of available end times for the end time combo box.
     */
    @FXML
    void onStartTimeSelection(ActionEvent event) {
        LocalTime selectedTime = (LocalTime)startTimeCombo.getSelectionModel().getSelectedItem();
        if(selectedTime != null){
            endTimeCombo.setItems(TimeUtility.getEndTimes(selectedTime));
        }
    }

    /**
     *
     * @param event User selects the cancel button.
     * Allows the user to stay on the page if they do not want to lose all of their information.
     * @throws IOException if main dash file is not accessible.
     */
    @FXML
    void onCancel(ActionEvent event) throws IOException {
        Alert cancelAlert = new Alert(Alert.AlertType.WARNING);
        cancelAlert.setTitle("Are you sure you want to go back?");
        cancelAlert.setHeaderText("Data may be lost.");
        cancelAlert.setContentText("Are you sure you want to go back to main dashboard? All data will be lost.");
        Optional<ButtonType> result = cancelAlert.showAndWait();
        if(result.isPresent() && result.get() == ButtonType.OK){
            stage = (Stage)((Button)event.getSource()).getScene().getWindow();
            scene = FXMLLoader.load(getClass().getResource("/view_controller/dashboard.fxml"));
            stage.setScene(new Scene(scene));
            stage.show();
        }
    }

    /**
     *
     * Inserts an appointment into the database if there are no errors found.
     * @param event User selects the save button.
     * @throws IOException if unable to go back to dashboard.
     */
    @FXML
    void onSave(ActionEvent event) throws IOException {
        clearErrorLabels();
        Contact contact = null;
        int contactID = -1;
        Customer customer = null;
        int customerID = -1;
        User user = null;
        int userID = -1;
        boolean errorFound = false;
        LocalDate lclDate = datePicker.getValue();
        String description = descriptionTF.getText();
        String location = locationTF.getText();
        String type = typeTF.getText();
        String title = titleTF.getText();
        LocalTime lclStart= null;
        LocalTime lclEnd=null;
        LocalDateTime lclDateTimeStart= null;
        LocalDateTime lclDateTimeEnd= null;




        try{
            lclStart=(LocalTime)startTimeCombo.getSelectionModel().getSelectedItem();
        }catch (NullPointerException e){
            startTimeEmptyErrorLbl.setVisible(true);
            errorFound = true;
        }
        try{
            lclEnd=(LocalTime) endTimeCombo.getSelectionModel().getSelectedItem();
        }catch (NullPointerException e){
            endTimeEmptyErrorLbl.setVisible(true);
            errorFound = true;
        }

      try{
          contact = contactCombo.getSelectionModel().getSelectedItem();
          contactID = contact.getContactID();
      }catch (NullPointerException e){
          contactErrorLbl.setVisible(true);
          errorFound = true;
      }
      try{
            customer = customerIDCombo.getSelectionModel().getSelectedItem();
            customerID = customer.getId();
      }catch (NullPointerException e){
            customerIDErrorLbl.setVisible(true);
            errorFound = true;
        }
      try{
            user = userIDCombo.getSelectionModel().getSelectedItem();
            userID = user.getUserId();
      }catch (NullPointerException e){
            userIDErrorLbl.setVisible(true);
            errorFound = true;
        }
        try{
            lclDate = datePicker.getValue();
        }catch (NullPointerException e){
            dateEmptyErrorLbl.setVisible(true);
            errorFound = true;
        }

        if(title.isEmpty()){
            errorFound = true;
            titleErrorLbl.setVisible(true);
        }
        if(type.isEmpty()){
            typeErrorLbl.setVisible(true);
            errorFound = true;
        }
        if(location.isEmpty()){
            locationErrorLbl.setVisible(true);
            errorFound = true;
        }
        if(description.isEmpty()){
            descriptionErrorLbl.setVisible(true);
            errorFound = true;
        }
        if(lclDate == null){
            dateEmptyErrorLbl.setVisible(true);
            errorFound = true;
        }
        if(lclDate != null && lclEnd != null && lclStart != null){
            if(TimeUtility.apptAddVerification(lclDate, customerID, lclEnd,lclStart)){
                //this branch is desired
            }else{
                apptTimeLabel.setVisible(true);
                errorFound = true;
            }
        }

        if(!errorFound){
            //combines date picker with the times selected if there are no errors selected
            lclDateTimeStart = LocalDateTime.of(lclDate,lclStart);
            lclDateTimeEnd = LocalDateTime.of(lclDate,lclEnd);
            Timestamp timeStampStart = Timestamp.valueOf(lclDateTimeStart);
            Timestamp timeStampEnd = Timestamp.valueOf(lclDateTimeEnd);
            AppointmentQuery.insertAppt(title,description,location,type,timeStampStart,timeStampEnd,customerID,userID,contactID);
            stage = (Stage)((Button)event.getSource()).getScene().getWindow();
            scene = FXMLLoader.load(getClass().getResource("/view_controller/dashboard.fxml"));
            stage.setScene(new Scene(scene));
            stage.show();
        }

    }


}
