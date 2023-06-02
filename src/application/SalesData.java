package application;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SalesData {

    @FXML
    private Button dashboardButton;

    @FXML
    private DatePicker datepicker;

    @FXML
    private DatePicker datepicker1;

    @FXML
    private Button generate_sales;

    @FXML
    private Label labelcs;

    @FXML
    private Label labelga;

    @FXML
    private Label labelgc;

    @FXML
    private Label labelts;

    @FXML
    private Label labelva;

    @FXML
    private Label labelvc;

    @FXML
    void fetch_details(ActionEvent event) {
        // Get the selected date range
        String fromDate = datepicker.getValue().toString();
        String toDate = datepicker1.getValue().toString();

        // Fetch the sales details from the 'book_ticket1' table for the selected date range
        double generalAdultsTickets = fetchTicketsSold("book_ticket1", "General Adult", fromDate, toDate);
        double vipAdultsTickets = fetchTicketsSold("book_ticket1", "VIP Adult", fromDate, toDate);
        double collegeStudentsTickets = fetchTicketsSold("book_ticket1", "College Student", fromDate, toDate);
        double generalChildTickets = fetchTicketsSold("book_ticket1", "General Child", fromDate, toDate);
        double vipChildTickets = fetchTicketsSold("book_ticket1", "VIP Child", fromDate, toDate);
        double totalTickets = generalAdultsTickets + vipAdultsTickets + collegeStudentsTickets + generalChildTickets + vipChildTickets;

        // Calculate the total sales amount based on the tickets sold
        double totalSales = calculateTotalSales("book_ticket1", fromDate, toDate);

        // Display the fetched sales details
        labelga.setText(String.valueOf(generalAdultsTickets));
        labelva.setText(String.valueOf(vipAdultsTickets));
        labelcs.setText(String.valueOf(collegeStudentsTickets));
        labelgc.setText(String.valueOf(generalChildTickets));
        labelvc.setText(String.valueOf(vipChildTickets));
        labelts.setText(String.valueOf(totalSales));
    }

    @FXML
    void go_to_dashboard(ActionEvent event) {try {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/resources/dashboard1.fxml"));
        Parent nextRoot = loader.load();

        
        Stage stage = (Stage) dashboardButton.getScene().getWindow();

        Scene nextScene = new Scene(nextRoot);

        stage.setScene(nextScene);
        stage.setTitle("Dashboard");
        stage.show();
    } catch (IOException e) {
        e.printStackTrace();
    }}

    // Helper method to fetch the number of tickets sold from the database
    private double fetchTicketsSold(String tableName, String category, String fromDate, String toDate) {
        double ticketsSold = 0;

        try {
            // Establish the database connection (replace with your actual connection details)
            Connection connection = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1522:xe", "project", "root");

            // Prepare the SQL statement to fetch the tickets sold for the given category and date range
            String sql = "SELECT SUM(adult_no) + SUM(child_no) AS total_tickets FROM " + tableName +
                         " WHERE (booking_date BETWEEN TO_DATE(?, 'YYYY-MM-DD') AND TO_DATE(?, 'YYYY-MM-DD'))" +
                         " AND (ticket_type = ? OR ticket_type1 = ?)";

            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, fromDate);
            statement.setString(2, toDate);
            statement.setString(3, category);
            statement.setString(4, category);

            // Execute the query
            ResultSet resultSet = statement.executeQuery();

            // Fetch the tickets sold count
            if (resultSet.next()) {
                ticketsSold = resultSet.getDouble("total_tickets");
            }

            // Close the resources
            resultSet.close();
            statement.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return ticketsSold;
    }

    // Helper method to calculate the total sales amount from the database
    private double calculateTotalSales(String tableName, String fromDate, String toDate) {
        double totalSales = 0;

        try {
            // Establish the database connection (replace with your actual connection details)
            Connection connection = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1522:xe", "project", "root");

            // Prepare the SQL statement to calculate the total sales amount for the given date range
            String sql = "SELECT SUM(price) AS total_sales FROM " + tableName +
                         " WHERE booking_date BETWEEN TO_DATE(?, 'YYYY-MM-DD') AND TO_DATE(?, 'YYYY-MM-DD')";

            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, fromDate);
            statement.setString(2, toDate);

            // Execute the query
            ResultSet resultSet = statement.executeQuery();

            // Fetch the total sales amount
            if (resultSet.next()) {
                totalSales = resultSet.getDouble("total_sales");
            }

            // Close the resources
            resultSet.close();
            statement.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return totalSales;
    }
}
