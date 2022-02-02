package view_controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.Appointment;
import model.Contact;
import model.Customer;
import query.AppointmentQuery;
import query.ContactQuery;
import query.CustomerQuery;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Controller for the report screen.
 */
public class ReportController implements Initializable {
    /**
     *Initializes and populates combo boxes for the user to select information in the reports.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //set customer combo
        customerSelectionCombo.setItems(CustomerQuery.getAllCustomers());
        //set contact combo
        contactSelectionCombo.setItems(ContactQuery.getAllContacts());
        //set country combo
        contactTable.setItems(contactSchedule);
        countryListView.setItems(CustomerQuery.customersPerCountry());
        typeCol.setCellValueFactory(new PropertyValueFactory<>("type"));
        titleCol.setCellValueFactory(new PropertyValueFactory<>("title"));
        startCol.setCellValueFactory(new PropertyValueFactory<>("appointmentStart"));
        endCol.setCellValueFactory(new PropertyValueFactory<>("appointmentEnd"));
        descriptionCol.setCellValueFactory(new PropertyValueFactory<>("description"));
        customerIDCol.setCellValueFactory(new PropertyValueFactory<>("customerID"));
        apptIDCol.setCellValueFactory(new PropertyValueFactory<>("apptID"));
    }

    /**
     * The ObservableList that will populate the customer table.
     */
    ObservableList<Appointment> contactSchedule = FXCollections.observableArrayList();
    Stage stage;
    Parent scene;

    @FXML
    private TableColumn<Appointment, Integer> apptIDCol;
    /**
     * ListView that shows how many customers are in each country.
     */
    @FXML
    private ListView<String> countryListView;
    /**
     * ListView that shows how many appointments for each type.
     */
    @FXML
    private ListView<String> apptTypeListView;
    /**
     * ListView that shows how many appointments for each month.
     */
    @FXML
    private ListView<String> apptMonthListView;
    /**
     * ComboBox that has all contacts from database.
     */
    @FXML
    private ComboBox<Contact> contactSelectionCombo;
    /**
     * Table that contains the appointment information for a contact's schedule.
     */
    @FXML
    private TableView<Appointment> contactTable;


    @FXML
    private TableColumn<Appointment, Integer> customerIDCol;
    /**
     * ComboBox that has all customers from database.
     */
    @FXML
    private ComboBox<Customer> customerSelectionCombo;

    @FXML
    private TableColumn<Appointment, String> descriptionCol;

    @FXML
    private TableColumn<Appointment, String> endCol;

    @FXML
    private TableColumn<Appointment, String> startCol;

    @FXML
    private TableColumn<Appointment, String> titleCol;

    @FXML
    private TableColumn<Appointment, String> typeCol;


    /**
     * Displays the contact's schedule in a TableView.
     * @param event User selects a contact.
     */
    @FXML
    void onContactSelection(ActionEvent event) {
        Contact selectedContact = contactSelectionCombo.getSelectionModel().getSelectedItem();
        contactSchedule = AppointmentQuery.contactSchedule(selectedContact.getContactID());
        contactTable.setItems(contactSchedule);
    }

    /**
     * Displays the customers statistics in two ListViews.
     * @param event User selects a contact.
     */
    @FXML
    void onCustomerSelection(ActionEvent event) {
        //set type report listView
        Customer selectedCustomer = customerSelectionCombo.getSelectionModel().getSelectedItem();
        int customerID = selectedCustomer.getId();
        apptTypeListView.setItems(AppointmentQuery.returnTypeReport(customerID));
        //set month report listView
        apptMonthListView.setItems(AppointmentQuery.returnMonthReport(customerID));
    }

    /**
     *
     * @param event User selects the back button and returns back to main dashboard screen.
     * @throws IOException Error if the main dashboard is inaccessible.
     */
    @FXML
    void onBack(ActionEvent event) throws IOException {
        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/view_controller/dashboard.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }

}

