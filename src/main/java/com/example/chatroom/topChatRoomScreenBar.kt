package com.example.chatroom

import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun topChatRoomScreenBar(text:String ,onMenuClick:()->Unit) {
    val colorScheme = MaterialTheme.colorScheme

    TopAppBar(modifier = Modifier.statusBarsPadding().heightIn(30.dp),
        navigationIcon = {
            IconButton(onClick = {onMenuClick()}){
            Icon(Icons.Default.Menu,contentDescription = "MENU" , tint = colorScheme.onPrimary)
        }
        },

        title = { Text(text = text) },
        colors = TopAppBarColors(
            containerColor = colorScheme.primary,
            scrolledContainerColor = colorScheme.primary ,
            navigationIconContentColor = colorScheme.onPrimary,
            titleContentColor = colorScheme.onPrimary,
            actionIconContentColor = colorScheme.onPrimary
        )
    )


}