package application;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class Upkeepsuccess {

    @FXML
    private Button dashboard_field;

    @FXML
    void go_to_dashboard(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/resources/dashboard1.fxml"));
            Parent nextRoot = loader.load();

            // Get the stage from the current scene
            Stage stage = (Stage) dashboard_field.getScene().getWindow();

            // Create a new scene with the nextRoot
            Scene nextScene = new Scene(nextRoot);

            // Set the new scene on the stage
            stage.setScene(nextScene);
            stage.setTitle("Dashboard");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
