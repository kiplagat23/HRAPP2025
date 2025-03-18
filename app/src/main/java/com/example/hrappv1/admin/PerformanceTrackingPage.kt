package com.example.hrappv1.admin

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController

@Composable
fun PerformanceTrackingPage(navController: NavHostController) {
    var goalSettingVisible by remember { mutableStateOf(false) }
    var performanceReviewVisible by remember { mutableStateOf(false) }
    var feedbackCollectionVisible by remember { mutableStateOf(false) }
    var promotionBonusVisible by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState()) // Make the page scrollable
            .padding(16.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.Start
    ) {
        Text(
            text = "Employee Performance Tracking & Appraisals",
            style = MaterialTheme.typography.headlineMedium,
            color = Color.Black, // Set text color to black
            modifier = Modifier.padding(bottom = 16.dp)
        )

        SectionToggleButton(
            title = "Goal Setting",
            isVisible = goalSettingVisible,
            onToggle = { goalSettingVisible = !goalSettingVisible }
        )
        if (goalSettingVisible) {
            GoalSettingForm()
        }

        SectionToggleButton(
            title = "Performance Review Setup",
            isVisible = performanceReviewVisible,
            onToggle = { performanceReviewVisible = !performanceReviewVisible }
        )
        if (performanceReviewVisible) {
            PerformanceReviewSetupForm()
        }

        SectionToggleButton(
            title = "Feedback Collection",
            isVisible = feedbackCollectionVisible,
            onToggle = { feedbackCollectionVisible = !feedbackCollectionVisible }
        )
        if (feedbackCollectionVisible) {
            FeedbackCollectionForm()
        }

        SectionToggleButton(
            title = "Promotion/Bonus Configuration",
            isVisible = promotionBonusVisible,
            onToggle = { promotionBonusVisible = !promotionBonusVisible }
        )
        if (promotionBonusVisible) {
            PromotionBonusConfigurationForm()
        }
    }
}

@Composable
fun SectionToggleButton(
    title: String,
    isVisible: Boolean,
    onToggle: () -> Unit
) {
    Button(
        onClick = onToggle,
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primaryContainer)
    ) {
        Text(
            text = if (isVisible) "Hide $title" else "Show $title",
            color = Color.Black // Set text color to black
        )
    }
}

@Composable
fun GoalSettingForm() {
    var goal by remember { mutableStateOf("") }
    var deadline by remember { mutableStateOf("") }

    Column(modifier = Modifier.fillMaxWidth()) {
        FormField(
            value = goal,
            onValueChange = { goal = it },
            label = "Goal",
            keyboardType = KeyboardType.Text
        )
        Spacer(modifier = Modifier.height(8.dp))
        FormField(
            value = deadline,
            onValueChange = { deadline = it },
            label = "Deadline",
            keyboardType = KeyboardType.Text
        )
        Spacer(modifier = Modifier.height(16.dp))
        Button(
            onClick = { /* Save goal logic */ },
            modifier = Modifier.align(Alignment.End)
        ) {
            Text("Save Goal", color = Color.Black) // Set text color to black
        }
    }
}

@Composable
fun PerformanceReviewSetupForm() {
    var reviewDate by remember { mutableStateOf("") }
    var ratingSystem by remember { mutableStateOf("") }

    Column(modifier = Modifier.fillMaxWidth()) {
        FormField(
            value = reviewDate,
            onValueChange = { reviewDate = it },
            label = "Review Date",
            keyboardType = KeyboardType.Text
        )
        Spacer(modifier = Modifier.height(8.dp))
        FormField(
            value = ratingSystem,
            onValueChange = { ratingSystem = it },
            label = "Rating System",
            keyboardType = KeyboardType.Text
        )
        Spacer(modifier = Modifier.height(16.dp))
        Button(
            onClick = { /* Save review setup logic */ },
            modifier = Modifier.align(Alignment.End)
        ) {
            Text("Save Review Setup", color = Color.Black) // Set text color to black
        }
    }
}

@Composable
fun FeedbackCollectionForm() {
    var feedbackType by remember { mutableStateOf("") }
    var feedbackSource by remember { mutableStateOf("") }

    Column(modifier = Modifier.fillMaxWidth()) {
        FormField(
            value = feedbackType,
            onValueChange = { feedbackType = it },
            label = "Feedback Type (e.g., 360-degree)",
            keyboardType = KeyboardType.Text
        )
        Spacer(modifier = Modifier.height(8.dp))
        FormField(
            value = feedbackSource,
            onValueChange = { feedbackSource = it },
            label = "Feedback Source",
            keyboardType = KeyboardType.Text
        )
        Spacer(modifier = Modifier.height(16.dp))
        Button(
            onClick = { /* Save feedback collection logic */ },
            modifier = Modifier.align(Alignment.End)
        ) {
            Text("Save Feedback Setup", color = Color.Black) // Set text color to black
        }
    }
}

@Composable
fun PromotionBonusConfigurationForm() {
    var promotionCriteria by remember { mutableStateOf("") }
    var bonusAmount by remember { mutableStateOf("") }

    Column(modifier = Modifier.fillMaxWidth()) {
        FormField(
            value = promotionCriteria,
            onValueChange = { promotionCriteria = it },
            label = "Promotion Criteria",
            keyboardType = KeyboardType.Text
        )
        Spacer(modifier = Modifier.height(8.dp))
        FormField(
            value = bonusAmount,
            onValueChange = { bonusAmount = it },
            label = "Bonus Amount",
            keyboardType = KeyboardType.Number
        )
        Spacer(modifier = Modifier.height(16.dp))
        Button(
            onClick = { /* Save promotion/bonus logic */ },
            modifier = Modifier.align(Alignment.End)
        ) {
            Text("Save Configuration", color = Color.Black) // Set text color to black
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FormField(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    keyboardType: KeyboardType
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text(label, color = Color.Black) }, // Set text color to black
        modifier = Modifier.fillMaxWidth(),
        keyboardOptions = KeyboardOptions(keyboardType = keyboardType),
        singleLine = true,
        colors = TextFieldDefaults.outlinedTextFieldColors(
            focusedBorderColor = Color.Black, // Set focused border color to black
            unfocusedBorderColor = Color.Gray // Set unfocused border color to gray
        )
    )
}