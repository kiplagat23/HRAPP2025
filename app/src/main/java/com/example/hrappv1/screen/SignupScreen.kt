package com.example.hrappv1.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
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
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SignupScreen(
    modifier: Modifier = Modifier,
    navController: NavController,
    onBackClick: () -> Unit
) {
    var name by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }
    var number by remember { mutableStateOf("") }
    var idNumber by remember { mutableStateOf("") }
    var date by remember { mutableStateOf("") }
    var errorMessage by remember { mutableStateOf<String?>(null) }
    var isLoading by remember { mutableStateOf(false) }

    val auth: FirebaseAuth = Firebase.auth
    val db = Firebase.firestore

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
                .verticalScroll(rememberScrollState())
                .padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(id = R.drawable.user),
                contentDescription = "App Logo",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(150.dp)
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "Hello there!",
                style = MaterialTheme.typography.headlineMedium,
                color = Color.White // Adjust text color for better visibility
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "Create an account",
                style = MaterialTheme.typography.bodyMedium,
                color = Color.White // Adjust text color for better visibility
            )
            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = name,
                onValueChange = { name = it },
                label = { Text("Name") },
                modifier = Modifier.fillMaxWidth(),
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    focusedBorderColor = Color.White, // Adjust border color
                    unfocusedBorderColor = Color.Gray // Adjust border color
                )
            )
            OutlinedTextField(
                value = number,
                onValueChange = { number = it },
                label = { Text("Phone Number") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
                modifier = Modifier.fillMaxWidth(),
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    focusedBorderColor = Color.White,
                    unfocusedBorderColor = Color.Gray
                )
            )
            OutlinedTextField(
                value = idNumber,
                onValueChange = { idNumber = it },
                label = { Text("ID Number") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                modifier = Modifier.fillMaxWidth(),
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    focusedBorderColor = Color.White,
                    unfocusedBorderColor = Color.Gray
                )
            )
            OutlinedTextField(
                value = date,
                onValueChange = { date = it },
                label = { Text("Date of Birth") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                modifier = Modifier.fillMaxWidth(),
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    focusedBorderColor = Color.White,
                    unfocusedBorderColor = Color.Gray
                )
            )
            OutlinedTextField(
                value = email,
                onValueChange = { email = it },
                label = { Text("Email") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                modifier = Modifier.fillMaxWidth(),
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    focusedBorderColor = Color.White,
                    unfocusedBorderColor = Color.Gray
                )
            )
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
            OutlinedTextField(
                value = confirmPassword,
                onValueChange = { confirmPassword = it },
                label = { Text("Confirm Password") },
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
                    modifier = Modifier.padding(bottom = 8.dp)
                )
            }

            if (isLoading) {
                CircularProgressIndicator(modifier = Modifier.padding(8.dp))
            }
            Spacer(modifier = Modifier.height(50.dp))

            Button(
                onClick = {
                    if (password != confirmPassword) {
                        errorMessage = "Error: Passwords do not match"
                        return@Button
                    }
                    if (name.isBlank() || email.isBlank() || password.isBlank() || number.isBlank() || idNumber.isBlank() || date.isBlank()) {
                        errorMessage = "Error: Please fill in all fields"
                        return@Button
                    }

                    isLoading = true
                    auth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener { authTask ->
                            if (authTask.isSuccessful) {
                                val user = auth.currentUser
                                if (user != null) {
                                    val userData = hashMapOf(
                                        "name" to name,
                                        "email" to email,
                                        "phoneNumber" to number,
                                        "idNumber" to idNumber,
                                        "dateOfBirth" to date
                                    )
                                    db.collection("users").document(user.uid)
                                        .set(userData)
                                        .addOnCompleteListener { dbTask ->
                                            isLoading = false
                                            if (dbTask.isSuccessful) {
                                                navController.navigate("login")
                                            } else {
                                                errorMessage = "Error: ${dbTask.exception?.message}"
                                            }
                                        }
                                }
                            } else {
                                isLoading = false
                                errorMessage = "Error: ${authTask.exception?.message}"
                            }
                        }
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Signup")
            }
        }
    }
}