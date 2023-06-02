package application;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.*;
import java.util.Date;

public class StaffdataController {
    @FXML
    private TableView<Staff> tableview;

    @FXML
    private TableColumn<Staff, Integer> eid;

    @FXML
    private TableColumn<Staff, String> name;

    @FXML
    private TableColumn<Staff, Integer> age;

    @FXML
    private TableColumn<Staff, String> salary;

    
    @FXML
    private TableColumn<Staff, String> post;

    @FXML
    private TableColumn<Staff, String> contact;

    @FXML
    private Button dashboard_field;

    public void initialize() {
        // Set up the table columns
        eid.setCellValueFactory(new PropertyValueFactory<>("eid"));
        name.setCellValueFactory(new PropertyValueFactory<>("name"));
        age.setCellValueFactory(new PropertyValueFactory<>("age"));
        salary.setCellValueFactory(new PropertyValueFactory<>("salary"));
        post.setCellValueFactory(new PropertyValueFactory<>("post"));
        contact.setCellValueFactory(new PropertyValueFactory<>("contact")); 
        // Retrieve the upkeep details and populate the table
        ObservableList<Staff> staffList = retrieveStaffDetails();
        tableview.setItems(staffList);
    }

    private ObservableList<Staff> retrieveStaffDetails() {
        ObservableList<Staff> staffList = FXCollections.observableArrayList();

        try (Connection connection = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1522:xe", "project", "root");
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery("SELECT * FROM staff")) {

            while (resultSet.next()) {
            	int eid = resultSet.getInt("staff_id");
                String name = resultSet.getString("f_name");
                int age = resultSet.getInt("staff_age");
                String salary = resultSet.getString("salary");
                String post = resultSet.getString("post");
                String contact = resultSet.getString("contact_no");
               

                Staff staff = new Staff(eid ,name, age, salary, post, contact);
                staffList.add(staff);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return staffList;
    }

    @FXML
    private void go_to_dashboard() {
    	try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/resources/dashboard1.fxml"));
            Parent nextRoot = loader.load();

            
            Stage stage = (Stage) dashboard_field.getScene().getWindow();

            Scene nextScene = new Scene(nextRoot);

            stage.setScene(nextScene);
            stage.setTitle("Dashboard");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
   }
    }

