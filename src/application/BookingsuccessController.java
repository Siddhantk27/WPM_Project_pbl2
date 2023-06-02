package application;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class BookingsuccessController {

    @FXML
    private Button dashboard_Field;

    @FXML
    void go_to_dashboard(ActionEvent event) {
    	 try {
             FXMLLoader loader = new FXMLLoader(getClass().getResource("/resources/dashboard1.fxml"));
             Parent nextRoot = loader.load();

             
             Stage stage = (Stage) dashboard_Field.getScene().getWindow();

             Scene nextScene = new Scene(nextRoot);

             stage.setScene(nextScene);
             stage.setTitle("Dashboard");
             stage.show();
         } catch (IOException e) {
             e.printStackTrace();
         }
    }

}
