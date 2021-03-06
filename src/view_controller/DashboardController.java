package view_controller;



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
import query.CustomerQuery;
import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import static view_controller.UpdateCustomerController.setCustomer;

/**
 * Main Dashboard Controller
 */
public class DashboardController implements Initializable {
    /**
     *Initializes and populates the customer and appointment tables.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        allAppointmentsRadio.setSelected(true);
        customerTable.setItems(CustomerQuery.getAllCustomers()); //queries db to create customer objects and add them to ObservableList
        custIDCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        custNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        custPhoneCol.setCellValueFactory(new PropertyValueFactory<>("phone"));
        custAddressCol.setCellValueFactory(new PropertyValueFactory<>("address"));
        custPostalCol.setCellValueFactory(new PropertyValueFactory<>("postal"));
        custDivIDCol.setCellValueFactory(new PropertyValueFactory<>("divisionId"));
        //setting up appointment table with all appointments
        appointmentTable.setItems(AppointmentQuery.getAllAppointments());
        apptContactCol.setCellValueFactory(new PropertyValueFactory<>("contact"));
        apptCustomerIDCol.setCellValueFactory(new PropertyValueFactory<>("customerID"));
        apptDescriptionCol.setCellValueFactory(new PropertyValueFactory<>("description"));
        apptEndCol.setCellValueFactory(new PropertyValueFactory<>("appointmentEnd"));
        apptIdCol.setCellValueFactory(new PropertyValueFactory<>("apptID"));
        apptLocationCol.setCellValueFactory(new PropertyValueFactory<>("location"));
        apptStartCol.setCellValueFactory(new PropertyValueFactory<>("appointmentStart"));
        apptTitleCol.setCellValueFactory(new PropertyValueFactory<>("title"));
        apptTypeCol.setCellValueFactory(new PropertyValueFactory<>("type"));
        apptUserIDCol.setCellValueFactory(new PropertyValueFactory<>("userID"));
    }

    /**
     * RadioButton if selected disiplays all appointments in tableview.
     */
    @FXML
    private RadioButton allAppointmentsRadio;
    /**
     * Table that displays appointment information.
     */
    @FXML
    private TableView<Appointment> appointmentTable;
    @FXML
    private TableColumn<Appointment, Contact> apptContactCol;
    @FXML
    private TableColumn<Appointment, Integer> apptCustomerIDCol;
    @FXML
    private TableColumn<Appointment, String> apptDescriptionCol;
    @FXML
    private TableColumn<Appointment, String> apptEndCol;
    @FXML
    private TableColumn<Appointment, Integer> apptIdCol;
    @FXML
    private TableColumn<Appointment, String> apptLocationCol;
    @FXML
    private TableColumn<Appointment, String> apptStartCol;
    @FXML
    private TableColumn<Appointment, String> apptTitleCol;
    @FXML
    private TableColumn<Appointment, String> apptTypeCol;
    @FXML
    private TableColumn<Appointment, Integer> apptUserIDCol;
    @FXML
    private TableColumn<Customer, String> custAddressCol;
    @FXML
    private TableColumn<Customer, Integer> custDivIDCol;
    @FXML
    private TableColumn<Customer, Integer> custIDCol;
    @FXML
    private TableColumn<Customer, String> custNameCol;
    @FXML
    private TableColumn<Customer, String> custPhoneCol;
    @FXML
    private TableColumn<Customer, String> custPostalCol;
    /**
     * Table that displays appointment information.
     */
    @FXML
    private TableView<Customer> customerTable;

    Stage stage;
    Parent scene;

    /**
     *
     * @param event User selects radiobutton to display all appointments in database.
     */
    @FXML
    void allAppointmentSelected(ActionEvent event) {
        appointmentTable.setItems(AppointmentQuery.getAllAppointments());
    }

