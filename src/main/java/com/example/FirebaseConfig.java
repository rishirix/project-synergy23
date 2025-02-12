package com.example;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.IOException;
import java.io.InputStream;

public class FirebaseConfig {
    private static FirebaseAuth auth;

    public static void initializeFirebase() throws IOException {
        // Check if Firebase is already initialized to prevent multiple initializations
        if (FirebaseApp.getApps().isEmpty()) {
            // Load the JSON file from resources
            try (InputStream serviceAccount = FirebaseConfig.class.getResourceAsStream("/synergy-niituni-firebase-adminsdk-f9nqd-078e5b0e67.json")) {
                if (serviceAccount == null) {
                    throw new IOException("Firebase service account JSON file not found in resources.");
                }

                FirebaseOptions options = FirebaseOptions.builder()
                        .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                        .setDatabaseUrl("https://synergy-niituni-default-rtdb.asia-southeast1.firebasedatabase.app")
                        .build();

                FirebaseApp.initializeApp(options);
                auth = FirebaseAuth.getInstance();
            }
        }
    }

    public static DatabaseReference getDatabaseReference(String path) {
        return FirebaseDatabase.getInstance().getReference(path);
    }

    public static FirebaseAuth getAuthInstance() {
        return auth;
    }
}
