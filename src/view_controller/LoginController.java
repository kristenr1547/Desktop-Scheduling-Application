package view_controller;

import daoImplementation.UserQuery;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import java.net.URL;
import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;


public class LoginController implements Initializable {
    ResourceBundle rb;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try{
            rb = ResourceBundle.getBundle("view_controller/resourceFile", Locale.getDefault());
            if(Locale.getDefault().getLanguage().equals("fr")){
//                System.out.println(rb.getString("hello")+ " "+ rb.getString("you"));
                loginButton.setText(rb.getString("LOGINBTN"));
                loginLabel.setText(rb.getString("LOGINLBL"));
                passwordTF.setText(rb.getString("PASSWORD"));
                userNameTF.setText(rb.getString("USERNAME"));
                zoneIDLabel.setText(rb.getString("ZONEID"));
                identifiedZonelbl.setText(String.valueOf(Locale.getDefault()));


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
        if(UserQuery.verifyUser(userNameTF.getText(), passwordTF.getText()) == null){
            Alert incorrectLogin = new Alert(Alert.AlertType.WARNING);
            incorrectLogin.setTitle(rb.getString("alertTitle"));
            incorrectLogin.setHeaderText(rb.getString("alertHeader"));
            incorrectLogin.showAndWait();
        }else{
            System.out.println("Logged in");
        }


    }

}
