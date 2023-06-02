 package application;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class DashboardController {

    @FXML
    private Button bookingField;

    @FXML
    private Button logoutField;

    @FXML
    private Button salesField;

    @FXML
    private Button staffField;

    @FXML
    private Button upkeepField;

    @FXML
    void go_to_booking(ActionEvent event) {
         
    	try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/resources/TICKET BOOKING.fxml"));
            Parent nextRoot = loader.load();

            
            Stage stage = (Stage) bookingField.getScene().getWindow();

            
            Scene nextScene = new Scene(nextRoot);

            
            stage.setScene(nextScene);
            stage.setTitle("Booking");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void go_to_login(ActionEvent event) {
               
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/resources/newlogin.fxml"));
            Parent nextRoot = loader.load();

            
            Stage stage = (Stage) logoutField.getScene().getWindow();

            Scene nextScene = new Scene(nextRoot);

            stage.setScene(nextScene);
            stage.setTitle("Login Page");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @FXML
    void go_to_sales(ActionEvent event) {
      
    	try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/resources/newsales.fxml"));
            Parent nextRoot = loader.load();

            // Get the stage from the current scene
            Stage stage = (Stage) salesField.getScene().getWindow();

            // Create a new scene with the nextRoot
            Scene nextScene = new Scene(nextRoot);

            // Set the new scene on the stage
            stage.setScene(nextScene);
            stage.setTitle("Sales Page");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    

    @FXML
    void go_to_staff(ActionEvent event) {
      
    	try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/resources/staffnew.fxml"));
            Parent nextRoot = loader.load();

            // Get the stage from the current scene
            Stage stage = (Stage) staffField.getScene().getWindow();

            // Create a new scene with the nextRoot
            Scene nextScene = new Scene(nextRoot);

            // Set the new scene on the stage
            stage.setScene(nextScene);
            stage.setTitle("staffdatabase Page");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void go_to_upkeep(ActionEvent event) {
      
    	try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/resources/maintenance entry.fxml"));
            Parent nextRoot = loader.load();

            // Get the stage from the current scene
            Stage stage = (Stage) upkeepField.getScene().getWindow();

            // Create a new scene with the nextRoot
            Scene nextScene = new Scene(nextRoot);

            // Set the new scene on the stage
            stage.setScene(nextScene);
            stage.setTitle("Upkeep Page");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}

