package com.example.hrappv1.admin

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.Alignment
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            UserRoleManagementApp()
        }
    }
}

@Composable
fun UserRoleManagementApp() {
    MaterialTheme {
        UserRoleManagementPage(navController = NavHostController(LocalContext.current))
    }
}

@Composable
fun UserRoleManagementPage(navController: NavHostController) {
    // State for role creation
    var roleName by remember { mutableStateOf("") }
    var roleDescription by remember { mutableStateOf("") }

    // State for access control
    var selectedRole by remember { mutableStateOf("") }
    var permissions by remember { mutableStateOf("") }

    // State for role assignment
    var employeeId by remember { mutableStateOf("") }
    var assignedRole by remember { mutableStateOf("") }

    // State for role deactivation
    var roleToDeactivate by remember { mutableStateOf("") }

    // Snackbar state
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    // Firestore instance
    val db = Firebase.firestore

    // Function to show Snackbar messages
    fun showSnackbar(message: String) {
        scope.launch {
            snackbarHostState.showSnackbar(message)
        }
    }

    // Function to create a new role
    fun createRole(roleName: String, roleDescription: String) {
        if (roleName.isEmpty() || roleDescription.isEmpty()) {
            showSnackbar("Role name and description cannot be empty!")
            return
        }

        val role = hashMapOf(
            "name" to roleName,
            "description" to roleDescription
        )

        db.collection("roles")
            .add(role)
            .addOnSuccessListener {
                showSnackbar("Role created successfully!")
            }
            .addOnFailureListener { e ->
                showSnackbar("Error creating role: ${e.message}")
            }
    }

    // Function to set permissions for a role
    fun setPermissions(role: String, permissions: List<String>) {
        if (role.isEmpty() || permissions.isEmpty()) {
            showSnackbar("Role and permissions cannot be empty!")
            return
        }

        val permissionData = hashMapOf(
            "role" to role,
            "permissions" to permissions
        )

        db.collection("permissions")
            .add(permissionData)
            .addOnSuccessListener {
                showSnackbar("Permissions set successfully!")
                selectedRole = ""
            }
            .addOnFailureListener { e ->
                showSnackbar("Error setting permissions: ${e.message}")
            }
    }

    // Function to assign a role to an employee
    fun assignRole(employeeId: String, role: String) {
        if (employeeId.isEmpty() || role.isEmpty()) {
            showSnackbar("Employee ID and role cannot be empty!")
            return
        }

        val assignment = hashMapOf(
            "employeeId" to employeeId,
            "role" to role
        )

        db.collection("assignments")
            .add(assignment)
            .addOnSuccessListener {
                showSnackbar("Role assigned successfully!")
                assignedRole = ""
            }
            .addOnFailureListener { e ->
                showSnackbar("Error assigning role: ${e.message}")
            }
    }

    // Function to deactivate a role
    fun deactivateRole(role: String) {
        if (role.isEmpty()) {
            showSnackbar("Role cannot be empty!")
            return
        }

        val deactivationData = hashMapOf(
            "role" to role,
            "status" to "deactivated"
        )

        db.collection("deactivatedRoles")
            .add(deactivationData)
            .addOnSuccessListener {
                showSnackbar("Role deactivated successfully!")
                roleToDeactivate = ""
            }
            .addOnFailureListener { e ->
                showSnackbar("Error deactivating role: ${e.message}")
            }
    }

    // UI
    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text("User Role Management", style = MaterialTheme.typography.headlineMedium)

            // Role Creation
            Text("Role Creation", style = MaterialTheme.typography.titleMedium)
            OutlinedTextField(
                value = roleName,
                onValueChange = { roleName = it },
                label = { Text("Role Name") },
                modifier = Modifier.fillMaxWidth()
            )
            OutlinedTextField(
                value = roleDescription,
                onValueChange = { roleDescription = it },
                label = { Text("Role Description") },
                modifier = Modifier.fillMaxWidth()
            )
            Button(onClick = { createRole(roleName, roleDescription) }) {
                Text("Create Role")
            }

            // Access Control
            Text("Access Control", style = MaterialTheme.typography.titleMedium)
            OutlinedTextField(
                value = selectedRole,
                onValueChange = { selectedRole = it },
                label = { Text("Select Role") },
                modifier = Modifier.fillMaxWidth()
            )
            OutlinedTextField(
                value = permissions,
                onValueChange = { permissions = it },
                label = { Text("Permissions (comma-separated)") },
                modifier = Modifier.fillMaxWidth()
            )
            Button(onClick = { setPermissions(selectedRole, permissions.split(",")) }) {
                Text("Set Permissions")
            }

            // Role Assignment
            Text("Role Assignment", style = MaterialTheme.typography.titleMedium)
            OutlinedTextField(
                value = employeeId,
                onValueChange = { employeeId = it },
                label = { Text("Employee ID") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number, imeAction = ImeAction.Done),
                modifier = Modifier.fillMaxWidth()
            )
            OutlinedTextField(
                value = assignedRole,
                onValueChange = { assignedRole = it },
                label = { Text("Role to Assign") },
                modifier = Modifier.fillMaxWidth()
            )
            Button(onClick = { assignRole(employeeId, assignedRole) }) {
                Text("Assign Role")
            }

            // Role Deactivation
            Text("Role Deactivation", style = MaterialTheme.typography.titleMedium)
            OutlinedTextField(
                value = roleToDeactivate,
                onValueChange = { roleToDeactivate = it },
                label = { Text("Role to Deactivate") },
                modifier = Modifier.fillMaxWidth()
            )
            Button(onClick = { deactivateRole(roleToDeactivate) }) {
                Text("Deactivate Role")
            }
        }

        // Snackbar Host
        SnackbarHost(hostState = snackbarHostState, modifier = Modifier.align(Alignment.BottomCenter))
    }
}