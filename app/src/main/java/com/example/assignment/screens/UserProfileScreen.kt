@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.assignment.screens

import android.net.Uri
import android.util.Patterns
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Error
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.assignment.R
import com.example.assignment.data.UserPreferences
import kotlinx.coroutines.launch

@Composable
fun UserProfileScreen(userPreferences: UserPreferences) {
    val coroutineScope = rememberCoroutineScope()

    var storedName by remember { mutableStateOf(TextFieldValue("")) }
    var storedEmail by remember { mutableStateOf(TextFieldValue("")) }
    var storedPhone by remember { mutableStateOf(TextFieldValue("")) }
    var profilePicUri by remember { mutableStateOf<Uri?>(null) }
    var emailError by remember { mutableStateOf(false) }
    var phoneError by remember { mutableStateOf(false) }
    var isUpdated by remember { mutableStateOf(false) }
    var portfolioValue by remember { mutableStateOf("10,000.00") } // Default Portfolio Value

    LaunchedEffect(Unit) {
        userPreferences.getUserName.collect { storedName = TextFieldValue(it) }
        userPreferences.getUserEmail.collect { storedEmail = TextFieldValue(it) }
        userPreferences.getUserPhone.collect { storedPhone = TextFieldValue(it) }
        userPreferences.getUserProfilePic.collect { uri ->
            profilePicUri = uri.takeIf { it.isNotEmpty() }?.let { Uri.parse(it) }
        }
    }

    val launcher = rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
        uri?.let { selectedUri ->
            profilePicUri = selectedUri
            coroutineScope.launch { userPreferences.saveUserProfilePic(selectedUri.toString()) }
            isUpdated = true
        }
    }

    Scaffold(

    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(1.dp)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            ProfilePicture(profilePicUri) { launcher.launch("image/*") }
            Spacer(modifier = Modifier.height(16.dp))

            ProfileEditableField("Name", storedName, onValueChange = {
                storedName = it
                isUpdated = true
            })
            ProfileEditableField("Email", storedEmail, isError = emailError, errorMessage = "Invalid email format", onValueChange = {
                storedEmail = it
                emailError = !Patterns.EMAIL_ADDRESS.matcher(it.text).matches()
                isUpdated = !emailError
            })
            ProfileEditableField("Phone", storedPhone, isError = phoneError, errorMessage = "Invalid phone number", onValueChange = {
                storedPhone = it
                phoneError = it.text.length < 10 || !Patterns.PHONE.matcher(it.text).matches()
                isUpdated = !phoneError
            })

            Spacer(modifier = Modifier.height(24.dp))

            // Display Portfolio Holdings Card
            PortfolioCard(portfolioValue)

            Spacer(modifier = Modifier.height(24.dp))

            if (isUpdated && !emailError && !phoneError) {
                Button(
                    onClick = {
                        coroutineScope.launch {
                            userPreferences.saveUserName(storedName.text)
                            userPreferences.saveUserEmail(storedEmail.text)
                            userPreferences.saveUserPhone(storedPhone.text)
                        }
                        isUpdated = false
                    },
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)
                ) {
                    Text("Save Profile", fontSize = 18.sp, fontWeight = FontWeight.Bold)
                }
            }
        }
    }
}

@Composable
fun PortfolioCard(portfolioValue: String) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant),
        elevation = CardDefaults.cardElevation(8.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text("Portfolio Value", fontSize = 18.sp, fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.height(8.dp))
            Text(portfolioValue, fontSize = 22.sp, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.primary)
        }
    }
}

@Composable
fun ProfilePicture(uri: Uri?, onClick: () -> Unit) {
    Box(
        modifier = Modifier
            .size(140.dp)
            .clip(CircleShape)
            .background(MaterialTheme.colorScheme.onSurfaceVariant)
            .clickable(onClick = onClick),
        contentAlignment = Alignment.Center
    ) {
        uri?.let {
            AsyncImage(
                model = it,
                contentDescription = "Profile Picture",
                modifier = Modifier.size(140.dp).clip(CircleShape)
            )
        } ?: Image(
            painter = painterResource(id = R.drawable.ic_profile_placeholder),
            contentDescription = "Profile Placeholder",
            modifier = Modifier.size(140.dp)
        )
        Icon(
            imageVector = Icons.Default.Edit,
            contentDescription = "Edit Profile",
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .size(36.dp)
                .clip(CircleShape)
                .background(Color.White)
                .clickable(onClick = onClick),
            tint = Color.Gray
        )
    }
}

@Composable
fun ProfileEditableField(
    label: String,
    value: TextFieldValue,
    isError: Boolean = false,
    errorMessage: String = "",
    onValueChange: (TextFieldValue) -> Unit
) {
    Column {
        OutlinedTextField(
            value = value,
            onValueChange = onValueChange,
            label = { Text(label) },
            singleLine = true,
            isError = isError,
            modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp),
            textStyle = TextStyle(fontSize = 16.sp),
            trailingIcon = {
                if (isError) Icon(Icons.Default.Error, contentDescription = "Error", tint = Color.Red)
                else Icon(Icons.Default.Edit, contentDescription = "Edit")
            }
        )
        if (isError) {
            Text(text = errorMessage, color = Color.Red, fontSize = 12.sp, style = MaterialTheme.typography.bodySmall)
        }
    }
}
