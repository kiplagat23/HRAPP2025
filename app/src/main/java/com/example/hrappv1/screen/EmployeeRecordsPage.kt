package com.example.hrappv1.screen

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
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


@Composable
fun EmployeeRecordsPage(navController: NavController) {
    var resumeUri by remember { mutableStateOf<Uri?>(null) }
    var contractUri by remember { mutableStateOf<Uri?>(null) }
    var certificateUri by remember { mutableStateOf<Uri?>(null) }
    var nationalIdUri by remember { mutableStateOf<Uri?>(null) }
    var passportUri by remember { mutableStateOf<Uri?>(null) }
    var workPermitUri by remember { mutableStateOf<Uri?>(null) }
    var taxDocumentUri by remember { mutableStateOf<Uri?>(null) }

    val context = LocalContext.current
    var currentDocumentType by remember { mutableStateOf<DocumentType?>(null) }

    val filePickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent(),
        onResult = { uri ->
            when (currentDocumentType) {
                DocumentType.RESUME -> resumeUri = uri
                DocumentType.CONTRACT -> contractUri = uri
                DocumentType.CERTIFICATE -> certificateUri = uri
                DocumentType.NATIONAL_ID -> nationalIdUri = uri
                DocumentType.PASSPORT -> passportUri = uri
                DocumentType.WORK_PERMIT -> workPermitUri = uri
                DocumentType.TAX_DOCUMENT -> taxDocumentUri = uri
                null -> {}
            }
        }
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.Start
    ) {
        Text("Digital Employee Records", fontSize = 24.sp, fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.height(16.dp))



        Text("🔹 Document Upload: Upload & manage important documents (resumes, contracts, certificates).", fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.height(8.dp))

        DocumentUploadSection(
            documentType = "Resume",
            selectedFileUri = resumeUri,
            onUploadClick = {
                currentDocumentType = DocumentType.RESUME
                filePickerLauncher.launch("application/pdf")
            }
        )
        Spacer(modifier = Modifier.height(16.dp))

        DocumentUploadSection(
            documentType = "Contract",
            selectedFileUri = contractUri,
            onUploadClick = {
                currentDocumentType = DocumentType.CONTRACT
                filePickerLauncher.launch("application/pdf")
            }
        )
        Spacer(modifier = Modifier.height(16.dp))

        DocumentUploadSection(
            documentType = "Certificate",
            selectedFileUri = certificateUri,
            onUploadClick = {
                currentDocumentType = DocumentType.CERTIFICATE
                filePickerLauncher.launch("application/pdf")
            }
        )
        Spacer(modifier = Modifier.height(16.dp))

        Text("🔹 ID & Verification: Store national ID, passports, work permits, and tax documents.", fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.height(8.dp))

        DocumentUploadSection(
            documentType = "National ID",
            selectedFileUri = nationalIdUri,
            onUploadClick = {
                currentDocumentType = DocumentType.NATIONAL_ID
                filePickerLauncher.launch("application/pdf")
            }
        )
        Spacer(modifier = Modifier.height(16.dp))

        DocumentUploadSection(
            documentType = "Passport",
            selectedFileUri = passportUri,
            onUploadClick = {
                currentDocumentType = DocumentType.PASSPORT
                filePickerLauncher.launch("application/pdf")
            }
        )
        Spacer(modifier = Modifier.height(16.dp))

        DocumentUploadSection(
            documentType = "Work Permit",
            selectedFileUri = workPermitUri,
            onUploadClick = {
                currentDocumentType = DocumentType.WORK_PERMIT
                filePickerLauncher.launch("application/pdf")
            }
        )
        Spacer(modifier = Modifier.height(16.dp))

        DocumentUploadSection(
            documentType = "Tax Document",
            selectedFileUri = taxDocumentUri,
            onUploadClick = {
                currentDocumentType = DocumentType.TAX_DOCUMENT
                filePickerLauncher.launch("application/pdf")
            }
        )
        Spacer(modifier = Modifier.height(16.dp))





        Button(
            onClick = {
                handleSubmission(
                    resumeUri,
                    contractUri,
                    certificateUri,
                    nationalIdUri,
                    passportUri,
                    workPermitUri,
                    taxDocumentUri
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
fun DocumentUploadSection(
    documentType: String,
    selectedFileUri: Uri?,
    onUploadClick: () -> Unit
) {
    Column {
        Text("$documentType Upload", fontWeight = FontWeight.SemiBold)
        Spacer(modifier = Modifier.height(8.dp))

        if (selectedFileUri != null) {
            Text("Selected File: ${selectedFileUri.lastPathSegment}", fontWeight = FontWeight.Normal)
            Spacer(modifier = Modifier.height(8.dp))
        }

        Button(
            onClick = onUploadClick,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Upload $documentType")
        }
    }
}

enum class DocumentType {
    RESUME, CONTRACT, CERTIFICATE,
    NATIONAL_ID, PASSPORT, WORK_PERMIT, TAX_DOCUMENT
}

fun handleSubmission(
    resumeUri: Uri?,
    contractUri: Uri?,
    certificateUri: Uri?,
    nationalIdUri: Uri?,
    passportUri: Uri?,
    workPermitUri: Uri?,
    taxDocumentUri: Uri?
) {
    if (resumeUri != null) println("Resume uploaded: ${resumeUri.lastPathSegment}")
    if (contractUri != null) println("Contract uploaded: ${contractUri.lastPathSegment}")
    if (certificateUri != null) println("Certificate uploaded: ${certificateUri.lastPathSegment}")
    if (nationalIdUri != null) println("National ID uploaded: ${nationalIdUri.lastPathSegment}")
    if (passportUri != null) println("Passport uploaded: ${passportUri.lastPathSegment}")
    if (workPermitUri != null) println("Work Permit uploaded: ${workPermitUri.lastPathSegment}")
    if (taxDocumentUri != null) println("Tax Document uploaded: ${taxDocumentUri.lastPathSegment}")
}