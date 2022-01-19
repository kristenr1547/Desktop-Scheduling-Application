package view_controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import model.Country;
import model.Customer;
import model.FirstLevelDiv;
import query.CountryQuery;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

import static query.FirstLevelDivQuery.getAllFirstLevelDiv;

public class UpdateCustomerController implements Initializable {

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        countryCombo.setItems(CountryQuery.getAllCountries());
        customerIDTF.setDisable(true);
        customerIDTF.setText(String.valueOf(updateCustomer.getId()));
        nameTF.setText(String.valueOf(updateCustomer.getName()));
        phoneTF.setText(String.valueOf(updateCustomer.getPhone()));
        postalTF.setText(String.valueOf(updateCustomer.getPostal()));
        addressTF.setText(String.valueOf(updateCustomer.getAddress()));
    }
    private static Customer updateCustomer;

    public static Customer getCustomer() {
        return updateCustomer;
    }

    public static void setCustomer(Customer updateCustomer) {
        UpdateCustomerController.updateCustomer = updateCustomer;
    }

    private Stage stage;
    private Parent scene;

    @FXML
    private TextField addressTF;

    @FXML
    private Button cancelBtn;

    @FXML
    private TextField customerIDTF;

    @FXML
    private TextField nameTF;

    @FXML
    private TextField phoneTF;

    @FXML
    private TextField postalTF;

    @FXML
    private Button saveBtn;
    @FXML
    private Label nameErrorLbl;
    @FXML
    private Label phoneErrorLbl;
    @FXML
    private Label addressErrorLbl;
    @FXML
    private Label postalErrorLbl;
    @FXML
    private Label countryError;
    @FXML
    private Label fldError;

    @FXML
    private ComboBox<Country> countryCombo;

    @FXML
    private ComboBox<FirstLevelDiv> firstLevelDiv;

    @FXML
    void onCountrySelection(ActionEvent event) {
        Country selectedCountry = (Country) countryCombo.getSelectionModel().getSelectedItem();
        if(selectedCountry != null){
            firstLevelDiv.setItems(getAllFirstLevelDiv(selectedCountry.getCountryID()));
        }
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


    public void onSave(ActionEvent actionEvent) {

    }
}
