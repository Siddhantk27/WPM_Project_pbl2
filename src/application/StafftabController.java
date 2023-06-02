package application;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class StafftabController {

    @FXML
    private Button addemp;

    @FXML
    private Button dashboard;
    @FXML
	public AnchorPane anchortab;

    @FXML
    private Button viewemp;

    @FXML
    private Button add;
    @FXML
    private Button delemp;

    @FXML
    private TextField eage;

    @FXML
    private TextField econtact;

    @FXML
    private Label elabel;

    @FXML
    private TextField ename;

    @FXML
    private TextField poste;

    @FXML
    private TextField esalary;

    @FXML
    void go_to_added(ActionEvent event) {
        String empname = ename.getText();
        String empage = eage.getText();
        String empsal = esalary.getText();
        String empost = poste.getText();
        String empcontact = econtact.getText();

        if (empname.isEmpty() || empcontact.isEmpty() || empage.isEmpty() || empsal.isEmpty() || empost.isEmpty()) {
            elabel.setText("Warning: Please fill in all required fields.");
            return;
        }

        // Validate input
        validateInput(empname, empage, empsal, empost, empcontact);
    }

    @FXML
    void go_to_addemp(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/resources/Addemp.fxml"));
            Parent nextRoot = loader.load();
            
            // Create a new AnchorPane
            AnchorPane newAnchorPane = new AnchorPane();
            newAnchorPane.getChildren().add(nextRoot);
            
            // Replace the content of the existing AnchorPane with the new AnchorPane
            anchortab.getChildren().setAll(newAnchorPane);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void go_to_delemp(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/resources/DeleteEmp.fxml"));
            Parent nextRoot = loader.load();

            // Create a new AnchorPane
            AnchorPane newAnchorPane = new AnchorPane();
            newAnchorPane.getChildren().add(nextRoot);

            // Replace the content of the existing AnchorPane with the new AnchorPane
            anchortab.getChildren().setAll(newAnchorPane);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void go_to_dashboard(ActionEvent event) {
    	try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/resources/dashboard1.fxml"));
            Parent nextRoot = loader.load();

            
            Stage stage = (Stage) dashboard.getScene().getWindow();

            Scene nextScene = new Scene(nextRoot);

            stage.setScene(nextScene);
            stage.setTitle("Dashboard");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
   }
    
    @FXML
    void go_to_showdata(ActionEvent event) {
    	 try {
             FXMLLoader loader = new FXMLLoader(getClass().getResource("/resources/staffdatabase1.fxml"));
             Parent nextRoot = loader.load();
             
             // Create a new AnchorPane
             AnchorPane newAnchorPane = new AnchorPane();
             newAnchorPane.getChildren().add(nextRoot);
             
             // Replace the content of the existing AnchorPane with the new AnchorPane
             anchortab.getChildren().setAll(newAnchorPane);
         } catch (IOException e) {
             e.printStackTrace();
         }
     }
    

    private void validateInput(String empname, String empage, String empsal, String empost, String empcontact) {
        // Check for invalid characters in empname and empost
        if (!empname.matches("[a-zA-Z]+")) {
            elabel.setText("Invalid characters in Name. Please enter alphabets only.");
            return;
        }
        if (!empost.matches("[a-zA-Z]+")) {
            elabel.setText("Invalid characters in Post. Please enter alphabets only.");
            return;
        }

        // Check for invalid characters in empcontact, empage, and empsal
        if (!empcontact.matches("\\d+")) {
            elabel.setText("Invalid characters in Contact. Please enter numbers only.");
            return;
        }
        if (!empage.matches("\\d+")) {
            elabel.setText("Invalid characters in Age. Please enter numbers only.");
            return;
        }
        if (!empsal.matches("\\d+")) {
            elabel.setText("Invalid characters in Salary. Please enter numbers only.");
            return;
        }

        // Input is valid, continue with adding the employee
        try (Connection connection = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1522:xe", "project", "root");
                PreparedStatement idStatement = connection.prepareStatement("SELECT seq.nextval FROM DUAL");
                PreparedStatement statement = connection.prepareStatement(
                        "INSERT INTO staff (staff_id, f_name, staff_age, salary, post, contact_no) VALUES (?, ?, ?, ?, ?, ?)")) {

            try (ResultSet rs = idStatement.executeQuery()) {
                if (rs.next()) {
                    int staffid = rs.getInt(1);
                    statement.setInt(1, staffid);
                    statement.setString(2, empname);
                    statement.setString(3, empage);
                    statement.setString(4, empsal);
                    statement.setString(5, empost);
                    statement.setString(6, empcontact);
                    statement.executeUpdate();
                    elabel.setText("Employee Added Successfully!!");
                }
            } catch (SQLException e) {
                e.printStackTrace();
                // Handle the exception appropriately (e.g., display error message)
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle the exception appropriately (e.g., display error message)
        }
    }
}
