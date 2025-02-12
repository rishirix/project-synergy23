package com.example;

import java.io.IOException;

import com.example.Controllers.LoginController;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {
    private AuthManager authManager = new AuthManager();

    @Override
    public void start(Stage primaryStage) throws Exception {
        authManager = new AuthManager(); // Initialize AuthManager

        // Initialize Firebase
        try {
            FirebaseConfig.initializeFirebase();
        } catch (IOException e) {
            e.printStackTrace();
            return; // Exit if Firebase initialization fails
        }

        // Load Login FXML
        FXMLLoader loader = new FXMLLoader(com.example.Main.class.getResource("/login.fxml"));
        Parent root = loader.load();
        LoginController loginController = loader.getController();
        loginController.setAuthManager(authManager); // Set AuthManager in controller
        
        // Set up the scene
        primaryStage.setTitle("GAR-BOT");
        primaryStage.setScene(new Scene(root, 800, 600));
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}