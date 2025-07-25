package com.example.chatroom

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable


@Composable
fun Navigation (navController : NavHostController,authViewModel: AuthViewModel){

    NavHost(navController = navController , startDestination = Screen.SignupScreen.route){
        composable(Screen.SignupScreen.route){
                SignUpScreen(navController,authViewModel)
        }
        composable(Screen.LoginScreen.route){
            LogInScreen(navController, authViewModel )
        }
    }

}