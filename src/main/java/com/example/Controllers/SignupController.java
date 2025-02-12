package com.example.Controllers;

import java.io.IOException;

import com.example.AuthManager;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class SignupController {
    @FXML
    private TextField emailField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Button signupButton;

    @FXML
    private Button goBackButton;

    private AuthManager authManager;

    public void setAuthManager(AuthManager authManager) {
        this.authManager = authManager;
    }

    @FXML
    public void initialize() {
        signupButton.setOnAction(e -> handleSignup());
        goBackButton.setOnAction(e -> handleGoBack());
    }

    private void handleSignup() {
        authManager.signUp(emailField.getText(), passwordField.getText());
        // Provide feedback to the user
        System.out.println("Signup process initiated. Please check your email.");
    }

    private void handleGoBack() {
        try {
            FXMLLoader loader = new FXMLLoader(com.example.Controllers.LoginController.class.getResource("/login.fxml"));
            Parent loginRoot = loader.load();
            LoginController loginController = loader.getController();
            loginController.setAuthManager(this.authManager);
            Stage stage = (Stage) goBackButton.getScene().getWindow();
            stage.setScene(new Scene(loginRoot, 800, 600)); // Adjust the size as needed
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}