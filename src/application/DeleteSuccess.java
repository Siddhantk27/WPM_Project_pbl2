package application;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class DeleteSuccess extends StafftabController{

    @FXML
    private Button back_button;

    @FXML
    private Button confirm_field;

    @FXML
    private Label dlabel;

    private String employeeId;

    public void setEmployeeId(String employeeId) {
        this.employeeId = employeeId;
    }

    @FXML
    void on_back(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/resources/DeleteEmp.fxml"));
            Parent nextRoot = loader.load();

            Stage stage = (Stage) back_button.getScene().getWindow();

            Scene nextScene = new Scene(nextRoot);

            stage.setScene(nextScene);
            stage.setTitle("Search");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void on_click(ActionEvent event) {
        if (deleteEmployee()) {
        	try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/resources/staffnew.fxml"));
                Parent nextRoot = loader.load();

                
                Stage stage = (Stage) confirm_field.getScene().getWindow();

                Scene nextScene = new Scene(nextRoot);

                stage.setScene(nextScene);
                stage.setTitle("Dashboard");
                stage.show();
            } catch (IOException e) {
                e.printStackTrace();
            }
       }
    }

    private boolean deleteEmployee() {
        try (Connection con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1522:xe", "project", "root")) {
            String deleteQuery = "DELETE FROM staff WHERE staff_id = ?";
            try (PreparedStatement deleteStmt = con.prepareStatement(deleteQuery)) {
                deleteStmt.setString(1, employeeId);

                int rowsAffected = deleteStmt.executeUpdate();

                if (rowsAffected > 0) {
                    return true; // Deletion successful
                } else {
                    dlabel.setText("Employee not found"); // Display error message
                    return false; // Employee ID not found
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false; // Error occurred
        }
    }
}