    /**
     *
     * @param event User selects RadioButton to display all appointments within a week.
     */
    @FXML
    void weeklyAppointmentSelected(ActionEvent event) {
        appointmentTable.setItems(AppointmentQuery.getAllAppointmentsWeek());

    }
    /**
     *
     * @param event User selects RadioButton to display all appointments within a month.
     */
    @FXML
    void monthlyAppointmentSelected(ActionEvent event) {
        appointmentTable.setItems(AppointmentQuery.getAllAppointmentsMonthly());

/**
 * User selects to go to the reports screen to view data from reports pulled.
 */
    }
    @FXML
    void onReport(ActionEvent event) throws IOException{
        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/view_controller/report.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    /**
     * Takes user to add appointment screen.
     * @param event User selects add appointment button.
     * @throws IOException if screen in inaccessible.
     */
    @FXML
    void addAppt(ActionEvent event) throws IOException {
        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/view_controller/addAppointment.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }
    /**
     * Takes user to add customer screen.
     * @param event User selects add customer button.
     * @throws IOException if screen in inaccessible.
     */
    @FXML
    void addCustomer(ActionEvent event) throws IOException {
        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/view_controller/addCustomer.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    /**
     * Will display an error if there are no appointments selected. It will display appointment information that is deleted successfully.
     * @param event User selects delete appointment button.
     */
    @FXML
    void deleteAppt(ActionEvent event) {
        Appointment selAppt = (Appointment) appointmentTable.getSelectionModel().getSelectedItem();
        //if no appointment is selected
        if(selAppt == null){
            Alert noSelectionAlert = new Alert(Alert.AlertType.ERROR);
            noSelectionAlert.setTitle("Appointment");
            noSelectionAlert.setContentText("Please select an appointment to delete");
            noSelectionAlert.showAndWait();
        }else{
            //if an appointment is selected code to be completed
            int apptID = selAppt.getApptID();
            String apptType = selAppt.getType();
            Alert deleteAlert = new Alert(Alert.AlertType.CONFIRMATION);
            deleteAlert.setTitle("Delete Appointment");
            deleteAlert.setGraphic(null);
            deleteAlert.setContentText("Are you sure you want to delete the appointment?");
            Optional<ButtonType> result = deleteAlert.showAndWait();
            if(result.isPresent() && result.get() == ButtonType.OK){
                //if customer deletion is confirmed
                if(AppointmentQuery.deleteAppointment(selAppt)){
                    Alert deleteSuccess = new Alert(Alert.AlertType.INFORMATION);
                    deleteSuccess.setTitle("Appointment");
                    deleteSuccess.setGraphic(null);
                    deleteSuccess.setHeaderText("Appointment Deleted");
                    deleteSuccess.setContentText("Appointment ID: "+apptID+ " Type: " + apptType+"was successfully deleted");
                    deleteSuccess.showAndWait();
                }}
        }appointmentTable.setItems(AppointmentQuery.getAllAppointments());
    }
    /**
     * Will display an error if there are no customers selected. It will display customer information that is deleted successfully.
     * It warns the user that deleting a customer will also delete all appointments, the database automatically deletes the appointments if the user
     * still desires to delete the customer.
     * @param event User selects delete customer button.
     */
    @FXML
    void deleteCustomer(ActionEvent event) {
        Customer sCustomer = (Customer) customerTable.getSelectionModel().getSelectedItem();
        //if no customer is selected
        if(sCustomer == null){
            Alert noSelectionAlert = new Alert(Alert.AlertType.ERROR);
            noSelectionAlert.setTitle("Customer");
            noSelectionAlert.setGraphic(null);
            noSelectionAlert.setContentText("Please select a customer to delete");
            noSelectionAlert.showAndWait();
        }else{
            //if a customer is selected code to be completed
            Alert deleteAlert = new Alert(Alert.AlertType.CONFIRMATION);
            deleteAlert.setTitle("Delete Customer");
            deleteAlert.setGraphic(null);
            deleteAlert.setContentText("Are you sure you want to delete the customer? This will delete all appointments associated with customer.");
            Optional<ButtonType> result = deleteAlert.showAndWait();
            if(result.isPresent() && result.get() == ButtonType.OK){
                //if customer deletion is confirmed
                if(CustomerQuery.deleteCustomer(sCustomer)){
                    //database automatically deletes all appointments associated with customer code below resets appointment table
                    Alert deleteSuccess = new Alert(Alert.AlertType.INFORMATION);
                    deleteSuccess.setGraphic(null);
                    deleteSuccess.setTitle("Customer");
                    deleteSuccess.setContentText("Customer was successfully deleted");
                    deleteSuccess.showAndWait();
                    customerTable.setItems(CustomerQuery.getAllCustomers());
                    appointmentTable.setItems(AppointmentQuery.getAllAppointments());
                }}
        }
    }

    /**
     * Will create an alert if there is not an appointment selected to be updated.
     * @param event User selects update appointment information.
     * @throws IOException if screen file is inaccessible.
     */
    @FXML
    void updateAppt(ActionEvent event) throws IOException{
        Appointment sAppointment = (Appointment) appointmentTable.getSelectionModel().getSelectedItem();
        //if no customer is selected
        if(sAppointment == null){
            Alert noSelectionAlert = new Alert(Alert.AlertType.ERROR);
            noSelectionAlert.setTitle("Customer");
            noSelectionAlert.setContentText("Please select a customer to update");
            noSelectionAlert.showAndWait();
        }else{
            UpdateAppointmentController.setUpdateAppt(sAppointment);
            stage = (Stage)((Button)event.getSource()).getScene().getWindow();
            scene = FXMLLoader.load(getClass().getResource("/view_controller/updateAppointment.fxml"));
            stage.setScene(new Scene(scene));
            stage.show();
        }

    }
    /**
     * Will create an alert if there is not a customer selected to be updated.
     * @param event User selects update customer information.
     * @throws IOException if screen file is inaccessible.
     */
    @FXML
    void updateCustomer(ActionEvent event) throws IOException {
        Customer sCustomer = (Customer) customerTable.getSelectionModel().getSelectedItem();
        //if no customer is selected
        if(sCustomer == null){
            Alert noSelectionAlert = new Alert(Alert.AlertType.ERROR);
            noSelectionAlert.setTitle("Customer");
            noSelectionAlert.setContentText("Please select a customer to update");
            noSelectionAlert.showAndWait();
        }else{
            setCustomer(sCustomer);
            stage = (Stage)((Button)event.getSource()).getScene().getWindow();
            scene = FXMLLoader.load(getClass().getResource("/view_controller/updateCustomer.fxml"));
            stage.setScene(new Scene(scene));
            stage.show();
        }
    }


}
