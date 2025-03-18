package com.example.hrappv1.admin

import android.app.DatePickerDialog
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import java.util.*

@Composable
fun ReportingAnalyticsPage(navController: NavHostController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState()) // Make the page scrollable
            .padding(16.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.Start
    ) {
        Text(
            text = "Reporting & Analytics",
            style = MaterialTheme.typography.headlineMedium,
            color = Color.Black, // Set text color to black
            modifier = Modifier.padding(bottom = 16.dp)
        )

        // Custom Reports Section
        Text(
            text = "Custom Reports",
            style = MaterialTheme.typography.headlineSmall,
            modifier = Modifier.padding(bottom = 8.dp)
        )
        CustomReportsForm()

        Spacer(modifier = Modifier.height(24.dp))

        // Data Analytics Section
        Text(
            text = "Data Analytics",
            style = MaterialTheme.typography.headlineSmall,
            modifier = Modifier.padding(bottom = 8.dp)
        )
        DataAnalyticsForm()

        Spacer(modifier = Modifier.height(24.dp))

        // Scheduled Reporting Section
        Text(
            text = "Scheduled Reporting",
            style = MaterialTheme.typography.headlineSmall,
            modifier = Modifier.padding(bottom = 8.dp)
        )
        ScheduledReportingForm()

        Spacer(modifier = Modifier.height(24.dp))

        // Predictive Analytics Section
        Text(
            text = "Predictive Analytics",
            style = MaterialTheme.typography.headlineSmall,
            modifier = Modifier.padding(bottom = 8.dp)
        )
        PredictiveAnalyticsForm()
    }
}

// Custom Reports Form
@Composable
fun CustomReportsForm() {
    var reportType by remember { mutableStateOf("") }
    var startDate by remember { mutableStateOf("") }
    var endDate by remember { mutableStateOf("") }

    // Date Picker State
    val context = LocalContext.current
    val calendar = Calendar.getInstance()
    val year = calendar.get(Calendar.YEAR)
    val month = calendar.get(Calendar.MONTH)
    val day = calendar.get(Calendar.DAY_OF_MONTH)

    // Start Date Picker
    val startDatePicker = DatePickerDialog(
        context,
        { _, selectedYear, selectedMonth, selectedDay ->
            startDate = "$selectedDay/${selectedMonth + 1}/$selectedYear"
        }, year, month, day
    )

    // End Date Picker
    val endDatePicker = DatePickerDialog(
        context,
        { _, selectedYear, selectedMonth, selectedDay ->
            endDate = "$selectedDay/${selectedMonth + 1}/$selectedYear"
        }, year, month, day
    )

    Column(modifier = Modifier.fillMaxWidth()) {
        // Report Type Field
        FormField(
            value = reportType,
            onValueChange = { reportType = it },
            label = "Report Type",
            keyboardType = KeyboardType.Text
        )
        Spacer(modifier = Modifier.height(8.dp))

        // Start Date Field with Icon
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            FormField(
                value = startDate,
                onValueChange = { startDate = it },
                label = "Start Date",
                keyboardType = KeyboardType.Text,
                modifier = Modifier.weight(1f)
            )
            IconButton(onClick = { startDatePicker.show() }) {
                Icon(
                    imageVector = Icons.Default.DateRange,
                    contentDescription = "Pick Start Date",
                    tint = Color.Black
                )
            }
        }
        Spacer(modifier = Modifier.height(8.dp))

        // End Date Field with Icon
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            FormField(
                value = endDate,
                onValueChange = { endDate = it },
                label = "End Date",
                keyboardType = KeyboardType.Text,
                modifier = Modifier.weight(1f)
            )
            IconButton(onClick = { endDatePicker.show() }) {
                Icon(
                    imageVector = Icons.Default.DateRange,
                    contentDescription = "Pick End Date",
                    tint = Color.Black
                )
            }
        }
        Spacer(modifier = Modifier.height(16.dp))

        // Submit Button
        Button(
            onClick = {
                // Handle form submission
                if (reportType.isNotEmpty() && startDate.isNotEmpty() && endDate.isNotEmpty()) {
                    val reportDetails = """
                        Report Type: $reportType
                        Start Date: $startDate
                        End Date: $endDate
                    """.trimIndent()
                    println("Report Details:\n$reportDetails")
                } else {
                    // Show error: All fields are required
                    println("Error: Please fill all fields.")
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Submit", color = Color.Black) // Set text color to black
        }
    }
}

