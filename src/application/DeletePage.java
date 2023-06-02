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
import javafx.stage.Stage;

public class DeletePage {
    @FXML
    private Button deletemp;

    @FXML
    private TextField empid;

    @FXML
    private Label lage;

    @FXML
    private Label lcontact;

    @FXML
    private Label lname;

    @FXML
    private Label lpost;

    @FXML
    private Label elabel;

    @FXML
    private Button search;

    @FXML
    void go_to_next(ActionEvent event) {
        String employeeId = empid.getText();
        if (!employeeId.isEmpty()) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/resources/delete confirm.fxml"));
                Parent nextRoot = loader.load();

                DeleteSuccess deleteSuccessController = loader.getController();
                deleteSuccessController.setEmployeeId(employeeId);

                Stage stage = (Stage) deletemp.getScene().getWindow();
                Scene nextScene = new Scene(nextRoot);

                stage.setScene(nextScene);
                stage.setTitle("Delete Confirmation");
                stage.show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @FXML
    void go_find(ActionEvent event) {
        String employeeId = empid.getText();
        retrieveAndDisplayEmployeeDetails(employeeId);
    }

    public void setEmpInfo(String tname, String tage, String tcontact, String tpost) {
        lname.setText(tname);
        lage.setText(tage);
        lcontact.setText(tcontact);
        lpost.setText(tpost);
    }

    public void clearEmpInfo() {
        lname.setText("");
        lage.setText("");
        lcontact.setText("");
        lpost.setText("");
    }

    private void retrieveAndDisplayEmployeeDetails(String employeeId) {
        try (Connection con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1522:xe", "project", "root")) {
            String selectQuery = "SELECT f_name, staff_age, contact_no, post" +
                    " FROM staff" +
                    " WHERE staff_id = ?";
            try (PreparedStatement selectStmt = con.prepareStatement(selectQuery)) {
                selectStmt.setString(1, employeeId);

                try (ResultSet rs = selectStmt.executeQuery()) {
                    if (rs.next()) {
                        String tname = rs.getString("f_name");
                        String tage = rs.getString("staff_age");
                        String tcontact = rs.getString("contact_no");
                        String tpost = rs.getString("post");

                        setEmpInfo(tname, tage, tcontact, tpost);
                        elabel.setText("");
                    } else {
                        clearEmpInfo();
                        elabel.setText("Employee not found");
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
