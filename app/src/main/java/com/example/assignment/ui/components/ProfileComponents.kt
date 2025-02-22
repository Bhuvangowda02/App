package com.example.assignment.components

import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Error
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.assignment.R

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
        Box(
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .size(36.dp)
                .clip(CircleShape)
                .background(Color.White)
                .clickable(onClick = onClick),
            contentAlignment = Alignment.Center
        ) {
            Icon(Icons.Default.Edit, contentDescription = "Edit Profile", tint = Color.Gray)
        }
    }
}

@Composable
fun ProfileEditableField(
    label: String,
    value: TextFieldValue,
    isError: Boolean = false,
    onValueChange: (TextFieldValue) -> Unit
) {
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
}
