package com.example.ui.screens

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.data.FirebaseManager
import com.example.model.UserProfile
import kotlinx.coroutines.launch

@Composable
fun AuthScreen(
    onSignInSuccess: (UserProfile) -> Unit
) {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    var isLoading by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Shivamogga Hidden Gems",
            style = MaterialTheme.typography.headlineLarge,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = "Discover the unseen beauty of Shivamogga with AI.",
            style = MaterialTheme.typography.bodyLarge,
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(48.dp))

        if (isLoading) {
            CircularProgressIndicator()
        } else {
            Button(
                onClick = {
                    isLoading = true
                    scope.launch {
                        // Replace with your Web Client ID from Firebase Console (Authentication -> Sign-in method -> Google -> Web client ID)
                        val serverClientId = "YOUR_WEB_CLIENT_ID" 
                        val result = FirebaseManager.signInWithGoogle(context, serverClientId)
                        result.onSuccess { user ->
                            onSignInSuccess(user)
                        }.onFailure {
                            isLoading = false
                            Toast.makeText(context, "Sign In Failed: ${it.message}", Toast.LENGTH_LONG).show()
                        }
                    }
                },
                modifier = Modifier.fillMaxWidth().height(50.dp)
            ) {
                Text("Sign In with Google")
            }
            Spacer(modifier = Modifier.height(16.dp))
            OutlinedButton(
                onClick = {
                    onSignInSuccess(UserProfile(uid = "guest", displayName = "Guest User", email = "guest@example.com"))
                },
                modifier = Modifier.fillMaxWidth().height(50.dp)
            ) {
                Text("Continue as Guest")
            }
        }
    }
}
