package com.example.hrappv1.admin

import android.net.Uri
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.Alignment
import androidx.navigation.NavHostController
import com.example.hrappv1.R
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference

@Composable
fun EmployeeProfileManagementApp() {
    MaterialTheme {
        EmployeeProfileManagementPage(navController = NavHostController(LocalContext.current))
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EmployeeProfileManagementPage(navController: NavHostController) {
    val context = LocalContext.current

    // Firestore and Storage instances
    val db = FirebaseFirestore.getInstance()
    val storage = FirebaseStorage.getInstance()
    val storageRef = storage.reference

    // State for document management
    var documentName by remember { mutableStateOf("") }
    var documentUri by remember { mutableStateOf<Uri?>(null) }

    // File picker for document upload
    val documentPickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent(),
        onResult = { uri ->
            documentUri = uri
        }
    )

    // Function to show Toast messages
    fun showToast(message: String) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }

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
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
                .verticalScroll(rememberScrollState()), // Make the page scrollable
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Document Management
            Text(
                text = "Document Management",
                style = MaterialTheme.typography.titleMedium,
                color = Color.White // Set text color to white
            )
            OutlinedTextField(
                value = documentName,
                onValueChange = { documentName = it },
                label = { Text("Document Name", color = Color.White) },
                modifier = Modifier.fillMaxWidth(),
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    focusedBorderColor = Color.White,
                    unfocusedBorderColor = Color.Gray
                )
            )
            Button(
                onClick = {
                    documentPickerLauncher.launch("*/*") // Launch file picker for any file type
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Select Document", color = Color.White) // Set text color to white
            }
            if (documentUri != null) {
                Text(
                    text = "Selected Document: ${documentUri.toString()}",
                    color = Color.White // Set text color to white
                )
            }
            Button(
                onClick = {
                    if (documentUri != null) {
                        uploadDocument(storageRef, documentName, documentUri!!, db, ::showToast)
                        documentName = ""
                        documentUri = null
                    } else {
                        showToast("Please select a document first!")
                    }
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Upload Document", color = Color.White) // Set text color to white
            }
        }
    }
}

// Function to upload a document to Firebase Storage and save its reference in Firestore
private fun uploadDocument(
    storageRef: StorageReference,
    documentName: String,
    documentUri: Uri,
    db: FirebaseFirestore,
    showToast: (String) -> Unit // Corrected parameter type
) {
    if (documentName.isEmpty()) {
        showToast("Document name cannot be empty!")
        return
    }

    val fileRef = storageRef.child("documents/$documentName")
    fileRef.putFile(documentUri)
        .addOnSuccessListener { taskSnapshot ->
            fileRef.downloadUrl.addOnSuccessListener { uri ->
                val document = hashMapOf(
                    "name" to documentName,
                    "url" to uri.toString()
                )
                db.collection("documents")
                    .add(document)
                    .addOnSuccessListener {
                        showToast("Document uploaded successfully!")
                    }
                    .addOnFailureListener { e ->
                        showToast("Error saving document reference: ${e.message}")
                    }
            }
        }
        .addOnFailureListener { e ->
            showToast("Error uploading document: ${e.message}")
        }
}