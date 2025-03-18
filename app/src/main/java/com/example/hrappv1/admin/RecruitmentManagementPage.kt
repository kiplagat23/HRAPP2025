package com.example.hrappv1.admin

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarToday
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun RecruitmentManagementPage(navController: NavHostController) {
    // State variables for all sections
    var trainingProgramName by remember { mutableStateOf("") }
    var trainingProgramDescription by remember { mutableStateOf("") }
    var trainingProgramFileUri by remember { mutableStateOf<Uri?>(null) }

    var employeeId by remember { mutableStateOf("") }
    var certificationName by remember { mutableStateOf("") }
    var certificationFileUri by remember { mutableStateOf<Uri?>(null) }

    var skillName by remember { mutableStateOf("") }
    var skillLevel by remember { mutableStateOf("") }
    var skillProgressFileUri by remember { mutableStateOf<Uri?>(null) }

    var trainingDate by remember { mutableStateOf("") }
    var trainingTime by remember { mutableStateOf("") }
    var showCalendarDialog by remember { mutableStateOf(false) }
    var trainingMaterialFileUri by remember { mutableStateOf<Uri?>(null) }

    // File picker launchers
    val trainingProgramFilePicker = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent(),
        onResult = { uri -> trainingProgramFileUri = uri }
    )

    val certificationFilePicker = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent(),
        onResult = { uri -> certificationFileUri = uri }
    )

    val skillProgressFilePicker = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent(),
        onResult = { uri -> skillProgressFileUri = uri }
    )

    val trainingMaterialFilePicker = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent(),
        onResult = { uri -> trainingMaterialFileUri = uri }
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Employee Training & Development", style = MaterialTheme.typography.headlineMedium)

        // Training Program Management
        Column(
            modifier = Modifier.padding(vertical = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text("Training Program Management", style = MaterialTheme.typography.titleMedium)

            TextField(
                value = trainingProgramName,
                onValueChange = { trainingProgramName = it },
                label = { Text("Training Program Name") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 8.dp)
            )

            TextField(
                value = trainingProgramDescription,
                onValueChange = { trainingProgramDescription = it },
                label = { Text("Training Program Description") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 8.dp)
            )

            Button(onClick = { trainingProgramFilePicker.launch("*/*") }) {
                Text("Upload Training Program Material")
            }

            trainingProgramFileUri?.let { uri ->
                Text(
                    text = "Selected File: ${uri.lastPathSegment}",
                    modifier = Modifier.padding(top = 8.dp)
                )
            }
        }

        // Certification Management
        Column(
            modifier = Modifier.padding(vertical = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text("Certification Management", style = MaterialTheme.typography.titleMedium)

            TextField(
                value = employeeId,
                onValueChange = { employeeId = it },
                label = { Text("Employee ID") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 8.dp)
            )

            TextField(
                value = certificationName,
                onValueChange = { certificationName = it },
                label = { Text("Certification Name") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 8.dp)
            )

            Button(onClick = { certificationFilePicker.launch("*/*") }) {
                Text("Upload Certification File")
            }

            certificationFileUri?.let { uri ->
                Text(
                    text = "Selected File: ${uri.lastPathSegment}",
                    modifier = Modifier.padding(top = 8.dp)
                )
            }
        }

        // Skill Development Tracking
        Column(
            modifier = Modifier.padding(vertical = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text("Skill Development Tracking", style = MaterialTheme.typography.titleMedium)

            TextField(
                value = skillName,
                onValueChange = { skillName = it },
                label = { Text("Skill Name") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 8.dp)
            )

            TextField(
                value = skillLevel,
                onValueChange = { skillLevel = it },
                label = { Text("Skill Level") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 8.dp)
            )

            Button(onClick = { skillProgressFilePicker.launch("*/*") }) {
                Text("Upload Skill Progress Material")
            }

            skillProgressFileUri?.let { uri ->
                Text(
                    text = "Selected File: ${uri.lastPathSegment}",
                    modifier = Modifier.padding(top = 8.dp)
                )
            }
        }

        // Training Scheduling
        Column(
            modifier = Modifier.padding(vertical = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text("Training Scheduling", style = MaterialTheme.typography.titleMedium)

            // Training Date Picker
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 8.dp)
            ) {
                TextField(
                    value = trainingDate,
                    onValueChange = { trainingDate = it },
                    label = { Text("Training Date") },
                    modifier = Modifier.weight(1f)
                )
                IconButton(onClick = { showCalendarDialog = true }) {
                    Icon(Icons.Filled.CalendarToday, contentDescription = "Select Date")
                }
            }



            // Training Time Input
            TextField(
                value = trainingTime,
                onValueChange = { trainingTime = it },
                label = { Text("Training Time") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 8.dp)
            )

            // File Upload for Training Material
            Button(onClick = { trainingMaterialFilePicker.launch("*/*") }) {
                Text("Upload Training Material")
            }

            trainingMaterialFileUri?.let { uri ->
                Text(
                    text = "Selected File: ${uri.lastPathSegment}",
                    modifier = Modifier.padding(top = 8.dp)
                )
            }
        }

        // Single Submit Button at the Bottom
        Button(
            onClick = {
                // Handle submission for all sections
                createTrainingProgram(trainingProgramName, trainingProgramDescription, trainingProgramFileUri)
                uploadCertification(employeeId, certificationName, certificationFileUri)
                trackSkillDevelopment(skillName, skillLevel, skillProgressFileUri)
                scheduleTraining(trainingDate, trainingTime, trainingMaterialFileUri)
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp)
        ) {
            Text("Submit All")
        }
    }
}

// Functions to handle submission
private fun createTrainingProgram(name: String, description: String, fileUri: Uri?) {
    println("Training Program Created: $name - $description")
    fileUri?.let { uri ->
        println("Training Program Material: $uri")
    }
}

private fun uploadCertification(employeeId: String, certificationName: String, fileUri: Uri?) {
    println("Certification Uploaded: Employee $employeeId -> $certificationName")
    fileUri?.let { uri ->
        println("Certification File: $uri")
    }
}

private fun trackSkillDevelopment(skillName: String, skillLevel: String, fileUri: Uri?) {
    println("Skill Tracked: $skillName - Level $skillLevel")
    fileUri?.let { uri ->
        println("Skill Progress Material: $uri")
    }
}

private fun scheduleTraining(date: String, time: String, fileUri: Uri?) {
    println("Training Scheduled: $date at $time")
    fileUri?.let { uri ->
        println("Training Material: $uri")
    }
}

