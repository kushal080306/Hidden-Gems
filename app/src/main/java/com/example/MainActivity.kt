package com.example

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import com.example.data.FirebaseManager
import com.example.model.UserProfile
import com.example.ui.screens.AuthScreen
import com.example.ui.screens.MainApp
import com.example.ui.theme.MyApplicationTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MyApplicationTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    var user by remember { mutableStateOf<UserProfile?>(FirebaseManager.getCurrentUser()) }

                    // If Firebase is not configured (e.g., missing google-services.json), bypass login for preview
                    if (FirebaseManager.auth == null) {
                        user = UserProfile(uid = "guest", displayName = "Guest User", email = "guest@example.com")
                    }

                    if (user == null) {
                        AuthScreen(onSignInSuccess = { user = it })
                    } else {
                        MainApp(user = user!!, onSignOut = { user = null })
                    }
                }
            }
        }
    }
}
