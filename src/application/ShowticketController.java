package application;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;

import javafx.event.ActionEvent;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;


public class ShowticketController {
	 @FXML
	    private Button conform_field;

	    @FXML
	    private Button dashboard_field;
    @FXML
    private Label lname;
    @FXML
    private Button back;
    @FXML
    private Label lid;

    @FXML
    private Label ladult;

    @FXML
    private Label lchild;

    @FXML
    private Label lamount;

    @FXML
    private Label ldate;
    @FXML
    void go_to_dashboard(ActionEvent event) {
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
    @FXML
    void go_to_booking(ActionEvent event) {
    	try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/resources/TICKET BOOKING.fxml"));
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
    void go_to_success(ActionEvent event) {
    	try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/resources/msaved.fxml"));
            Parent nextRoot = loader.load();

            
            Stage stage = (Stage) conform_field.getScene().getWindow();

            Scene nextScene = new Scene(nextRoot);

            stage.setScene(nextScene);
            stage.setTitle("Booking Successful");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void setTicketInfo(String tname, int tid, String tadult, String tchild, int price, String bookingDate) {
        lname.setText(tname);
        lid.setText(String.valueOf(tid));
        ladult.setText(tadult);
        lchild.setText(tchild);
        lamount.setText(String.valueOf(price));
        ldate.setText(bookingDate);
    }

    public void initialize() {
        // Retrieve the latest ticket information from the database and set it in the UI
        try (Connection con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1522:xe", "project", "root")) {
        	String selectQuery = "SELECT username, user_id, adult_no, child_no, price, TO_CHAR(booking_date, 'DD/MM/YYYY') AS booking_date " +
        		    "FROM (SELECT username, user_id, adult_no, child_no, price, booking_date " +
        		    "      FROM book_ticket1 " +
        		    "      ORDER BY insert_timestamp DESC) " +
        		    "WHERE ROWNUM = 1";
            try (PreparedStatement selectStmt = con.prepareStatement(selectQuery)) {
                try (ResultSet rs = selectStmt.executeQuery()) {
                    if (rs.next()) {
                        // Retrieve the values from the result set
                        String tname = rs.getString("username");
                        int tid = rs.getInt("user_id");
                        String tadult = rs.getString("adult_no");
                        String tchild = rs.getString("child_no");
                        int price = rs.getInt("price");
                        String bookingDate = rs.getString("booking_date");

                        // Set the ticket information in the UI elements
                        setTicketInfo(tname, tid, tadult, tchild, price, bookingDate);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
