package com.example.hrappv1.admin

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
@Composable
fun AdminAuthScreen(
    onAdminLoginClick: () -> Unit,
    onBackClick: () -> Unit
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Admin Login Screen")
        Button(onClick = onAdminLoginClick) {
            Text("Login as Admin")
        }
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = onBackClick) {
            Text("Back")
        }
    }
}