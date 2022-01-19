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
import model.Customer;
import query.CustomerQuery;
import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

import static view_controller.UpdateCustomerController.setCustomer;

public class DashboardController implements Initializable {

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        customerTable.setItems(CustomerQuery.getAllCustomers()); //queries db to create customer objects and add them to ObservableList
        custIDCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        custNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        custPhoneCol.setCellValueFactory(new PropertyValueFactory<>("phone"));
        custAddressCol.setCellValueFactory(new PropertyValueFactory<>("address"));
        custPostalCol.setCellValueFactory(new PropertyValueFactory<>("postal"));
        custDivIDCol.setCellValueFactory(new PropertyValueFactory<>("divisionId"));
    }


    @FXML
    private Button CustDeleteBtn;

    @FXML
    private Button apptAddBtn;

    @FXML
    private TableColumn<?, ?> apptContactCol;

    @FXML
    private TableColumn<?, ?> apptCustomerIDCol;

    @FXML
    private TableColumn<?, ?> apptDescriptionCol;

    @FXML
    private TableColumn<?, ?> apptEndCol;

    @FXML
    private TableColumn<?, ?> apptIdCol;

    @FXML
    private TableColumn<?, ?> apptLocationCol;

    @FXML
    private TableColumn<?, ?> apptStartCol;

    @FXML
    private TableColumn<?, ?> apptTitleCol;

    @FXML
    private TableColumn<?, ?> apptTypeCol;

    @FXML
    private Button apptUpdateBtn;

    @FXML
    private TableColumn<?, ?> apptUserIDCol;

    @FXML
    private Button aptDeleteBtn;

    @FXML
    private Button custAddBtn;

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

    @FXML
    private Button custUpdateBtn;

    @FXML
    private TableView<Customer> customerTable;

    Stage stage;
    Parent scene;

    @FXML
    void addAppt(ActionEvent event) {

    }

    @FXML
    void addCustomer(ActionEvent event) throws IOException {
        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/view_controller/addCustomer.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    @FXML
    void deleteAppt(ActionEvent event) {

    }

    @FXML
    void deleteCustomer(ActionEvent event) {
        Customer sCustomer = (Customer) customerTable.getSelectionModel().getSelectedItem();
        //if no customer is selected
        if(sCustomer == null){
            Alert noSelectionAlert = new Alert(Alert.AlertType.ERROR);
            noSelectionAlert.setTitle("Customer");
            noSelectionAlert.setContentText("Please select a customer to delete");
            noSelectionAlert.showAndWait();
        }else{
            //if a customer is selected code to be completed
            Alert deleteAlert = new Alert(Alert.AlertType.CONFIRMATION);
            deleteAlert.setTitle("Delete Customer");
            deleteAlert.setContentText("Are you sure you want to delete the customer? This will delete all appointments associated with customer.");
            Optional<ButtonType> result = deleteAlert.showAndWait();
            if(result.isPresent() && result.get() == ButtonType.OK){
                //if customer deletion is confirmed
                if(CustomerQuery.deleteCustomer(sCustomer)){
                    Alert deleteSuccess = new Alert(Alert.AlertType.INFORMATION);
                    deleteSuccess.setTitle("Customer");
                    deleteSuccess.setContentText("Customer was successfully deleted");
                    deleteSuccess.showAndWait();
                    customerTable.setItems(CustomerQuery.getAllCustomers());
                }
                //if a connection was lost, or other deletion error occurs
            Alert deleteError = new Alert(Alert.AlertType.INFORMATION);
            deleteError.setTitle("Customer");
            deleteError.setContentText("Something went wrong with customer to be deleted");
            deleteError.showAndWait();
            }
        }
    }

    @FXML
    void updateAppt(ActionEvent event) {

    }

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
