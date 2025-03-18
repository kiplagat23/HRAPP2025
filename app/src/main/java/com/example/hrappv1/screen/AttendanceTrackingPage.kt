package com.example.hrappv1.screen

import android.content.Context
import android.hardware.biometrics.BiometricPrompt
import android.os.Build
import android.os.CancellationSignal
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import java.time.Duration
import java.time.LocalDate
import java.time.LocalTime
import java.time.format.DateTimeFormatter


@RequiresApi(Build.VERSION_CODES.P)
@Composable
fun AttendanceTrackingPage(navController: NavController) {
    var checkInTime by remember { mutableStateOf("") }
    var checkOutTime by remember { mutableStateOf("") }
    var checkInDate by remember { mutableStateOf("") }
    var checkOutDate by remember { mutableStateOf("") }
    var shiftSchedule by remember { mutableStateOf("08:00 - 17:00") }
    var totalWorkHours by remember { mutableStateOf("") }
    var overtimeHours by remember { mutableStateOf("") }
    var lateArrival by remember { mutableStateOf("") }
    var earlyArrival by remember { mutableStateOf("") }
    var earlyDeparture by remember { mutableStateOf("") }
    var extraWorkingHours by remember { mutableStateOf("") }

    val context = LocalContext.current

    LaunchedEffect(checkInTime, checkOutTime) {
        if (checkInTime.isNotEmpty() && checkOutTime.isNotEmpty()) {
            val formatter = DateTimeFormatter.ofPattern("HH:mm")
            val checkIn = LocalTime.parse(checkInTime, formatter)
            val checkOut = LocalTime.parse(checkOutTime, formatter)
            val shiftStart = LocalTime.parse(shiftSchedule.split(" - ")[0], formatter)
            val shiftEnd = LocalTime.parse(shiftSchedule.split(" - ")[1], formatter)

            val totalDuration = Duration.between(checkIn, checkOut).toHours()
            totalWorkHours = "$totalDuration hours"

            lateArrival = if (checkIn.isAfter(shiftStart)) {
                val lateDuration = Duration.between(shiftStart, checkIn).toHours()
                "$lateDuration hours late"
            } else {
                "On time"
            }

            earlyArrival = if (checkIn.isBefore(shiftStart)) {
                val earlyDuration = Duration.between(checkIn, shiftStart).toHours()
                "$earlyDuration hours early"
            } else {
                "On time"
            }

            earlyDeparture = if (checkOut.isBefore(shiftEnd)) {
                val earlyDuration = Duration.between(checkOut, shiftEnd).toHours()
                "$earlyDuration hours early"
            } else {
                "On time"
            }

            overtimeHours = if (checkOut.isAfter(shiftEnd)) {
                val overtimeDuration = Duration.between(shiftEnd, checkOut).toHours()
                "$overtimeDuration hours"
            } else {
                "0 hours"
            }

            val standardWorkingHours = 9
            extraWorkingHours = if (totalDuration > standardWorkingHours) {
                "${totalDuration - standardWorkingHours} hours"
            } else {
                "0 hours"
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.Start
    ) {
        Text("Attendance & Time Tracking", fontSize = 24.sp, fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.height(16.dp))

        Text("Check-In Time: $checkInTime", fontWeight = FontWeight.Normal)
        Text("🕒 Time Clocking: Employees clock in and out.", fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.height(8.dp))

        Button(
            onClick = {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                    authenticateWithBiometrics(context, "Clock In") { success ->
                        if (success) {
                            checkInTime = LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm"))
                            checkInDate = LocalDate.now().toString()
                        }
                    }
                } else {
                    checkInTime = LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm"))
                    checkInDate = LocalDate.now().toString()
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Clock In with Biometrics")
        }
        Spacer(modifier = Modifier.height(8.dp))
        Text("\uD83D\uDCC5 Check-In Date: $checkInDate", fontWeight = FontWeight.Normal)
        Text("\uD83D\uDD52 Check-In Time: $checkInTime", fontWeight = FontWeight.Normal)
        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                    authenticateWithBiometrics(context, "Clock Out") { success ->
                        if (success) {
                            checkOutTime = LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm"))
                            checkOutDate = LocalDate.now().toString()
                        }
                    }
                } else {
                    checkOutTime = LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm"))
                    checkOutDate = LocalDate.now().toString()
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Clock Out with Biometrics")
        }
        Spacer(modifier = Modifier.height(8.dp))
        Text("\uD83D\uDCC5 Check-Out Date: $checkOutDate", fontWeight = FontWeight.Normal)
        Text("\uD83D\uDD52 Check-Out Time: $checkOutTime", fontWeight = FontWeight.Normal)
        Spacer(modifier = Modifier.height(16.dp))

        Text("📊 Work Hours Tracking", fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.height(8.dp))

        WorkingHoursTrackingTable(
            checkInDate = checkInDate,
            checkInTime = checkInTime,
            checkOutDate = checkOutDate,
            checkOutTime = checkOutTime,
            totalWorkHours = totalWorkHours,
            lateArrival = lateArrival,
            earlyArrival = earlyArrival,
            earlyDeparture = earlyDeparture,
            overtimeHours = overtimeHours,
            extraWorkingHours = extraWorkingHours
        )
        Spacer(modifier = Modifier.height(16.dp))

        Text("⚡ Overtime Tracking: ", fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.height(8.dp))
        Text("Overtime Hours: $overtimeHours", fontWeight = FontWeight.Normal)
        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                handleAttendanceSubmission(
                    checkInDate,
                    checkInTime,
                    checkOutDate,
                    checkOutTime,
                    totalWorkHours,
                    shiftSchedule,
                    overtimeHours
                )
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp)
        ) {
            Text("Submit")
        }
    }
}

