package com.example.hrappv1.admin

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.hrappv1.R

@Composable
fun CompliancePolicyManagementPage(navController: NavHostController) {
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
                .verticalScroll(rememberScrollState()) // Make the page scrollable
                .padding(16.dp),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.Start
        ) {
            Text(
                text = "Compliance & Policy Management",
                style = MaterialTheme.typography.headlineMedium,
                color = Color.White, // Set text color to white for better visibility
                modifier = Modifier.padding(bottom = 16.dp)
            )

            // HR Policies Management
            Text(
                text = "HR Policies Management",
                style = MaterialTheme.typography.headlineSmall,
                color = Color.White, // Set text color to white
                modifier = Modifier.padding(bottom = 8.dp)
            )
            HRPoliciesForm()

            Spacer(modifier = Modifier.height(24.dp))

            // Compliance Checks
            Text(
                text = "Compliance Checks",
                style = MaterialTheme.typography.headlineSmall,
                color = Color.White, // Set text color to white
                modifier = Modifier.padding(bottom = 8.dp)
            )
            ComplianceChecksForm()

            Spacer(modifier = Modifier.height(24.dp))

            // Audit Trails
            Text(
                text = "Audit Trails",
                style = MaterialTheme.typography.headlineSmall,
                color = Color.White, // Set text color to white
                modifier = Modifier.padding(bottom = 8.dp)
            )
            AuditTrailsForm()

            Spacer(modifier = Modifier.height(24.dp))

            // Legal Document Management
            Text(
                text = "Legal Document Management",
                style = MaterialTheme.typography.headlineSmall,
                color = Color.White, // Set text color to white
                modifier = Modifier.padding(bottom = 8.dp)
            )
            LegalDocumentsForm()
        }
    }
}

@Composable
fun HRPoliciesForm() {
    var policyTitle by remember { mutableStateOf("") }
    var policyDescription by remember { mutableStateOf("") }

    Column(modifier = Modifier.fillMaxWidth()) {
        FormField(
            value = policyTitle,
            onValueChange = { policyTitle = it },
            label = "Policy Title",
            keyboardType = KeyboardType.Text,
            textColor = Color.White // Set text color to white
        )
        Spacer(modifier = Modifier.height(8.dp))
        FormField(
            value = policyDescription,
            onValueChange = { policyDescription = it },
            label = "Policy Description",
            keyboardType = KeyboardType.Text,
            textColor = Color.White // Set text color to white
        )
        Spacer(modifier = Modifier.height(16.dp))
        Button(
            onClick = { /* Save policy logic */ },
            modifier = Modifier.align(Alignment.End)
        ) {
            Text("Save Policy", color = Color.Black) // Set text color to black
        }
    }
}

@Composable
fun ComplianceChecksForm() {
    var checkName by remember { mutableStateOf("") }
    var checkStatus by remember { mutableStateOf("") }

    Column(modifier = Modifier.fillMaxWidth()) {
        FormField(
            value = checkName,
            onValueChange = { checkName = it },
            label = "Check Name",
            keyboardType = KeyboardType.Text,
            textColor = Color.White // Set text color to white
        )
        Spacer(modifier = Modifier.height(8.dp))
        FormField(
            value = checkStatus,
            onValueChange = { checkStatus = it },
            label = "Check Status",
            keyboardType = KeyboardType.Text,
            textColor = Color.White // Set text color to white
        )
        Spacer(modifier = Modifier.height(16.dp))
        Button(
            onClick = { /* Save compliance check logic */ },
            modifier = Modifier.align(Alignment.End)
        ) {
            Text("Save Compliance Check", color = Color.Black) // Set text color to black
        }
    }
}

@Composable
fun AuditTrailsForm() {
    var action by remember { mutableStateOf("") }
    var timestamp by remember { mutableStateOf("") }

    Column(modifier = Modifier.fillMaxWidth()) {
        FormField(
            value = action,
            onValueChange = { action = it },
            label = "Action",
            keyboardType = KeyboardType.Text,
            textColor = Color.White // Set text color to white
        )
        Spacer(modifier = Modifier.height(8.dp))
        FormField(
            value = timestamp,
            onValueChange = { timestamp = it },
            label = "Timestamp",
            keyboardType = KeyboardType.Text,
            textColor = Color.White // Set text color to white
        )
        Spacer(modifier = Modifier.height(16.dp))
        Button(
            onClick = { /* Save audit trail logic */ },
            modifier = Modifier.align(Alignment.End)
        ) {
            Text("Save Audit Trail", color = Color.Black) // Set text color to black
        }
    }
}

@Composable
fun LegalDocumentsForm() {
    var documentName by remember { mutableStateOf("") }
    var documentType by remember { mutableStateOf("") }
    var selectedFileUri by remember { mutableStateOf<Uri?>(null) }

    // File Picker Launcher
    val filePickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent(),
        onResult = { uri ->
            selectedFileUri = uri
        }
    )

    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally // Center all child elements horizontally
    ) {
        // Document Name Field
        FormField(
            value = documentName,
            onValueChange = { documentName = it },
            label = "Document Name",
            keyboardType = KeyboardType.Text,
            textColor = Color.White // Set text color to white
        )
        Spacer(modifier = Modifier.height(8.dp))

        // Document Type Field
        FormField(
            value = documentType,
            onValueChange = { documentType = it },
            label = "Document Type",
            keyboardType = KeyboardType.Text,
            textColor = Color.White // Set text color to white
        )
        Spacer(modifier = Modifier.height(8.dp))

        // File Upload Button
        Button(
            onClick = {
                filePickerLauncher.launch("*/*") // Open file picker for all file types
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Choose File from Local Storage", color = Color.Black) // Set text color to black
        }

        // Display selected file name
        if (selectedFileUri != null) {
            Text(
                text = "Selected File: ${selectedFileUri?.lastPathSegment}",
                modifier = Modifier.padding(top = 8.dp),
                color = Color.White // Set text color to white
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Submit Button
        Button(
            onClick = {
                // Handle form submission
                if (documentName.isNotEmpty() && documentType.isNotEmpty() && selectedFileUri != null) {
                    // Save document logic with file upload
                    val fileDetails = """
                        Document Name: $documentName
                        Document Type: $documentType
                        File URI: $selectedFileUri
                    """.trimIndent()
                    println("Document Details:\n$fileDetails")
                } else {
                    // Show error: All fields are required
                    println("Error: Please fill all fields and select a file.")
                }
            },
            modifier = Modifier.fillMaxWidth(0.8f) // Set button width to 80% of parent width
        ) {
            Text("Submit", color = Color.Black) // Set text color to black
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FormField(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    keyboardType: KeyboardType,
    textColor: Color = Color.Black // Default text color
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text(label, color = textColor) },
        keyboardOptions = KeyboardOptions(keyboardType = keyboardType),
        modifier = Modifier.fillMaxWidth(),
        colors = TextFieldDefaults.outlinedTextFieldColors(
            focusedBorderColor = Color.White, // Set border color
            unfocusedBorderColor = Color.Gray // Set border color
        )
    )
}