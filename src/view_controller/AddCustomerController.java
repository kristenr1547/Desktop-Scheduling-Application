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
import javafx.stage.Stage;
import model.Country;
import model.FirstLevelDiv;
import query.CountryQuery;
import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import query.CustomerQuery;
import static query.FirstLevelDivQuery.getAllFirstLevelDiv;
/**
 * Class that controls the CreateCustomer FXML document.
 */
public class AddCustomerController implements Initializable {
    /**
     *Sets up fields and Combo Boxes to display the appointment options.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        countryCombo.setItems(CountryQuery.getAllCountries());
        customerIDTF.setPromptText("AUTO GEN-DISABLED");
        customerIDTF.setDisable(true);
    }
    Stage stage;
    Parent scene;
    private ObservableList<Country> allCountries = FXCollections.observableArrayList();
    @FXML
    private TextField addressTF;

    @FXML
    private TextField customerIDTF;

    @FXML
    private TextField nameTF;

    @FXML
    private TextField phoneTF;

    @FXML
    private TextField postalTF;

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
    /**
     * Clears all of the error labels so the user does not think they have more errors than they have if they fix one but still have some remaining.
     */
    private void clearErrorLabels(){
        //error labels
        fldError.setVisible(false);
        countryError.setVisible(false);
        postalErrorLbl.setVisible(false);
        addressErrorLbl.setVisible(false);
        phoneErrorLbl.setVisible(false);
        nameErrorLbl.setVisible(false);
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
     * @param event Sets the FirstLevelDiv Combo Box.
     */
    @FXML
    void onCountrySelection(ActionEvent event) {
        Country selectedCountry = (Country) countryCombo.getSelectionModel().getSelectedItem();
        if(selectedCountry != null){
            firstLevelDiv.setItems(getAllFirstLevelDiv(selectedCountry.getCountryID()));
        }
    }
    /**
     *
     * @param event user selects the save button.
     *  Creates the customer with the information in the database if there are no errors.
     * @throws IOException if main dash file is not accessible.
     */
    @FXML
    void onSave(ActionEvent event) throws IOException {
        clearErrorLabels();
        //will create an insert into statement not a constructor for pojo object
        String name = null;
        String postal = null;
        String phone = null;
        String address = null;
        Country selectedCountry = (Country) countryCombo.getSelectionModel().getSelectedItem();
        FirstLevelDiv selectedFirstLevelDiv = (FirstLevelDiv) firstLevelDiv.getSelectionModel().getSelectedItem();
        int divId = 0;
        boolean errorFound = false;

        if(!nameTF.getText().isEmpty()){
            name = nameTF.getText();
        }else{
            errorFound = true;
            nameErrorLbl.setVisible(true);
        }
        if(!addressTF.getText().isEmpty()){
            address = addressTF.getText();
        }else{
            errorFound = true;
            addressErrorLbl.setVisible(true);
        }
        if(!phoneTF.getText().isEmpty()){
            phone = phoneTF.getText();
        }else{
            errorFound = true;
            phoneErrorLbl.setVisible(true);
        }
        if(!postalTF.getText().isEmpty()){
            postal = postalTF.getText();
        }else{
            errorFound = true;
            postalErrorLbl.setVisible(true);
        }
        if(selectedCountry == null){
            errorFound = true;
            countryError.setVisible(true);
        }
        if(selectedFirstLevelDiv == null){
            errorFound = true;
            fldError.setVisible(true);
        }else{
            divId = selectedFirstLevelDiv.getDivID();
        }


        if(!errorFound){
            //create new customer object
            //insertCustomer(String name, String address, String postal, String phone, int divID)
            CustomerQuery.insertCustomer(name,address,postal,phone,divId);
            stage = (Stage)((Button)event.getSource()).getScene().getWindow();
            scene = FXMLLoader.load(getClass().getResource("/view_controller/dashboard.fxml"));
            stage.setScene(new Scene(scene));
            stage.show();
        }


    }



}
