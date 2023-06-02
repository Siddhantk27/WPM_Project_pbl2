package application;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import java.sql.ResultSet;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;

public class MaintananceController {

    @FXML
    private TextField id;

    @FXML
    private TextField employee;

    @FXML
    private DatePicker datepick;

    @FXML
    private TextField stime;

    @FXML
    private CheckBox c_box;

    @FXML
    private CheckBox s_box;

    @FXML
    private Button save_field;

    @FXML
    private Button history_field;
    
    @FXML
    private Label ulabel;

    @FXML
    void go_to_history(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/resources/newupkeep.fxml"));
            Parent nextRoot = loader.load();

            // Create a new scene with the nextRoot
            Scene nextScene = new Scene(nextRoot);

            // Get the stage from the current scene
            Stage stage = (Stage) history_field.getScene().getWindow();

            // Set the new scene on the stage
            stage.setScene(nextScene);
            stage.setTitle("Upkeep History");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private Button back;
    @FXML
    void go_to_dashboard(ActionEvent event) {

    
    try {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/resources/dashboard1.fxml"));
        Parent nextRoot = loader.load();

        
        Stage stage = (Stage) back.getScene().getWindow();

        Scene nextScene = new Scene(nextRoot);

        stage.setScene(nextScene);
        stage.setTitle("Dashboard");
        stage.show();
    } catch (IOException e) {
        e.printStackTrace();
    }
}
    @FXML
    void go_to_next() {
        // Get the values from the UI controls
        String employeeName = employee.getText();
        LocalDate upkeepDate = datepick.getValue();
        String startTime = stime.getText();
        String chlorination = c_box.isSelected() ? "Y" : "N";
        String slides = s_box.isSelected() ? "Y" : "N";
        
        try {
            // Insert data into the database
            try (Connection connection = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1522:xe", "project", "root");
                 PreparedStatement idStatement = connection.prepareStatement("SELECT seq.nextval FROM DUAL");
                 PreparedStatement statement = connection.prepareStatement("INSERT INTO upkeep_details (maintenance_id, emp_name, start_time, date_done, chlorination, slides) VALUES (?, ?, ?, TO_DATE(?, 'YYYY-MM-DD'), ?, ?)")) {
                
                // Retrieve the next value from the sequence for MAINTENANCE_ID
                try (ResultSet rs = idStatement.executeQuery()) {
                    if (rs.next()) {
                        int upkeepId = rs.getInt(1);
                        
                        // Set the values for the INSERT statement
                        statement.setInt(1, upkeepId);
                        statement.setString(2, employeeName);
                        statement.setString(3, startTime);
                        statement.setString(4, upkeepDate.toString());
                        statement.setString(5, chlorination);
                        statement.setString(6, slides);
                        
                        // Execute the INSERT statement
                        statement.executeUpdate();

                        FXMLLoader loader = new FXMLLoader(getClass().getResource("/resources/upkeepdone.fxml"));
                        Parent nextRoot = loader.load();
                        Upkeepsuccess controller = loader.getController();

                        // Get the stage from the current scene
                        Stage stage = (Stage) save_field.getScene().getWindow();

                        // Create a new scene with the nextRoot
                        Scene nextScene = new Scene(nextRoot);

                        // Set the new scene on the stage
                        stage.setScene(nextScene);
                        stage.setTitle("Next Page");
                        stage.show();
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace();
                // Handle database errors
            } catch (IOException e) {
                e.printStackTrace();
                // Handle FXML loading errors
            }
        } catch (NumberFormatException e) {
            ulabel.setText("Invalid start time. Please enter an integer value.");
        }
    }
}