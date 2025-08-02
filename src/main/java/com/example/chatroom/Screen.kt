package com.example.chatroom

import androidx.annotation.DrawableRes

sealed class Screen(val title :String ,val route:String){
    object LoginScreen:Screen("LOG IN","loginscreen")
    object SignupScreen:Screen("SIGN UP","signupscreen")
    object ChatRoomsScreen:Screen("CHAT ROOM","chatroomscreen")
    object ChatScreen:Screen("CHAT SCREEN","chatscreen")
    sealed class DrawerScreen(val dTitle : String , val dRoute:String , @DrawableRes val icon:Int) : Screen(dTitle ,dRoute){
            object Settings : DrawerScreen(
                "SETTINGS",
                "settings",
                R.drawable.baseline_settings_24
             )
    }
}

val drawerItemsList = listOf(
    Screen.DrawerScreen.Settings
)