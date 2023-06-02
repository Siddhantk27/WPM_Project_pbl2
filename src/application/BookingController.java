package application;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class BookingController {

    @FXML
    private ChoiceBox<String> TICKETTYPE;

    @FXML
    private TextField child;
    @FXML
    private ChoiceBox<String> TICKETTYPE1;

    @FXML
    private TextField address;

    @FXML
    private TextField adult;

    @FXML
    private TextField age;
    @FXML
    private Button back;

    @FXML
    private TextField contact;

    @FXML
    private DatePicker datepicker;

    @FXML
    private ChoiceBox<String> genderChoiceBox;

    @FXML
    private TextField name;
    @FXML
    private Label warning;
    @FXML
    private Button submit;

    @FXML
    public void initialize() {
        // Add choices to the genderChoiceBox
        ObservableList<String> genderChoices = FXCollections.observableArrayList(
                "Male", "Female", "Other");
        genderChoiceBox.setItems(genderChoices);

        // Add choices to the TICKETTYPE choice box
        ObservableList<String> ticketTypeChoices = FXCollections.observableArrayList(
                "General Adult", "VIP Adult", "College Student");
        TICKETTYPE.setItems(ticketTypeChoices);

        // Add choices to the TICKETTYPE1 choice box
        ObservableList<String> ticketType1Choices = FXCollections.observableArrayList(
                "General Child", "VIP Child");
        TICKETTYPE1.setItems(ticketType1Choices);
    }

    @FXML
    void save_to_database(ActionEvent event) {
        String tname = name.getText();
        String tgender = genderChoiceBox.getValue();
        String tage = age.getText();
        String tcontact = contact.getText();
        String taddress = address.getText();
        String tadult = adult.getText();
        String tchild = child.getText();
        String ttickettype = TICKETTYPE.getValue();
        String ttickettype1 = TICKETTYPE1.getValue();
        int price = 0;

        if (tname.isEmpty() || tcontact.isEmpty() || tage.isEmpty() || tadult.isEmpty() || tchild.isEmpty()
                || tgender.isEmpty() || taddress.isEmpty()) {
            warning.setText("Warning: Please fill in all required fields.");
            return;
        }

        // Check if the child or adult number is 0
        boolean isChildZero = tchild.equals("0");
        boolean isAdultZero = tadult.equals("0");

        if (!isChildZero && !isAdultZero && (ttickettype == null || ttickettype1 == null)) {
            warning.setText("Warning: Please select ticket types.");
            return;
        }

        // Validate ttickettype1 value if child number is not 0
        if (!isChildZero && !ttickettype1.equals("General Child") && !ttickettype1.equals("VIP Child")) {
            warning.setText("Warning: Invalid ticket type for child. Please select 'General Child' or 'VIP Child'.");
            return;
        }

        // Validate ttickettype value if adult number is not 0
        if (!isAdultZero && !ttickettype.equals("General Adult") && !ttickettype.equals("VIP Adult")
                && !ttickettype.equals("College Student")) {
            warning.setText("Warning: Invalid ticket type for adult. Please select 'General Adult', 'VIP Adult', or 'College Student'.");
            return;
        }

        // Validate age, contact, adult, and child fields
        if (!isValidNumber(tage)) {
            warning.setText("Warning: Invalid age. Please enter a valid number.");
            return;
        }

        if (!isValidNumber(tcontact)) {
            warning.setText("Warning: Invalid contact number. Please enter a valid number.");
            return;
        }

        if (!isValidNumber(tadult)) {
            warning.setText("Warning: Invalid adult number. Please enter a valid number.");
            return;
        }

        if (!isValidNumber(tchild)) {
            warning.setText("Warning: Invalid child number. Please enter a valid number.");
            return;
        }

        // Validate name and address fields
        if (!isValidText(tname)) {
            warning.setText("Warning: Invalid name. Please enter a valid name without special characters.");
            return;
        }

        if (!isValidText(taddress)) {
            warning.setText("Warning: Invalid address. Please enter a valid address without special characters.");
            return;
        }

        try (Connection con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1522:xe", "project", "root")) {
            String sql = "SELECT seq.nextval AS tid FROM dual";
            try (PreparedStatement st1 = con.prepareStatement(sql)) {
                try (ResultSet rs1 = st1.executeQuery()) {
                    if (rs1.next()) {
                        int tid = rs1.getInt("tid");

                        sql = "SELECT * FROM adult_price";
                        try (PreparedStatement st2 = con.prepareStatement(sql)) {
                            try (ResultSet rs2 = st2.executeQuery()) {
                                while (rs2.next()) {
                                    int p = rs2.getInt("a_amount");
                                    String type = rs2.getString("adult_type");

                                    if (type.equals(ttickettype)) {
                                        price += p * Integer.parseInt(tadult);
                                        break;
                                    }
                                }
                            }
                        }

                        sql = "SELECT * FROM child_price";
                        try (PreparedStatement st3 = con.prepareStatement(sql)) {
                            try (ResultSet rs3 = st3.executeQuery()) {
                                while (rs3.next()) {
                                    int p = rs3.getInt("c_amount");
                                    String type = rs3.getString("child_type");

                                    if (type.equals(ttickettype1)) {
                                        price += p * Integer.parseInt(tchild);
                                        break;
                                    }
                                }
                            }
                        }

                        // Save data to the database
                        sql = "INSERT INTO book_ticket1(user_id, username, gender, age, contact, address, adult_no, child_no, ticket_type, ticket_type1, price, booking_date) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
                        try (PreparedStatement st4 = con.prepareStatement(sql)) {
                            st4.setInt(1, tid);
                            st4.setString(2, tname);
                            st4.setString(3, tgender);
                            st4.setString(4, tage);
                            st4.setString(5, tcontact);
                            st4.setString(6, taddress);
                            st4.setString(7, tadult);
                            st4.setString(8, tchild);
                            st4.setString(9, ttickettype);
                            st4.setString(10, ttickettype1);
                            st4.setInt(11, price);
                            st4.setDate(12, java.sql.Date.valueOf(LocalDate.now()));
                            st4.executeUpdate();
                        }

                        // Load the next FXML


                        // Load the next FXML and pass ticket information to the ShowticketController
                        FXMLLoader loader = new FXMLLoader(getClass().getResource("/resources/Tickect Display.fxml"));
                        Parent nextRoot = loader.load();
                        ShowticketController controller = loader.getController();

                        // Set the ticket information in the ShowticketController
                        controller.setTicketInfo(tname, tid, tadult, tchild, price, LocalDate.now().toString());

                        // Get the stage from the current scene
                        Stage stage = (Stage) submit.getScene().getWindow();

                        // Create a new scene with the nextRoot
                        Scene nextScene = new Scene(nextRoot);

                        // Set the new scene on the stage
                        stage.setScene(nextScene);
                        stage.setTitle("Next Page");
                        stage.show();
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Error: Failed to save data to the database");
        }
    }

    // Helper method to validate a number
    private boolean isValidNumber(String input) {
        return input.matches("\\d+");
    }

    // Helper method to validate text without special characters
    private boolean isValidText(String input) {
        return input.matches("[a-zA-Z\\s]+");
    }
    
    
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
    }


