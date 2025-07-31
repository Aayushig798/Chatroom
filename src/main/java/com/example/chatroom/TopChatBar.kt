package com.example.chatroom

import androidx.compose.foundation.content.MediaType.Companion.Text
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import org.checkerframework.checker.units.qual.C


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopChatBar(roomName : String , onBackClick:()->Unit) {

    TopAppBar(modifier = Modifier.statusBarsPadding().heightIn(30.dp),
        navigationIcon = {IconButton(onClick = {onBackClick()}){
            Icon(Icons.AutoMirrored.Filled.ArrowBack,contentDescription = null)
        }},

                title = { Text(text = roomName) },
        colors = TopAppBarColors(
            containerColor = colorResource(id = R.color.app_color),
            scrolledContainerColor = colorResource(id = R.color.app_color) ,
            navigationIconContentColor = Color.Black,
            titleContentColor = Color.Black,
            actionIconContentColor = Color.White
        )
                 )

}

@Preview(showBackground = true)
@Composable
fun top_preview(){
    TopChatBar("FOREIGN") { }
}