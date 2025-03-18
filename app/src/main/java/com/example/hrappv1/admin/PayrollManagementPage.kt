package com.example.hrappv1.admin

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.hrappv1.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PayrollManagementPage(navController: NavHostController) {
    // State for salary setup
    var salaryStructure by remember { mutableStateOf("") }
    var benefits by remember { mutableStateOf("") }
    var bonuses by remember { mutableStateOf("") }
    var commissions by remember { mutableStateOf("") }

    // State for tax configuration
    var taxRate by remember { mutableStateOf("") }

    // State for pay cycle setup
    var payCycleFrequency by remember { mutableStateOf("") }
    var isPayCycleDropdownExpanded by remember { mutableStateOf(false) }
    val payCycles = listOf("Weekly", "Bi-Weekly", "Monthly")

    // State for final salary approvals
    var employeeIdForExit by remember { mutableStateOf("") }
    var unpaidLeaves by remember { mutableStateOf("") } // Additional field for unpaid leaves

    // Calculated values
    var grossSalary by remember { mutableStateOf(0.0) }
    var netSalary by remember { mutableStateOf(0.0) }
    var totalTaxDeductions by remember { mutableStateOf(0.0) }
    var finalSettlementAmount by remember { mutableStateOf(0.0) }

    // Automatically calculate gross salary, tax deductions, net salary, and final settlement
    LaunchedEffect(salaryStructure, benefits, bonuses, commissions, taxRate, unpaidLeaves) {
        val baseSalary = salaryStructure.toDoubleOrNull() ?: 0.0
        val benefitsAmount = benefits.toDoubleOrNull() ?: 0.0
        val bonusesAmount = bonuses.toDoubleOrNull() ?: 0.0
        val commissionsAmount = commissions.toDoubleOrNull() ?: 0.0
        val taxRateValue = taxRate.toDoubleOrNull() ?: 0.0
        val unpaidLeavesDays = unpaidLeaves.toDoubleOrNull() ?: 0.0

        // Calculate gross salary
        grossSalary = baseSalary + benefitsAmount + bonusesAmount + commissionsAmount

        // Calculate tax deductions
        totalTaxDeductions = grossSalary * (taxRateValue / 100)

        // Calculate net salary
        netSalary = grossSalary - totalTaxDeductions

        // Calculate final settlement amount (net salary minus deductions for unpaid leaves)
        val dailySalary = netSalary / 30 // Assuming 30 days in a month
        finalSettlementAmount = netSalary - (unpaidLeavesDays * dailySalary)
    }

    // Use a Box to layer the background image and the content
    Box(modifier = Modifier.fillMaxSize()) {
        // Background Image
        Image(
            painter = painterResource(id = R.drawable.bg4), // Replace with your background image resource
            contentDescription = "Background Image",
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop // Ensures the image covers the entire screen
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
                .verticalScroll(rememberScrollState()), // Make the page scrollable
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Payroll Management & Configuration",
                style = MaterialTheme.typography.headlineMedium,
                color = Color.White // Set text color to white for better visibility
            )

            // Salary Setup
            Text(
                text = "Salary Setup",
                style = MaterialTheme.typography.titleMedium,
                color = Color.White // Set text color to white
            )
            OutlinedTextField(
                value = salaryStructure,
                onValueChange = { salaryStructure = it },
                label = { Text("Base Salary", color = Color.White) },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number, imeAction = ImeAction.Done),
                modifier = Modifier.fillMaxWidth(),
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    focusedBorderColor = Color.White, // Set border color to white
                    unfocusedBorderColor = Color.Gray // Set border color to gray
                )
            )
            OutlinedTextField(
                value = benefits,
                onValueChange = { benefits = it },
                label = { Text("Benefits", color = Color.White) },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number, imeAction = ImeAction.Done),
                modifier = Modifier.fillMaxWidth(),
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    focusedBorderColor = Color.White,
                    unfocusedBorderColor = Color.Gray
                )
            )
            OutlinedTextField(
                value = bonuses,
                onValueChange = { bonuses = it },
                label = { Text("Bonuses", color = Color.White) },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number, imeAction = ImeAction.Done),
                modifier = Modifier.fillMaxWidth(),
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    focusedBorderColor = Color.White,
                    unfocusedBorderColor = Color.Gray
                )
            )
            OutlinedTextField(
                value = commissions,
                onValueChange = { commissions = it },
                label = { Text("Commissions", color = Color.White) },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number, imeAction = ImeAction.Done),
                modifier = Modifier.fillMaxWidth(),
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    focusedBorderColor = Color.White,
                    unfocusedBorderColor = Color.Gray
                )
            )

            // Tax Configuration
            Text(
                text = "Tax Configuration",
                style = MaterialTheme.typography.titleMedium,
                color = Color.White // Set text color to white
            )
            OutlinedTextField(
                value = taxRate,
                onValueChange = { taxRate = it },
                label = { Text("Tax Rate (%)", color = Color.White) },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number, imeAction = ImeAction.Done),
                modifier = Modifier.fillMaxWidth(),
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    focusedBorderColor = Color.White,
                    unfocusedBorderColor = Color.Gray
                )
            )

            // Pay Cycle Setup
            Text(
                text = "Pay Cycle Setup",
                style = MaterialTheme.typography.titleMedium,
                color = Color.White // Set text color to white
            )
            // Pay Cycle Dropdown
            Box(modifier = Modifier.fillMaxWidth()) {
                OutlinedTextField(
                    value = payCycleFrequency,
                    onValueChange = { payCycleFrequency = it },
                    label = { Text("Select Pay Cycle", color = Color.White) },
                    modifier = Modifier.fillMaxWidth(),
                    readOnly = true,
                    trailingIcon = {
                        IconButton(onClick = { isPayCycleDropdownExpanded = true }) {
                            Icon(
                                Icons.Default.ArrowDropDown,
                                contentDescription = "Dropdown",
                                tint = Color.White // Set icon color to white
                            )
                        }
                    },
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        focusedBorderColor = Color.White,
                        unfocusedBorderColor = Color.Gray
                    )
                )
                DropdownMenu(
                    expanded = isPayCycleDropdownExpanded,
                    onDismissRequest = { isPayCycleDropdownExpanded = false },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    payCycles.forEach { cycle ->
                        DropdownMenuItem(
                            text = { Text(cycle, color = Color.Black) }, // Set text color to black
                            onClick = {
                                payCycleFrequency = cycle
                                isPayCycleDropdownExpanded = false
                            }
                        )
                    }
                }
            }

            // Final Salary Approvals
            Text(
                text = "Final Salary Approvals",
                style = MaterialTheme.typography.titleMedium,
                color = Color.White // Set text color to white
            )
            OutlinedTextField(
                value = employeeIdForExit,
                onValueChange = { employeeIdForExit = it },
                label = { Text("Employee ID", color = Color.White) },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number, imeAction = ImeAction.Done),
                modifier = Modifier.fillMaxWidth(),
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    focusedBorderColor = Color.White,
                    unfocusedBorderColor = Color.Gray
                )
            )
            OutlinedTextField(
                value = unpaidLeaves,
                onValueChange = { unpaidLeaves = it },
                label = { Text("Unpaid Leaves (Days)", color = Color.White) },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number, imeAction = ImeAction.Done),
                modifier = Modifier.fillMaxWidth(),
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    focusedBorderColor = Color.White,
                    unfocusedBorderColor = Color.Gray
                )
            )

            // Display calculated values at the bottom
            Text(
                text = "Calculated Values",
                style = MaterialTheme.typography.titleMedium,
                color = Color.White // Set text color to white
            )
            Text(
                text = "Gross Salary: $${grossSalary.format(2)}",
                style = MaterialTheme.typography.bodyMedium,
                color = Color.White // Set text color to white
            )
            Text(
                text = "Tax Deductions: $${totalTaxDeductions.format(2)}",
                style = MaterialTheme.typography.bodyMedium,
                color = Color.White // Set text color to white
            )
            Text(
                text = "Net Salary: $${netSalary.format(2)}",
                style = MaterialTheme.typography.bodyMedium,
                color = Color.White // Set text color to white
            )
            Text(
                text = "Final Settlement Amount: $${finalSettlementAmount.format(2)}",
                style = MaterialTheme.typography.bodyMedium,
                color = Color.White // Set text color to white
            )

            // Single Approve Button
            Button(
                onClick = {
                    // Save all configurations and approve final settlement
                    configureSalary(salaryStructure, benefits, bonuses, commissions)
                    configureTax(taxRate)
                    configurePayCycle(payCycleFrequency)
                    approveFinalSalary(employeeIdForExit, finalSettlementAmount)

                    // Clear all fields after approval
                    salaryStructure = ""
                    benefits = ""
                    bonuses = ""
                    commissions = ""
                    taxRate = ""
                    payCycleFrequency = ""
                    employeeIdForExit = ""
                    unpaidLeaves = ""
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Approve", color = Color.Black) // Set text color to black
            }
        }
    }
}

// Function to configure salary
private fun configureSalary(salaryStructure: String, benefits: String, bonuses: String, commissions: String) {
    // Add logic to save salary configuration (e.g., save to database)
    println("Salary Configured: $salaryStructure, $benefits, $bonuses, $commissions")
}

// Function to configure tax
private fun configureTax(taxRate: String) {
    // Add logic to save tax configuration (e.g., save to database)
    println("Tax Configured: $taxRate%")
}

// Function to configure pay cycle
private fun configurePayCycle(payCycleFrequency: String) {
    // Add logic to save pay cycle configuration (e.g., save to database)
    println("Pay Cycle Configured: $payCycleFrequency")
}

// Function to approve final salary
private fun approveFinalSalary(employeeId: String, finalSettlementAmount: Double) {
    // Add logic to approve final salary (e.g., save to database)
    println("Final Salary Approved: Employee $employeeId - $${finalSettlementAmount.format(2)}")
}

// Extension function to format Double values to 2 decimal places
fun Double.format(decimals: Int): String {
    return "%.${decimals}f".format(this)
}