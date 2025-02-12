package com.example.Controllers;

import java.io.IOException;
import com.example.FirebaseConfig;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import com.example.DashboardData;

public class DashboardController {
    @FXML
    private AnchorPane rootPane;

    @FXML
    private Button chargeButton;

    @FXML
    private Button clearButton;

    @FXML
    private Button logsButton;

    @FXML
    private Label overviewLabel;

    @FXML
    private Button logoutButton;

    private DatabaseReference databaseReference;

    @FXML
    private Text batteryStatusLabel;

    @FXML
    private Text capacityLabel;

    @FXML
    private Text statusLabel;


    // Initialize method to set up event handlers
    @FXML
    public void initialize() {
        chargeButton.setOnAction(e -> handleCharge());
        clearButton.setOnAction(e -> handleClear());
        logsButton.setOnAction(e -> handleLogs());
        logoutButton.setOnAction(e -> handleLogout());
        databaseReference = FirebaseConfig.getDatabaseReference("dashboardData");
        fetchDataFromFirebase();
    }

    private void fetchDataFromFirebase() {
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                DashboardData dashboardData = dataSnapshot.getValue(DashboardData.class);
                if (dashboardData != null) {
                    updateUI(dashboardData);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.err.println("Error fetching data: " + databaseError.getMessage());
            }
        });
    }

    private void updateUI(DashboardData dashboardData) {
        batteryStatusLabel.setText(dashboardData.getBatteryStatus());
        capacityLabel.setText(dashboardData.getCapacity());
        statusLabel.setText(dashboardData.getStatus());
    }

    private void handleCharge() {
        // Implement charge logic
        System.out.println("Charge button clicked");
    }

    private void handleClear() {
        // Implement clear logic
        System.out.println("Clear button clicked");
    }

    private void handleLogs() {
        // Implement logs logic
        System.out.println("Logs button clicked");
    }

    private void handleLogout() {
        try {
            // Load the login FXML
            FXMLLoader loader = new FXMLLoader(com.example.Controllers.DashboardController.class.getResource("/login.fxml"));
            Parent loginRoot = loader.load();
            LoginController loginController=loader.getController();
            loginController.reInitializeAuthManager();
            Stage stage = (Stage) logoutButton.getScene().getWindow();
            stage.setScene(new Scene(loginRoot, 800, 600)); // Set the size of the login window
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}