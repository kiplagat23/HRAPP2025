package com.example.hrappv1.screen

import android.net.Uri
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
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
fun ExitOffboardingPage(navController: NavController) {
    var resignationLetterUri by remember { mutableStateOf<Uri?>(null) }
    var noticePeriodStartDate by remember { mutableStateOf("") }
    var noticePeriodEndDate by remember { mutableStateOf("") }
    var exitInterviewFeedback by remember { mutableStateOf("") }
    val assetChecklist = remember {
        mutableStateListOf(
            "Laptop" to false,
            "ID Card" to false,
            "Access Badge" to false,
            "Company Phone" to false
        )
    }
    var finalSalary by remember { mutableStateOf("") }
    var benefits by remember { mutableStateOf("") }
    var taxDeductions by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.Start
    ) {
        Text("Exit & Offboarding Management", fontSize = 24.sp, fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.height(16.dp))

        Text("📅 Resignation & Notice Period", fontSize = 18.sp, fontWeight = FontWeight.SemiBold)
        Spacer(modifier = Modifier.height(8.dp))

        DocumentUploadSection(
            documentType = "Resignation Letter",
            selectedFileUri = resignationLetterUri,
            onUploadClick = { /* Launch file picker */ }
        )
        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = noticePeriodStartDate,
            onValueChange = { noticePeriodStartDate = it },
            label = { Text("Notice Period Start Date") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = noticePeriodEndDate,
            onValueChange = { noticePeriodEndDate = it },
            label = { Text("Notice Period End Date") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(16.dp))

        Text("📋 Exit Interviews", fontSize = 18.sp, fontWeight = FontWeight.SemiBold)
        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = exitInterviewFeedback,
            onValueChange = { exitInterviewFeedback = it },
            label = { Text("Feedback / Reasons for Leaving") },
            modifier = Modifier
                .fillMaxWidth()
                .height(100.dp)
        )
        Spacer(modifier = Modifier.height(16.dp))

        Text("💳 Asset Return & Clearance", fontSize = 18.sp, fontWeight = FontWeight.SemiBold)
        Spacer(modifier = Modifier.height(8.dp))

        assetChecklist.forEachIndexed { index, (asset, isReturned) ->
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Checkbox(
                    checked = isReturned,
                    onCheckedChange = { isChecked ->
                        assetChecklist[index] = asset to isChecked
                    }
                )
                Text(text = asset, modifier = Modifier.padding(start = 8.dp))
            }
        }
        Spacer(modifier = Modifier.height(16.dp))

        Text("📂 Final Settlements", fontSize = 18.sp, fontWeight = FontWeight.SemiBold)
        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = finalSalary,
            onValueChange = { finalSalary = it },
            label = { Text("Final Salary") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = benefits,
            onValueChange = { benefits = it },
            label = { Text("Benefits") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = taxDeductions,
            onValueChange = { taxDeductions = it },
            label = { Text("Tax Deductions") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                handleOffboardingSubmission(
                    resignationLetterUri,
                    noticePeriodStartDate,
                    noticePeriodEndDate,
                    exitInterviewFeedback,
                    assetChecklist,
                    finalSalary,
                    benefits,
                    taxDeductions
                )
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Submit Offboarding Details")
        }
    }
}

fun handleOffboardingSubmission(
    resignationLetterUri: Uri?,
    noticePeriodStartDate: String,
    noticePeriodEndDate: String,
    exitInterviewFeedback: String,
    assetChecklist: List<Pair<String, Boolean>>,
    finalSalary: String,
    benefits: String,
    taxDeductions: String
) {
    println("Resignation Letter: ${resignationLetterUri?.lastPathSegment}")
    println("Notice Period: $noticePeriodStartDate to $noticePeriodEndDate")
    println("Exit Interview Feedback: $exitInterviewFeedback")
    println("Assets Returned: $assetChecklist")
    println("Final Salary: $finalSalary")
    println("Benefits: $benefits")
    println("Tax Deductions: $taxDeductions")
}