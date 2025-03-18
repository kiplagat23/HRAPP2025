import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

@Composable
fun TransfersPromotionsPage(navController: NavController) {
    // State for managing transfer requests
    var transferRequest by remember { mutableStateOf("") }

    // State for managing promotion history
    val promotionHistory = remember {
        mutableStateListOf(
            PromotionRecord("John Doe", "Senior Developer", "2023-01-15", "$5000", "Lead projects"),
            PromotionRecord("Jane Smith", "Manager", "2023-03-10", "$7000", "Manage team")
        )
    }

    // State for managing role adjustments
    var roleAdjustment by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.Start
    ) {
        // Title
        Text("Employee Transfers & Promotions", fontSize = 24.sp, fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.height(16.dp))

        // 🔄 Transfer Requests Section
        Text("🔄 Transfer Requests", fontSize = 20.sp, fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.height(8.dp))
        Text("Employees can apply for department transfers.")
        Spacer(modifier = Modifier.height(8.dp))
        OutlinedTextField(
            value = transferRequest,
            onValueChange = { transferRequest = it },
            label = { Text("Enter Transfer Request") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(16.dp))

        // 📊 Promotion History Section
        Text("📊 Promotion History", fontSize = 20.sp, fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.height(8.dp))
        promotionHistory.forEach { record ->
            Text("• ${record.name} promoted to ${record.newRole} on ${record.date}. New salary: ${record.newSalary}. Responsibilities: ${record.responsibilities}")
            Spacer(modifier = Modifier.height(4.dp))
        }
        Spacer(modifier = Modifier.height(16.dp))

        // 📝 Role Adjustments Section
        Text("📝 Role Adjustments", fontSize = 20.sp, fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.height(8.dp))
        Text("Updates job descriptions and access rights automatically after a promotion.")
        Spacer(modifier = Modifier.height(8.dp))
        OutlinedTextField(
            value = roleAdjustment,
            onValueChange = { roleAdjustment = it },
            label = { Text("Enter Role Adjustment Details") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(16.dp))

        // Button to initiate transfer/promotion
        Button(
            onClick = {
                // Handle transfer request and role adjustment logic here
                if (transferRequest.isNotBlank()) {
                    // Process transfer request
                    println("Transfer Request Submitted: $transferRequest")
                    transferRequest = ""
                }
                if (roleAdjustment.isNotBlank()) {
                    // Process role adjustment
                    println("Role Adjustment Applied: $roleAdjustment")
                    roleAdjustment = ""
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Initiate Transfer/Promotion")
        }
    }
}

// Data class for promotion history records
data class PromotionRecord(
    val name: String,
    val newRole: String,
    val date: String,
    val newSalary: String,
    val responsibilities: String
)
