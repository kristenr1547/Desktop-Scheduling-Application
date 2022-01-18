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

public class UpdateCustomerController implements Initializable {

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        countryCombo.setItems(CountryQuery.getAllCountries());
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
}
