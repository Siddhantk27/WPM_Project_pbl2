package application;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

public class SalesController {

    @FXML
    private TableColumn<Sales, Integer> ccontact;

    @FXML
    private TableColumn<Sales, Integer> cid;

    @FXML
    private TableColumn<Sales, String> cname;

    @FXML
    private TableColumn<Sales, Integer> cprice;

    @FXML
    private TableColumn<Sales, Integer> cquantity;

    @FXML
    private Button dashboardButton;

    @FXML
    private TableView<Sales> tableView;

    @FXML
    private DatePicker datepicker;

    public void initialize() {
        // Set up the table columns
        cname.setCellValueFactory(new PropertyValueFactory<>("name"));
        cid.setCellValueFactory(new PropertyValueFactory<>("custId"));
        ccontact.setCellValueFactory(new PropertyValueFactory<>("contact"));
        cquantity.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        cprice.setCellValueFactory(new PropertyValueFactory<>("price"));

        // Set up the datepicker listener
        datepicker.valueProperty().addListener((obs, oldDate, newDate) -> {
            // Retrieve the sales details for the selected date and populate the table
            ObservableList<Sales> salesList = retrieveSalesDetails(newDate);
            tableView.setItems(salesList);
        });
    }

    private ObservableList<Sales> retrieveSalesDetails(LocalDate selectedDate) {
        ObservableList<Sales> salesList = FXCollections.observableArrayList();

        try (Connection connection = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1522:xe", "project", "root");
        		PreparedStatement statement = connection.prepareStatement("SELECT * FROM book_ticket1 WHERE booking_date = ?");
) {
            
            statement.setDate(1, java.sql.Date.valueOf(selectedDate));

            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    String name = resultSet.getString("username");
                    int custId = resultSet.getInt("user_id");
                    long contact = resultSet.getLong("contact");
                    int quantity = resultSet.getInt("adult_no");
                    int price = resultSet.getInt("price");

                    Sales sales = new Sales(name, custId, contact, quantity, price);
                    salesList.add(sales);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return salesList;
    }

    @FXML
    void go_to_dashboard(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/resources/dashboard1.fxml"));
            Parent nextRoot = loader.load();

            Stage stage = (Stage) dashboardButton.getScene().getWindow();

            Scene nextScene = new Scene(nextRoot);

            stage.setScene(nextScene);
            stage.setTitle("Dashboard");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
