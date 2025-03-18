package com.example.hrappv1.screen

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
fun LoginScreen(
    modifier: Modifier = Modifier,
    navController: NavController,
    onBackClick: () -> Unit
) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var errorMessage by remember { mutableStateOf<String?>(null) }

    val auth: FirebaseAuth = Firebase.auth

    // Use a Box to layer the background image and the content
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        // Background Image
        Image(
            painter = painterResource(id = R.drawable.bg4), // Replace with your background image resource
            contentDescription = "Background Image",
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop // Ensures the image covers the entire screen
        )

        // Content Column
        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(id = R.drawable.user),
                contentDescription = "App Logo",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
            )
            Spacer(modifier = Modifier.height(20.dp))
            Text(
                text = "Welcome back",
                style = MaterialTheme.typography.headlineMedium,
                color = Color.White // Adjust text color for better visibility
            )
            Spacer(modifier = Modifier.height(20.dp))
            Text(
                text = "Sign in to your account",
                style = MaterialTheme.typography.bodyMedium,
                color = Color.White // Adjust text color for better visibility
            )
            Spacer(modifier = Modifier.height(20.dp))

            OutlinedTextField(
                value = email,
                onValueChange = { email = it },
                label = { Text("Email") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                modifier = Modifier.fillMaxWidth(),
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    focusedBorderColor = Color.White, // Adjust border color
                    unfocusedBorderColor = Color.Gray // Adjust border color
                )
            )
            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = password,
                onValueChange = { password = it },
                label = { Text("Password") },
                visualTransformation = PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                modifier = Modifier.fillMaxWidth(),
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    focusedBorderColor = Color.White,
                    unfocusedBorderColor = Color.Gray
                )
            )

            errorMessage?.let {
                Text(
                    text = it,
                    color = MaterialTheme.colorScheme.error,
                    modifier = Modifier.padding(vertical = 8.dp)
                )
            }

            Spacer(modifier = Modifier.height(50.dp))

            Button(
                onClick = {
                    if (email.isBlank() || password.isBlank()) {
                        errorMessage = "Please fill in all fields."
                        return@Button
                    }
                    auth.signInWithEmailAndPassword(email, password)
                        .addOnCompleteListener { task ->
                            if (task.isSuccessful) {
                                navController.navigate("mainpage")
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
                Text("Login")
            }
        }
    }
}