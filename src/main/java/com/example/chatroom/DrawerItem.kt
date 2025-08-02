package com.example.chatroom

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp


@Composable
fun DrawerItem (
    selected :Boolean,
    item : Screen.DrawerScreen,
    onDrawerItemClicked :()->Unit
){
    val background = if(selected) Color.DarkGray else Color.White
    Row(
        modifier = Modifier.fillMaxWidth().background(background)
            .clickable { onDrawerItemClicked() }

    ){
        Icon(
            painter = painterResource(id = item.icon),
            contentDescription = item.dTitle,
            modifier = Modifier.padding(5.dp)
        )
        Text(text = item.dTitle ,modifier = Modifier.padding(5.dp) )
    }
}