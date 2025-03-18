package com.example.hrappv1.admin

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.hrappv1.R // Replace with your actual package name
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AdminDashboard(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    onLogoutClick: () -> Unit
) {
    var isDrawerOpen by remember { mutableStateOf(false) }
    var selectedItem by remember { mutableStateOf("") } // Set initial selectedItem to empty

    // Admin-specific drawer items
    val drawerItems = listOf(
        "User Role Management",
        "Employee Profile & Data Management",
        "Payroll Management & Configuration",
        "Leave & Attendance Management",
        "Employee Performance Tracking & Appraisals",
        "Employee Training & Development",
        "Compliance & Policy Management",
        "Reporting & Analytics",
        "Recruitment Management"
    )

    val drawerBackgroundColor = Color(0xFFF5F5F5)
    val mainBackgroundColor = Color(0xFFE3F2FD)

    Row(modifier = Modifier.fillMaxSize()) {
        if (isDrawerOpen) {
            Column(
                modifier = Modifier
                    .width(280.dp)
                    .fillMaxHeight()
                    .background(drawerBackgroundColor)
                    .padding(16.dp)
            ) {
                Text(
                    text = "Admin Dashboard",
                    style = MaterialTheme.typography.headlineMedium,
                    modifier = Modifier.padding(vertical = 16.dp)
                )
                drawerItems.forEach { item ->
                    DrawerItem(
                        text = item,
                        selected = item == selectedItem,
                        onClick = {
                            selectedItem = item
                            isDrawerOpen = false
                            when (item) {
                                "User Role Management" -> navController.navigate("user_role_management")
                                "Employee Profile & Data Management" -> navController.navigate("employee_profile_management")
                                "Payroll Management & Configuration" -> navController.navigate("payroll_management")
                                "Leave & Attendance Management" -> navController.navigate("leave_attendance_management")
                                "Employee Performance Tracking & Appraisals" -> navController.navigate("performance_tracking")
                                "Employee Training & Development" -> navController.navigate("training_development")
                                "Compliance & Policy Management" -> navController.navigate("compliance_policy_management")
                                "Reporting & Analytics" -> navController.navigate("reporting_analytics")
                                "Recruitment Management" -> navController.navigate("recruitment_management")
                            }
                        }
                    )
                }
            }
        }

        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(mainBackgroundColor)
        ) {
            Scaffold(
                topBar = {
                    TopAppBar(
                        title = { Text("Admin Dashboard", fontSize = 20.sp) },
                        navigationIcon = {
                            IconButton(onClick = { isDrawerOpen = !isDrawerOpen }) {
                                Icon(
                                    imageVector = Icons.Default.Menu,
                                    contentDescription = "Menu"
                                )
                            }
                        },
                        actions = {
                            IconButton(onClick = onLogoutClick) {
                                Icon(
                                    imageVector = Icons.Default.ExitToApp,
                                    contentDescription = "Logout"
                                )
                            }
                        }
                    )
                }
            ) { paddingValues ->
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues)
                ) {
                    if (selectedItem.isEmpty()) {
                        // Display the centered image with adjustable size
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .wrapContentSize(Alignment.Center)
                        ) {
                            Image(
                                painter = painterResource(id = R.drawable.adm), // Replace with your image resource
                                contentDescription = "Default Image",
                                modifier = Modifier
                                    .size(600.dp) // Adjust size as needed
                                    .clickable { /* Handle image click if needed */ },
                                contentScale = ContentScale.Fit // Ensures the image fits within the specified size
                            )
                        }
                    } else {
                        // Display the selected item's content
                        when (selectedItem) {
                            "User Role Management" -> UserRoleManagementPage(navController = navController)
                            "Employee Profile & Data Management" -> EmployeeProfileManagementPage(navController = navController)
                            "Payroll Management & Configuration" -> PayrollManagementPage(navController = navController)
                            "Leave & Attendance Management" -> LeaveAttendanceManagementPage()
                            "Employee Performance Tracking & Appraisals" -> PerformanceTrackingPage(navController = navController)
                            "Employee Training & Development" -> TrainingDevelopmentPage(navController = navController)
                            "Compliance & Policy Management" -> CompliancePolicyManagementPage(navController = navController)
                            "Reporting & Analytics" -> ReportingAnalyticsPage(navController = navController)
                            "Recruitment Management" -> RecruitmentManagementPage(navController = navController)
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun DrawerItem(
    text: String,
    selected: Boolean,
    onClick: () -> Unit
) {
    val backgroundColor = if (selected) MaterialTheme.colorScheme.primaryContainer else Color.Transparent
    val contentColor = if (selected) MaterialTheme.colorScheme.onPrimaryContainer else MaterialTheme.colorScheme.onSurface

    Surface(
        color = backgroundColor,
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
    ) {
        Text(
            text = text,
            color = contentColor,
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier
                .padding(16.dp)
                .clickable(onClick = onClick)
        )
    }
}