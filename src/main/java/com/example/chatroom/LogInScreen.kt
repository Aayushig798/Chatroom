package com.example.chatroom

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.AlertDialogDefaults.containerColor
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItemDefaults.contentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LogInScreen(
navController: NavController,
authViewModel: AuthViewModel,
messageViewModel: MessageViewModel
) {
    var email by remember { mutableStateOf("") }
    var password by remember {
        mutableStateOf("")
    }
    val result by authViewModel.authResult.observeAsState()
    var passwordVisible by remember{ mutableStateOf(false) }

    LaunchedEffect(result){
        when(result){
            is Result.Success -> {
                messageViewModel.loadCurrentUser()
                navController.navigate(Screen.ChatRoomsScreen.route)
            }
            is Result.Error -> {

            }

            else -> { }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("Email") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = MaterialTheme.colorScheme.onBackground,
                unfocusedBorderColor =  MaterialTheme.colorScheme.onBackground
            )
        )
        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Password") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = MaterialTheme.colorScheme.onBackground,
                unfocusedBorderColor =  MaterialTheme.colorScheme.onBackground
            ),
            visualTransformation = if(passwordVisible) {
                VisualTransformation.None
            } else {
                PasswordVisualTransformation()
            },
            trailingIcon = {
                val image = if(passwordVisible) Icons.Filled.Visibility else Icons.Filled.VisibilityOff
                IconButton(onClick = {passwordVisible = !passwordVisible}){
                    Icon(imageVector = image , contentDescription = null)
                }
            }
        )
        Button(
            onClick = {authViewModel.login(email, password)},colors = ButtonColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                contentColor = MaterialTheme.colorScheme.onPrimary,
                disabledContainerColor = MaterialTheme.colorScheme.primary,
                disabledContentColor = MaterialTheme.colorScheme.onPrimary)

            ,
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        ) {
            Text("Login")
        }
        Spacer(modifier = Modifier.height(16.dp))
        Text("Don't have an account? Sign up.",
            modifier = Modifier.clickable { navController.navigate(Screen.SignupScreen.route) }
        )
    }
}



//@Preview
//@Composable
//fun LoginPreview() {
//    LogInScreen()
//}