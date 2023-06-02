package application;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class LoginController {

    @FXML
    private PasswordField password;

    @FXML
    private Label resulltLabel;

    @FXML
    private Button submit;

    @FXML
    private TextField username;

    @FXML
    void on_click(ActionEvent event) {
  
    	
    	String username1 = username.getText();
	    String password1 = password.getText();


	    if (username1.equals("admin") && password1.equals("root")) {
	    	resulltLabel.setText("Login successful!");
	    	try {
	            FXMLLoader loader = new FXMLLoader(getClass().getResource("/resources/dashboard1.fxml"));
	            Parent nextRoot = loader.load();

	            // Get the stage from the current scene
	            Stage stage = (Stage) submit.getScene().getWindow();

	            // Create a new scene with the nextRoot
	            Scene nextScene = new Scene(nextRoot);

	            // Set the new scene on the stage
	            stage.setScene(nextScene);
	            stage.setTitle("Next Page");
	            stage.show();
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	    }
	    else {
	    	resulltLabel.setText("Login Failed!");
	    }
    }

}
