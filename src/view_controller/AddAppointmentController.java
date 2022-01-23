package view_controller;

import helper.TimeUtility;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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

public class AddAppointmentController implements Initializable {

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


    @FXML
    private ComboBox<LocalTime> startTimeCombo;
    @FXML
    private ComboBox<LocalTime> endTimeCombo;
    @FXML
    private ComboBox<Contact> contactCombo;
    @FXML
    private ComboBox<Customer> customerIDCombo;
    @FXML
    private ComboBox<User> userIDCombo;

    private Stage stage;
    private Parent scene;

    @FXML
    private Button saveBtn;
    @FXML
    private Button cancelBtn;


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
    }


    @FXML
    void onContactSelection(ActionEvent event) {

    }

    @FXML
    void onCustomerIDSelection(ActionEvent event) {

    }

    @FXML
    void onDateSelection(ActionEvent event) {

    }

    @FXML
    void onEndTimeSelection(ActionEvent event) {

    }


    @FXML
    void onStartTimeSelection(ActionEvent event) {
        LocalTime selectedTime = (LocalTime)startTimeCombo.getSelectionModel().getSelectedItem();
        if(selectedTime != null){
            endTimeCombo.setItems(TimeUtility.getEndTimes(selectedTime));
        }
    }

    @FXML
    void onUserIDSelection(ActionEvent event) {

    }

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

        if(!errorFound){
            //combines date picker with the times selected if there are no nulls selected
            lclDateTimeStart = LocalDateTime.of(lclDate,lclStart);
            lclDateTimeEnd = LocalDateTime.of(lclDate,lclEnd);
            Timestamp timeStampStart = Timestamp.valueOf(lclDateTimeStart);
            Timestamp timeStampEnd = Timestamp.valueOf(lclDateTimeEnd);
            //insertCustomer(String title, String description, String location, String type,
            //             Timestamp start, Timestamp end,int customerID, int userID, int contactID)
            AppointmentQuery.insertCustomer(title,description,location,type,timeStampStart,timeStampEnd,customerID,userID,contactID);
            System.out.println(description +" "+ location+" "+title+" "+lclDate+" "+type);
            stage = (Stage)((Button)event.getSource()).getScene().getWindow();
            scene = FXMLLoader.load(getClass().getResource("/view_controller/dashboard.fxml"));
            stage.setScene(new Scene(scene));
            stage.show();
        }

    }


}
