package com.example.chatroom

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable


@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun Navigation (navController : NavHostController,authViewModel: AuthViewModel,roomViewModel: RoomViewModel,messageViewModel: MessageViewModel){

    NavHost(navController = navController , startDestination = Screen.SignupScreen.route){
        composable(Screen.SignupScreen.route){
                SignUpScreen(navController,authViewModel)
        }
        composable(Screen.LoginScreen.route){
            LogInScreen(navController, authViewModel )
        }
        composable(Screen.ChatRoomsScreen.route){
            ChatRoomsListScreen(
                roomViewModel){
                navController.navigate("${Screen.ChatScreen.route}/${it.id}")
            }
        }
        composable("${Screen.ChatScreen.route}/{roomID}"){
            navBackStackEntry ->
            val roomID = navBackStackEntry.arguments?.getString("roomID") ?: ""

                ChatScreen(roomID,messageViewModel)

        }
    }

}