package com.example.chatroom

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.google.firebase.auth.FirebaseAuth


@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun Navigation (navController : NavHostController,authViewModel: AuthViewModel,roomViewModel: RoomViewModel,messageViewModel: MessageViewModel){

    var startDestination by remember { mutableStateOf<String?>(null) }

    LaunchedEffect(Unit){
        val currentUser = FirebaseAuth.getInstance().currentUser
        if (currentUser != null) {
            // Load user data in ViewModel
            messageViewModel.loadCurrentUser()
                startDestination = Screen.ChatRoomsScreen.route



        }else{
            startDestination = Screen.SignupScreen.route
        }
    }

    startDestination?.let {
        NavHost(navController = navController , startDestination = it){
        composable(Screen.SignupScreen.route){
            SignUpScreen(navController,authViewModel)
        }
        composable(Screen.LoginScreen.route){
            LogInScreen(navController, authViewModel ,messageViewModel )
        }
        composable(Screen.ChatRoomsScreen.route){
            ChatRoomsListScreen(
                navController ,authViewModel,roomViewModel){
                navController.navigate("${Screen.ChatScreen.route}/${it.id}/${it.name}")
            }
        }
            composable("${Screen.ChatScreen.route}/{roomID}/{roomName}"){ navBackStackEntry ->
                val roomID = navBackStackEntry.arguments?.getString("roomID") ?: ""
                val roomName = navBackStackEntry.arguments?.getString("roomName") ?: ""

                ChatScreen(navController,roomID,roomName,messageViewModel)

        }
    }
    }

}