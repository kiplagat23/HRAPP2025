package com.example.hrappv1.screen

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import java.text.SimpleDateFormat
import java.util.*

@Composable
fun LeaveManagementPage(navController: NavController) {
    // State for employee name
    var employeeName by remember { mutableStateOf("") }

    // State for start and end dates
    var startDate by remember { mutableStateOf<Date?>(null) }
    var endDate by remember { mutableStateOf<Date?>(null) }

    // State for showing date pickers
    var showStartDatePicker by remember { mutableStateOf(false) }
    var showEndDatePicker by remember { mutableStateOf(false) }

    // Simulated status from the database
    var leaveStatus by remember { mutableStateOf("Pending") }

    // Simulate fetching status from a database
    LaunchedEffect(Unit) {
        // Simulate a database call
        leaveStatus = fetchLeaveStatusFromDatabase()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.Start
    ) {
        // Title
        Text("Leave & Absence Management", fontSize = 24.sp, fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.height(16.dp))

        // Employee Name Input
        OutlinedTextField(
            value = employeeName,
            onValueChange = { employeeName = it },
            label = { Text("Employee Name") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(16.dp))

        // Start Date Picker
        Row(verticalAlignment = Alignment.CenterVertically) {
            OutlinedTextField(
                value = startDate?.toStringFormatted() ?: "",
                onValueChange = {},
                label = { Text("Start Date") },
                modifier = Modifier.weight(1f),
                readOnly = true
            )
            IconButton(onClick = { showStartDatePicker = true }) {
                Icon(Icons.Default.DateRange, contentDescription = "Select Start Date")
            }
        }
        Spacer(modifier = Modifier.height(8.dp))

        // End Date Picker
        Row(verticalAlignment = Alignment.CenterVertically) {
            OutlinedTextField(
                value = endDate?.toStringFormatted() ?: "",
                onValueChange = {},
                label = { Text("End Date") },
                modifier = Modifier.weight(1f),
                readOnly = true
            )
            IconButton(onClick = { showEndDatePicker = true }) {
                Icon(Icons.Default.DateRange, contentDescription = "Select End Date")
            }
        }
        Spacer(modifier = Modifier.height(16.dp))

        // Leave Status
        Text("Leave Status: $leaveStatus", fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.height(16.dp))

        // Submit Button
        Button(
            onClick = {
                // Handle leave request submission
                submitLeaveRequest(employeeName, startDate, endDate)
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Submit Leave Request")
        }
    }

    // Start Date Picker Dialog
    if (showStartDatePicker) {
        DatePickerDialog(
            onDismissRequest = { showStartDatePicker = false },
            onDateSelected = { date ->
                startDate = date
                showStartDatePicker = false
            }
        )
    }

    // End Date Picker Dialog
    if (showEndDatePicker) {
        DatePickerDialog(
            onDismissRequest = { showEndDatePicker = false },
            onDateSelected = { date ->
                endDate = date
                showEndDatePicker = false
            }
        )
    }
}

// Custom DatePickerDialog Composable
@Composable
fun DatePickerDialog(
    onDismissRequest: () -> Unit,
    onDateSelected: (Date) -> Unit
) {
    val calendar = Calendar.getInstance()
    var selectedDate by remember { mutableStateOf(calendar.time) }

    // State for year, month, and day
    var year by remember { mutableStateOf(calendar.get(Calendar.YEAR)) }
    var month by remember { mutableStateOf(calendar.get(Calendar.MONTH)) }
    var day by remember { mutableStateOf(calendar.get(Calendar.DAY_OF_MONTH)) }

    // List of month names
    val monthNames = listOf(
        "January", "February", "March", "April", "May", "June",
        "July", "August", "September", "October", "November", "December"
    )

    // Calculate the number of days in the selected month and year
    val daysInMonth = remember(year, month) {
        val tempCalendar = Calendar.getInstance()
        tempCalendar.set(year, month, 1)
        tempCalendar.getActualMaximum(Calendar.DAY_OF_MONTH)
    }

    AlertDialog(
        onDismissRequest = onDismissRequest,
        confirmButton = {
            Button(onClick = {
                selectedDate = Calendar.getInstance().apply {
                    set(year, month, day)
                }.time
                onDateSelected(selectedDate)
            }) {
                Text("OK")
            }
        },
        dismissButton = {
            Button(onClick = onDismissRequest) {
                Text("Cancel")
            }
        },
        title = { Text("Select Date") },
        text = {
            Column {
                // Year Picker
                Text("Year: $year")
                Slider(
                    value = year.toFloat(),
                    onValueChange = { year = it.toInt() },
                    valueRange = 1900f..2100f,
                    steps = 200
                )

                // Month Picker
                Text("Month: ${monthNames[month]}")
                Slider(
                    value = month.toFloat(),
                    onValueChange = { month = it.toInt() },
                    valueRange = 0f..11f,
                    steps = 11
                )

                // Day Picker
                Text("Day: $day")
                Slider(
                    value = day.toFloat(),
                    onValueChange = { day = it.toInt() },
                    valueRange = 1f..daysInMonth.toFloat(),
                    steps = daysInMonth - 1
                )
            }
        }
    )
}

// Extension function to format Date as String
fun Date.toStringFormatted(): String {
    val format = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
    return format.format(this)
}

// Simulate fetching leave status from a database
fun fetchLeaveStatusFromDatabase(): String {
    // Simulate a database call
    return "Approved" // Replace with actual database logic
}

// Simulate submitting a leave request
fun submitLeaveRequest(employeeName: String, startDate: Date?, endDate: Date?) {
    // Handle leave request submission logic
    println("Leave Request Submitted for $employeeName from $startDate to $endDate")
}
