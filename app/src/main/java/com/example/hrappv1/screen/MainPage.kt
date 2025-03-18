package com.example.hrappv1.screen

import TransfersPromotionsPage
import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.hrappv1.admin.*

/**
 * Main page of the HR Application.
 * This screen provides navigation to different HR functions using a custom Drawer.
 *
 * @param modifier Modifier for styling the layout.
 * @param navController NavController for navigation.
 * @param onLogoutClick Callback for handling logout.
 */
@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("ComposableDestinationInComposeScope")
@Composable
fun MainPage(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    onLogoutClick: () -> Unit
) {
    // State to control the drawer's open/close state
    var isDrawerOpen by remember { mutableStateOf(false) }

    // State to track the selected drawer item
    var selectedItem by remember { mutableStateOf("Employee Records") }

    // List of drawer items
    val drawerItems = listOf(
        "Employee Records",
        "Attendance & Time Tracking",
        "Leave & Absence Management",
        "Employee Self-Service Portal",
        "Employee Transfers & Promotions",
        "Exit & Offboarding Management"
    )

    // Background colors
    val drawerBackgroundColor = Color(0xFFF5F5F5) // Light gray for the drawer
    val mainBackgroundColor = Color(0xFFE3F2FD)   // Light blue for the main content

    // Custom drawer implementation
    Row(modifier = Modifier.fillMaxSize()) {
        // Drawer content
        if (isDrawerOpen) {
            Column(
                modifier = Modifier
                    .width(280.dp) // Set a fixed width for the drawer
                    .fillMaxHeight()
                    .background(drawerBackgroundColor)
                    .padding(16.dp)
            ) {
                // Drawer header
                Text(
                    text = "HR Management",
                    style = MaterialTheme.typography.headlineMedium,
                    modifier = Modifier.padding(vertical = 16.dp)
                )

                // Drawer items
                drawerItems.forEach { item ->
                    DrawerItem(
                        text = item,
                        selected = item == selectedItem,
                        onClick = {
                            selectedItem = item
                            isDrawerOpen = false // Close the drawer after selecting an item
                            // Navigate to the corresponding screen
                            when (item) {
                                "Employee Records" -> navController.navigate("employee_records")
                                "Attendance & Time Tracking" -> navController.navigate("attendance_tracking")
                                "Leave & Absence Management" -> navController.navigate("leave_absence_management")
                                "Employee Self-Service Portal" -> navController.navigate("self_service_portal")
                                "Employee Transfers & Promotions" -> navController.navigate("transfers_promotions")
                                "Exit & Offboarding Management" -> navController.navigate("exit_offboarding")
                            }
                        }
                    )
                }
            }
        }

        // Main content
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(mainBackgroundColor)
        ) {
            Scaffold(
                topBar = {
                    TopAppBar(
                        title = { Text("HR Management", fontSize = 20.sp) },
                        navigationIcon = {
                            IconButton(onClick = { isDrawerOpen = !isDrawerOpen }) {
                                Icon(
                                    imageVector = Icons.Default.Menu, // Menu icon to open the drawer
                                    contentDescription = "Menu"
                                )
                            }
                        },
                        actions = {
                            // Logout button
                            IconButton(onClick = onLogoutClick) {
                                Icon(
                                    imageVector = Icons.Default.ArrowBack, // Use a proper icon
                                    contentDescription = "Logout"
                                )
                            }
                        }
                    )
                }
            ) { paddingValues ->
                // Display the selected page based on the drawer item
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues)
                ) {
                    when (selectedItem) {
                        "Employee Records" -> EmployeeRecordsPage(navController = navController)
                        "Attendance & Time Tracking" -> AttendanceTrackingPage(navController = navController)
                        "Leave & Absence Management" -> LeaveManagementPage(navController = navController)
                        "Employee Self-Service Portal" -> SelfServicePortalPage(navController = navController)
                        "Employee Transfers & Promotions" -> TransfersPromotionsPage(navController = navController)
                        "Exit & Offboarding Management" -> ExitOffboardingPage(navController = navController)
                    }
                }
            }
        }
    }
}

/**
 * Drawer item composable.
 *
 * @param text The text to display.
 * @param selected Whether the item is selected.
 * @param onClick Callback when the item is clicked.
 */
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