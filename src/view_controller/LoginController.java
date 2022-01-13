package view_controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import java.net.URL;
import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;


public class LoginController implements Initializable {
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try{
            ResourceBundle rb = ResourceBundle.getBundle("view_controller/resourceFile", Locale.getDefault());
            if(Locale.getDefault().getLanguage().equals("fr")){
//                System.out.println(rb.getString("hello")+ " "+ rb.getString("you"));
                loginButton.setText(rb.getString("LOGINBTN"));
                loginLabel.setText(rb.getString("LOGINLBL"));
                passwordTF.setText(rb.getString("PASSWORD"));
                userNameTF.setText(rb.getString("USERNAME"));
                zoneIDLabel.setText(rb.getString("ZONEID"));

            }
        }catch (MissingResourceException e){
            //nothing for now
        }
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


    private boolean authenticateUser(){
        return true;
    }
    @FXML
    void logOn(ActionEvent event) {

    }

}