@Composable
fun WorkingHoursTrackingTable(
    checkInDate: String,
    checkInTime: String,
    checkOutDate: String,
    checkOutTime: String,
    totalWorkHours: String,
    lateArrival: String,
    earlyArrival: String,
    earlyDeparture: String,
    overtimeHours: String,
    extraWorkingHours: String
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        // Row for Check-In Date and Time
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text("Check-In Date:", fontWeight = FontWeight.Bold)
            Text(checkInDate)
        }
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text("Check-In Time:", fontWeight = FontWeight.Bold)
            Text(checkInTime)
        }

        // Row for Check-Out Date and Time
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text("Check-Out Date:", fontWeight = FontWeight.Bold)
            Text(checkOutDate)
        }
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text("Check-Out Time:", fontWeight = FontWeight.Bold)
            Text(checkOutTime)
        }

        // Row for Total Work Hours
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text("Total Work Hours:", fontWeight = FontWeight.Bold)
            Text(totalWorkHours)
        }

        // Row for Late Arrival
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text("Late Arrival:", fontWeight = FontWeight.Bold)
            Text(lateArrival)
        }

        // Row for Early Arrival
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text("Early Arrival:", fontWeight = FontWeight.Bold)
            Text(earlyArrival)
        }

        // Row for Early Departure
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text("Early Departure:", fontWeight = FontWeight.Bold)
            Text(earlyDeparture)
        }

        // Row for Overtime Hours
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text("Overtime Hours:", fontWeight = FontWeight.Bold)
            Text(overtimeHours)
        }

        // Row for Extra Working Hours
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text("Extra Working Hours:", fontWeight = FontWeight.Bold)
            Text(extraWorkingHours)
        }
    }
}

@RequiresApi(Build.VERSION_CODES.P)
fun authenticateWithBiometrics(
    context: Context,
    promptMessage: String,
    onResult: (Boolean) -> Unit
) {
    val biometricPrompt = BiometricPrompt.Builder(context)
        .setTitle("Biometric Authentication")
        .setSubtitle(promptMessage)
        .setNegativeButton("Cancel", context.mainExecutor, { _, _ ->
            onResult(false)
        })
        .build()

    val cancellationSignal = CancellationSignal()
    biometricPrompt.authenticate(
        cancellationSignal,
        context.mainExecutor,
        object : BiometricPrompt.AuthenticationCallback() {
            override fun onAuthenticationSucceeded(result: BiometricPrompt.AuthenticationResult) {
                super.onAuthenticationSucceeded(result)
                onResult(true)
            }

            override fun onAuthenticationFailed() {
                super.onAuthenticationFailed()
                onResult(false)
            }
        }
    )
}

fun handleAttendanceSubmission(
    checkInDate: String,
    checkInTime: String,
    checkOutDate: String,
    checkOutTime: String,
    totalWorkHours: String,
    shiftSchedule: String,
    overtimeHours: String
) {
    println("Check-In Date: $checkInDate")
    println("Check-In Time: $checkInTime")
    println("Check-Out Date: $checkOutDate")
    println("Check-Out Time: $checkOutTime")
    println("Total Work Hours: $totalWorkHours")
    println("Shift Schedule: $shiftSchedule")
    println("Overtime Hours: $overtimeHours")
}