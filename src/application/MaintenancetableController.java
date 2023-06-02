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

public class MaintenancetableController {
    @FXML
    private TableView<Upkeep> tableView;

    @FXML
    private TableColumn<Upkeep, String> cname;

    @FXML
    private TableColumn<Upkeep, Integer> cid;

    @FXML
    private TableColumn<Upkeep, String> cslides;

    @FXML
    private TableColumn<Upkeep, String> ccl;

    @FXML
    private TableColumn<Upkeep, Date> cdate;

    @FXML
    private TableColumn<Upkeep, String> cstart;

   
    @FXML
    private Button dashboard_field;

    public void initialize() {
        // Set up the table columns
        cname.setCellValueFactory(new PropertyValueFactory<>("name"));
        cid.setCellValueFactory(new PropertyValueFactory<>("upkeepId"));
        cslides.setCellValueFactory(new PropertyValueFactory<>("slides"));
        ccl.setCellValueFactory(new PropertyValueFactory<>("chlorination"));
        cdate.setCellValueFactory(new PropertyValueFactory<>("date"));
        cstart.setCellValueFactory(new PropertyValueFactory<>("startTimeAsString")); // Use startTimeAsString property
       
        // Retrieve the upkeep details and populate the table
        ObservableList<Upkeep> upkeepList = retrieveUpkeepDetails();
        tableView.setItems(upkeepList);
    }

    private ObservableList<Upkeep> retrieveUpkeepDetails() {
        ObservableList<Upkeep> upkeepList = FXCollections.observableArrayList();

        try (Connection connection = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1522:xe", "project", "root");
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery("SELECT * FROM upkeep_details")) {

            while (resultSet.next()) {
                String name = resultSet.getString("emp_name");
                int upkeepId = resultSet.getInt("maintenance_id");
                String slides = resultSet.getString("slides");
                String chlorination = resultSet.getString("chlorination");
                Date date = resultSet.getDate("date_done");
                String startTime = resultSet.getString("start_time");
                

                Upkeep upkeep = new Upkeep(name, upkeepId, slides, chlorination, date, startTime);
                upkeepList.add(upkeep);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return upkeepList;
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