// Data Analytics Form
@Composable
fun DataAnalyticsForm() {
    var metric by remember { mutableStateOf("") }
    var timePeriod by remember { mutableStateOf("") }

    Column(modifier = Modifier.fillMaxWidth()) {
        FormField(
            value = metric,
            onValueChange = { metric = it },
            label = "Metric",
            keyboardType = KeyboardType.Text
        )
        Spacer(modifier = Modifier.height(8.dp))

        FormField(
            value = timePeriod,
            onValueChange = { timePeriod = it },
            label = "Time Period",
            keyboardType = KeyboardType.Text
        )
        Spacer(modifier = Modifier.height(16.dp))

        // Submit Button
        Button(
            onClick = {
                // Handle form submission
                if (metric.isNotEmpty() && timePeriod.isNotEmpty()) {
                    val analyticsDetails = """
                        Metric: $metric
                        Time Period: $timePeriod
                    """.trimIndent()
                    println("Analytics Details:\n$analyticsDetails")
                } else {
                    // Show error: All fields are required
                    println("Error: Please fill all fields.")
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Submit", color = Color.Black) // Set text color to black
        }
    }
}

// Scheduled Reporting Form
@Composable
fun ScheduledReportingForm() {
    var reportFrequency by remember { mutableStateOf("") }
    var recipients by remember { mutableStateOf("") }

    Column(modifier = Modifier.fillMaxWidth()) {
        FormField(
            value = reportFrequency,
            onValueChange = { reportFrequency = it },
            label = "Report Frequency",
            keyboardType = KeyboardType.Text
        )
        Spacer(modifier = Modifier.height(8.dp))

        FormField(
            value = recipients,
            onValueChange = { recipients = it },
            label = "Recipients",
            keyboardType = KeyboardType.Text
        )
        Spacer(modifier = Modifier.height(16.dp))

        // Submit Button
        Button(
            onClick = {
                // Handle form submission
                if (reportFrequency.isNotEmpty() && recipients.isNotEmpty()) {
                    val scheduledReportDetails = """
                        Report Frequency: $reportFrequency
                        Recipients: $recipients
                    """.trimIndent()
                    println("Scheduled Report Details:\n$scheduledReportDetails")
                } else {
                    // Show error: All fields are required
                    println("Error: Please fill all fields.")
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Submit", color = Color.Black) // Set text color to black
        }
    }
}

// Predictive Analytics Form
@Composable
fun PredictiveAnalyticsForm() {
    var predictionType by remember { mutableStateOf("") }
    var historicalDataRange by remember { mutableStateOf("") }

    Column(modifier = Modifier.fillMaxWidth()) {
        FormField(
            value = predictionType,
            onValueChange = { predictionType = it },
            label = "Prediction Type",
            keyboardType = KeyboardType.Text
        )
        Spacer(modifier = Modifier.height(8.dp))

        FormField(
            value = historicalDataRange,
            onValueChange = { historicalDataRange = it },
            label = "Historical Data Range",
            keyboardType = KeyboardType.Text
        )
        Spacer(modifier = Modifier.height(16.dp))

        // Submit Button
        Button(
            onClick = {
                // Handle form submission
                if (predictionType.isNotEmpty() && historicalDataRange.isNotEmpty()) {
                    val predictiveAnalyticsDetails = """
                        Prediction Type: $predictionType
                        Historical Data Range: $historicalDataRange
                    """.trimIndent()
                    println("Predictive Analytics Details:\n$predictiveAnalyticsDetails")
                } else {
                    // Show error: All fields are required
                    println("Error: Please fill all fields.")
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Submit", color = Color.Black) // Set text color to black
        }
    }
}

// Reusable Form Field Component
@Composable
fun FormField(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    keyboardType: KeyboardType,
    modifier: Modifier = Modifier
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text(label) },
        keyboardOptions = KeyboardOptions(keyboardType = keyboardType),
        modifier = modifier.fillMaxWidth()
    )
}