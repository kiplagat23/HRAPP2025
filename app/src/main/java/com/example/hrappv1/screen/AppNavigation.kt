package com.example.hrappv1.screen

import TransfersPromotionsPage
import android.annotation.SuppressLint
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.hrappv1.admin.AdminDashboard
import com.example.hrappv1.admin.AdminLoginScreen
import com.example.hrappv1.admin.CompliancePolicyManagementPage
import com.example.hrappv1.admin.EmployeeProfileManagementPage
import com.example.hrappv1.admin.LeaveAttendanceManagementPage
import com.example.hrappv1.admin.PayrollManagementPage
import com.example.hrappv1.admin.PerformanceTrackingPage
import com.example.hrappv1.admin.RecruitmentManagementPage
import com.example.hrappv1.admin.ReportingAnalyticsPage
import com.example.hrappv1.admin.TrainingDevelopmentPage
import com.example.hrappv1.admin.UserRoleManagementPage

@SuppressLint("ComposableDestinationInComposeScope")
@Composable
fun AppNavigation(modifier: Modifier = Modifier) {
    val navController: NavHostController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = "user_admin_selection", // Start with user/admin selection screen
        modifier = modifier
    ) {
        // User and Admin Selection Screen
        composable("user_admin_selection") {
            UserAdminSelectionScreen(
                onUserClick = { navController.navigate("auth") },
                onAdminClick = { navController.navigate("admin_auth") }
            )
        }

        // Regular User Authentication Flow
        composable("auth") {
            AuthScreen(
                onLoginClick = { navController.navigate("login") },
                onSignUpClick = { navController.navigate("signup") }
            )
        }
        composable("login") {
            LoginScreen(
                navController = navController,
                onBackClick = { navController.popBackStack() }
            )
        }
        composable("signup") {
            SignupScreen(
                navController = navController,
                onBackClick = { navController.popBackStack() }
            )
        }

        // Main User Dashboard
        composable("mainpage") {
            MainPage(
                navController = navController,
                onLogoutClick = {
                    navController.navigate("auth") {
                        popUpTo("auth") { inclusive = true }
                    }
                }
            )
        }

        // Admin Authentication Flow
        composable("admin_auth") {
            AdminLoginScreen(
                navController = navController,
                onBackClick = { navController.popBackStack() }
            )
        }

        // Admin Dashboard
        composable("admin_dashboard") {
            AdminDashboard(
                navController = navController,
                onLogoutClick = {
                    navController.navigate("user_admin_selection") {
                        popUpTo("user_admin_selection") { inclusive = true }
                    }
                }
            )
        }

        // Admin Management Pages
        composable("user_role_management") {
            UserRoleManagementPage(navController = navController)
        }
        composable("employee_profile_management") {
            EmployeeProfileManagementPage(navController = navController)
        }
        composable("payroll_management") {
            PayrollManagementPage(navController = navController)
        }
        composable("leave_attendance_management") {
            LeaveAttendanceManagementPage()
        }
        composable("performance_tracking") {
            PerformanceTrackingPage(navController = navController)
        }
        composable("training_development") {
            TrainingDevelopmentPage(navController = navController)
        }
        composable("compliance_policy_management") {
            CompliancePolicyManagementPage(navController = navController)
        }
        composable("reporting_analytics") {
            ReportingAnalyticsPage(navController = navController)
        }
        composable("recruitment_management") {
            RecruitmentManagementPage(navController = navController)
        }

        // Other Screens (Employee Records, Attendance Tracking, etc.)
        composable("employee_records") {
            EmployeeRecordsPage(navController = navController)
        }
        composable("attendance_tracking") {
            AttendanceTrackingPage(navController = navController)
        }
        composable("self_service_portal") {
            SelfServicePortalPage(navController = navController)
        }
        composable("transfers_promotions") {
            TransfersPromotionsPage(navController = navController)
        }
        composable("exit_offboarding") {
            ExitOffboardingPage(navController = navController)
        }
        composable("leave_absence_management") {
            LeaveManagementPage(navController = navController)
        }
    }
}