package view_controller;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import query.UserQuery;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import java.io.IOException;
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
                loginButton.setText(rb.getString("LOGINBTN"));
                loginLabel.setText(rb.getString("LOGINLBL"));
                passwordTF.setText(rb.getString("PASSWORD"));
                userNameTF.setText(rb.getString("USERNAME"));
                zoneIDLabel.setText(rb.getString("ZONEID"));

            }
        }catch (MissingResourceException e){
            //nothing for now
        }
        identifiedZonelbl.setText(String.valueOf(Locale.getDefault()));
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

    private Parent scene;
    private Stage stage;

    private void alertUser(){
        Alert incorrectLogin = new Alert(Alert.AlertType.WARNING);
        incorrectLogin.setTitle(rb.getString("alertTitle"));
        incorrectLogin.setHeaderText(rb.getString("alertHeader"));
        incorrectLogin.showAndWait();
    }
    @FXML
    void logOn(ActionEvent event) throws IOException {
        if(UserQuery.verifyUser(userNameTF.getText(), passwordTF.getText()) != null){
            System.out.println("logged in");
            stage = (Stage)((Button)event.getSource()).getScene().getWindow();
            scene = FXMLLoader.load(getClass().getResource("/view_controller/dashboard.fxml"));
            stage.setScene(new Scene(scene));
            stage.show();

        }else{
            alertUser();
        }


    }

}
