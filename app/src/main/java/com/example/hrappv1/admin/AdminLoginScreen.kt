package com.example.hrappv1.admin

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.hrappv1.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AdminLoginScreen(
    modifier: Modifier = Modifier,
    navController: NavController,
    onBackClick: () -> Unit
) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var errorMessage by remember { mutableStateOf<String?>(null) }

    val auth: FirebaseAuth = Firebase.auth

    // Use a Box to layer the background image and the content
    Box(modifier = Modifier.fillMaxSize()) {
        // Background Image
        Image(
            painter = painterResource(id = R.drawable.bg4), // Replace with your background image resource
            contentDescription = "Background Image",
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop // Ensures the image covers the entire screen
        )

        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(id = R.drawable.adm), // Replace with your admin logo if needed
                contentDescription = "Admin Logo",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
            )
            Spacer(modifier = Modifier.height(20.dp))
            Text(
                text = "Admin Login",
                style = MaterialTheme.typography.headlineMedium,
                color = Color.White // Set text color to white for better visibility
            )
            Spacer(modifier = Modifier.height(20.dp))
            Text(
                text = "Sign in to access the admin dashboard",
                style = MaterialTheme.typography.bodyMedium,
                color = Color.White // Set text color to white
            )
            Spacer(modifier = Modifier.height(20.dp))

            // Email Field
            OutlinedTextField(
                value = email,
                onValueChange = { email = it },
                label = { Text("Admin Email", color = Color.White) },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                modifier = Modifier.fillMaxWidth(),
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    focusedBorderColor = Color.White, // Set border color to white
                    unfocusedBorderColor = Color.Gray // Set border color to gray
                )
            )
            Spacer(modifier = Modifier.height(16.dp))

            // Password Field
            OutlinedTextField(
                value = password,
                onValueChange = { password = it },
                label = { Text("Password", color = Color.White) },
                visualTransformation = PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                modifier = Modifier.fillMaxWidth(),
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    focusedBorderColor = Color.White,
                    unfocusedBorderColor = Color.Gray
                )
            )

            // Error Message
            errorMessage?.let {
                Text(
                    text = it,
                    color = MaterialTheme.colorScheme.error,
                    modifier = Modifier.padding(vertical = 8.dp)
                )
            }

            Spacer(modifier = Modifier.height(50.dp))

            // Login Button
            Button(
                onClick = {
                    if (email.isBlank() || password.isBlank()) {
                        errorMessage = "Please fill in all fields."
                        return@Button
                    }
                    auth.signInWithEmailAndPassword(email, password)
                        .addOnCompleteListener { task ->
                            if (task.isSuccessful) {
                                // Check if the logged-in user is an admin
                                val user = auth.currentUser
                                if (user != null && isAdminUser(user.email)) {
                                    navController.navigate("admin_dashboard")
                                } else {
                                    errorMessage = "You do not have admin privileges."
                                    auth.signOut() // Sign out the user if they are not an admin
                                }
                            } else {
                                errorMessage =
                                    task.exception?.message ?: "Login failed. Please try again."
                            }
                        }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp)
            ) {
                Text("Login as Admin", color = Color.Black) // Set text color to black
            }
        }
    }
}

// Function to check if the user is an admin (replace with your logic)
private fun isAdminUser(email: String?): Boolean {
    // Example: Check if the email is in a predefined list of admin emails
    val adminEmails = listOf("admin1@example.com", "admin2@example.com")
    return email in adminEmails
}