package com.example.hrappv1.admin

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExpandLess
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp

@Composable
fun LeaveAttendanceManagementPage() {
    var leavePolicyExpanded by remember { mutableStateOf(false) }
    var attendanceRulesExpanded by remember { mutableStateOf(false) }
    var leaveApprovalExpanded by remember { mutableStateOf(false) }
    var attendanceReportsExpanded by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.Start
    ) {
        Text("Leave & Attendance Management", style = MaterialTheme.typography.headlineMedium)

        // Module 1: Leave Policy Configuration
        ExpandableSection(
            title = "Leave Policy Configuration",
            expanded = leavePolicyExpanded,
            onExpandChange = { leavePolicyExpanded = it }
        ) {
            LeavePolicyConfiguration()
        }

        // Module 2: Attendance Rules
        ExpandableSection(
            title = "Attendance Rules",
            expanded = attendanceRulesExpanded,
            onExpandChange = { attendanceRulesExpanded = it }
        ) {
            AttendanceRulesConfiguration()
        }

        // Module 3: Leave Approval Workflow
        ExpandableSection(
            title = "Leave Approval Workflow",
            expanded = leaveApprovalExpanded,
            onExpandChange = { leaveApprovalExpanded = it }
        ) {
            LeaveApprovalWorkflow()
        }

        // Module 4: Attendance Reports
        ExpandableSection(
            title = "Attendance Reports",
            expanded = attendanceReportsExpanded,
            onExpandChange = { attendanceReportsExpanded = it }
        ) {
            AttendanceReports()
        }
    }
}

// Reusable Expandable Section Composable
@Composable
fun ExpandableSection(
    title: String,
    expanded: Boolean,
    onExpandChange: (Boolean) -> Unit,
    content: @Composable () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        shape = MaterialTheme.shapes.medium,
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(text = title, style = MaterialTheme.typography.titleMedium)
                IconButton(onClick = { onExpandChange(!expanded) }) {
                    Icon(
                        imageVector = if (expanded) Icons.Default.ExpandLess else Icons.Default.ExpandMore,
                        contentDescription = if (expanded) "Collapse" else "Expand"
                    )
                }
            }
            if (expanded) {
                content()
            }
        }
    }
}

// Module 1: Leave Policy Configuration
@Composable
fun LeavePolicyConfiguration() {
    var leaveType by remember { mutableStateOf("") }
    var carryoverPolicy by remember { mutableStateOf("") }
    var accrualRate by remember { mutableStateOf("") }

    Column(modifier = Modifier.padding(vertical = 8.dp)) {
        OutlinedTextField(
            value = leaveType,
            onValueChange = { leaveType = it },
            label = { Text("Leave Type") },
            modifier = Modifier.fillMaxWidth()
        )
        OutlinedTextField(
            value = carryoverPolicy,
            onValueChange = { carryoverPolicy = it },
            label = { Text("Carryover Policy") },
            modifier = Modifier.fillMaxWidth()
        )
        OutlinedTextField(
            value = accrualRate,
            onValueChange = { accrualRate = it },
            label = { Text("Accrual Rate") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier.fillMaxWidth()
        )
        Button(onClick = { /* Save leave policy */ }) {
            Text("Save Leave Policy")
        }
    }
}

// Module 2: Attendance Rules
@Composable
fun AttendanceRulesConfiguration() {
    var workingHours by remember { mutableStateOf("") }
    var overtimeRules by remember { mutableStateOf("") }
    var holidays by remember { mutableStateOf("") }

    Column(modifier = Modifier.padding(vertical = 8.dp)) {
        OutlinedTextField(
            value = workingHours,
            onValueChange = { workingHours = it },
            label = { Text("Working Hours") },
            modifier = Modifier.fillMaxWidth()
        )
        OutlinedTextField(
            value = overtimeRules,
            onValueChange = { overtimeRules = it },
            label = { Text("Overtime Rules") },
            modifier = Modifier.fillMaxWidth()
        )
        OutlinedTextField(
            value = holidays,
            onValueChange = { holidays = it },
            label = { Text("Holidays") },
            modifier = Modifier.fillMaxWidth()
        )
        Button(onClick = { /* Save attendance rules */ }) {
            Text("Save Attendance Rules")
        }
    }
}

// Module 3: Leave Approval Workflow
@Composable
fun LeaveApprovalWorkflow() {
    var leaveRequests by remember { mutableStateOf(
        listOf(
            LeaveRequest("Thomas", "2023-10-01", "2023-10-05", "Pending"),
            LeaveRequest(" Javan", "2023-10-02", "2023-10-03", "Pending"),
            LeaveRequest("christine", "2023-10-03", "2023-10-07", "Pending"),
            LeaveRequest("Jasmine", "2023-10-03", "2023-10-07", "Pending")

    )
    )}

    LazyColumn(modifier = Modifier.padding(vertical = 8.dp)) {
        items(leaveRequests) { request ->
            LeaveRequestItem(request) {
                leaveRequests = leaveRequests.map {
                    if (it == request) it.copy(status = "Approved") else it
                }
            }
        }
    }
}

@Composable
fun LeaveRequestItem(request: LeaveRequest, onApprove: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        shape = MaterialTheme.shapes.medium,
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text("Employee: ${request.employeeName}", style = MaterialTheme.typography.bodyMedium)
            Text("Start Date: ${request.startDate}", style = MaterialTheme.typography.bodyMedium)
            Text("End Date: ${request.endDate}", style = MaterialTheme.typography.bodyMedium)
            Text("Status: ${request.status}", style = MaterialTheme.typography.bodyMedium)
            Button(onClick = onApprove) {
                Text("Approve")
            }
        }
    }
}

data class LeaveRequest(
    val employeeName: String,
    val startDate: String,
    val endDate: String,
    val status: String
)

// Module 4: Attendance Reports
@Composable
fun AttendanceReports() {
    var reportType by remember { mutableStateOf("") }

    Column(modifier = Modifier.padding(vertical = 8.dp)) {
        OutlinedTextField(
            value = reportType,
            onValueChange = { reportType = it },
            label = { Text("Report Type") },
            modifier = Modifier.fillMaxWidth()
        )
        Button(onClick = { /* Generate report */ }) {
            Text("Generate Report")
        }
    }
}