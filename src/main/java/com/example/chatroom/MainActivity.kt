package com.example.chatroom

import android.app.Activity
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.rememberNavController
import com.example.chatroom.ui.theme.ChatRoomTheme
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.processor.internal.definecomponent.codegen._dagger_hilt_android_components_ViewModelComponent
import org.checkerframework.checker.units.qual.A

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            val context = LocalContext.current
            val colorScheme = MaterialTheme.colorScheme
            SideEffect {
                val window = (context as Activity).window
                window.statusBarColor = colorScheme.primary.toArgb()

            }
            val navController = rememberNavController()
            val authViewModel:AuthViewModel = hiltViewModel()
            val roomViewModel:RoomViewModel= hiltViewModel()
            val messageViewModel:MessageViewModel = hiltViewModel()
            Navigation(navController, authViewModel,roomViewModel, messageViewModel  )
        }
    }
}
