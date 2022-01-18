package view_controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import model.Country;
import model.FirstLevelDiv;
import query.CountryQuery;
import java.net.URL;
import java.util.ResourceBundle;
import helper.InputValidate;

public class AddCustomerController implements Initializable {
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    countryCombo.setItems(CountryQuery.getAllCountries());
    customerIDTF.setPromptText("AUTO GEN-DISABLED");
    customerIDTF.setDisable(true);
    }

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
    private ComboBox<Country> countryCombo;

    @FXML
    private ComboBox<FirstLevelDiv> firstLevelDiv;

    @FXML
    void onCancel(ActionEvent event) {

    }

    @FXML
    void onFirstLevelDiv(ActionEvent event) {

    }

    @FXML
    void onSave(ActionEvent event) {
        String name = null;
        String postal = null;
        String phone = null;
        String address = null;
        boolean errorFound = false;

        if(InputValidate.validateString(nameTF.getText())){
            name = nameTF.getText();
        }else{
            errorFound = true;
        }
        if(InputValidate.validateString(addressTF.getText())){
            address = addressTF.getText();
        }else{
            errorFound = true;
        }
        if(InputValidate.validateString(phoneTF.getText())){
            phone = phoneTF.getText();
        }else{
            errorFound = true;
        }
        if(InputValidate.validateString(addressTF.getText())){
            address = addressTF.getText();
        }else{
            errorFound = true;
        }
        if(!errorFound){
            //create new customer object


        }

    }



}
