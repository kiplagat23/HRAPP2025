package com.example.hrappv1.screen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController


@Composable
fun SelfServicePortalPage(navController: NavController) {
    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(scrollState)
            .padding(16.dp)
    ) {
        IconButton(
            onClick = { navController.popBackStack() },
            modifier = Modifier.align(Alignment.Start)
        ) {
            Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "Back")
        }

        Text(
            text = "Employee Self-Service Portal (ESS)",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(16.dp))



        Spacer(modifier = Modifier.height(24.dp))

        ProfileUpdatesSection()
        PayslipAndTaxDocumentsSection()
        CompanyAnnouncementsSection()
        RequestsAndInquiriesSection()
    }
}

@Composable
fun ProfileUpdatesSection() {
    SectionTitle("💼 Profile Updates")
    Text(
        text = "Update personal details, addresses, and emergency contacts.",
        fontSize = 14.sp,
        modifier = Modifier.padding(bottom = 8.dp)
    )

    Button(
        onClick = { /* Handle navigation to profile update screen */ },
        modifier = Modifier.fillMaxWidth()
    ) {
        Text("Update Profile")
    }
    Spacer(modifier = Modifier.height(16.dp))
}

@Composable
fun PayslipAndTaxDocumentsSection() {
    SectionTitle("📂 Payslip & Tax Documents")
    Text(
        text = "Download salary slips, tax forms, and other HR documents.",
        fontSize = 14.sp,
        modifier = Modifier.padding(bottom = 8.dp)
    )

    val documentTypes = listOf("Payslip", "Tax Form", "Employment Contract")
    var selectedDocumentType by remember { mutableStateOf(documentTypes[0]) }
    var expanded by remember { mutableStateOf(false) }

    Box {
        Button(onClick = { expanded = true }) {
            Text("Select Document: $selectedDocumentType")
        }
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            documentTypes.forEach { type ->
                DropdownMenuItem(
                    text = { Text(type) },
                    onClick = {
                        selectedDocumentType = type
                        expanded = false
                    }
                )
            }
        }
    }
    Spacer(modifier = Modifier.height(8.dp))
    Button(
        onClick = { /* Handle document download */ },
        modifier = Modifier.fillMaxWidth()
    ) {
        Text("Download $selectedDocumentType")
    }
    Spacer(modifier = Modifier.height(16.dp))
}

@Composable
fun CompanyAnnouncementsSection() {
    SectionTitle("📢 Company Announcements")
    Text(
        text = "Receive updates on policies, holidays, and events.",
        fontSize = 14.sp,
        modifier = Modifier.padding(bottom = 8.dp)
    )

    val announcements = listOf(
        "New policy on remote work starting next month.",
        "Annual company party on December 15th.",
        "Holiday schedule for the upcoming year."
    )

    announcements.forEach { announcement ->
        Text(
            text = "• $announcement",
            fontSize = 14.sp,
            modifier = Modifier.padding(vertical = 4.dp)
        )
    }
    Spacer(modifier = Modifier.height(16.dp))
}

@Composable
fun RequestsAndInquiriesSection() {
    SectionTitle("📑 Requests & Inquiries")
    Text(
        text = "Submit requests for job letters, ID replacements, etc.",
        fontSize = 14.sp,
        modifier = Modifier.padding(bottom = 8.dp)
    )

    var requestDescription by remember { mutableStateOf("") }

    OutlinedTextField(
        value = requestDescription,
        onValueChange = { requestDescription = it },
        label = { Text("Enter your request") },
        modifier = Modifier.fillMaxWidth(),
        maxLines = 3
    )

    Button(
        onClick = { /* Handle request submission */ },
        modifier = Modifier.fillMaxWidth().padding(top = 8.dp)
    ) {
        Text("Submit Request")
    }
    Spacer(modifier = Modifier.height(16.dp))
}

@Composable
fun SectionTitle(title: String) {
    Text(
        text = title,
        fontSize = 18.sp,
        fontWeight = FontWeight.Bold,
        modifier = Modifier.padding(bottom = 8.dp)
    )
}