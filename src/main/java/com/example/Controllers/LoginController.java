package com.example.Controllers;

import java.io.IOException;

import com.example.AuthManager;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.scene.control.Hyperlink;

public class LoginController {
    @FXML
    private TextField emailField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Button loginButton;

    @FXML
    private Label errorLabel; // Optional for displaying error messages

    @FXML
    private Hyperlink signUpLink;

    private AuthManager authManager;

    public void setAuthManager(AuthManager authManager) {
        this.authManager = authManager;
    }

    @FXML
    public void initialize() {
        initializeAuthManager();
        loginButton.setOnAction(e -> handleLogin());
        signUpLink.setOnAction(e -> handleSignUp());
    }

    private void initializeAuthManager(){
        authManager=new AuthManager();
    }

    private void handleSignUp() {
        try {
            FXMLLoader loader = new FXMLLoader(com.example.Controllers.SignupController.class.getResource("/signup.fxml"));
            Parent signupRoot = loader.load();
            SignupController signupController = loader.getController();
            signupController.setAuthManager(this.authManager);
            Stage stage = (Stage) loginButton.getScene().getWindow();
            stage.setScene(new Scene(signupRoot, 800, 600)); // Adjust the size as needed
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @SuppressWarnings("unused")
    private void handleLogin() {
        if (authManager == null) {
            System.err.println("AuthManager is not initialized!"); // Debugging line
            return; // Prevent NullPointerException
        }
        boolean success = authManager.login(emailField.getText(), passwordField.getText());
        if (success) {
            // Load Dashboard FXML
            try {
                FXMLLoader loader = new FXMLLoader(com.example.Controllers.DashboardController.class.getResource("/Dashboard.fxml"));
                Parent dashboardRoot = loader.load();
                DashboardController dashboardController = loader.getController();
                // Set any necessary data in the dashboard controller
                // Switch to the dashboard scene
                Stage stage = (Stage) loginButton.getScene().getWindow();
                stage.setScene(new Scene(dashboardRoot, 1000, 800));
                stage.show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            errorLabel.setText(" Login failed. Check your credentials.");
        }
    }

    public void reInitializeAuthManager(){
        initializeAuthManager();
    }
}