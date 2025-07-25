package com.example.chatroom

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.OutlinedTextField


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChatRoomsListScreen(){
    var showDialog by remember { mutableStateOf(false) }
    var chatRoomName by remember { mutableStateOf("") }
    Column(modifier = Modifier.fillMaxSize()){
        Text(text = "CHAT ROOMS" , fontSize = 20.sp , fontWeight = FontWeight.Bold,
            modifier = Modifier.statusBarsPadding().padding(10.dp))
        LazyColumn {

        }
        Button(onClick = { showDialog = true },
            modifier = Modifier.fillMaxWidth().padding(10.dp)){
            Text("CREATE ROOM")
        }

    }

    if(showDialog){
        AlertDialog(title = {Text("CREATE A NEW ROOM")},
            onDismissRequest = {showDialog = false},
            confirmButton ={Row(modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly){
                Button(onClick = {}){Text("ADD")}
                Button(onClick = {showDialog = false}){Text("CANCEL")}
            } },
            text = { OutlinedTextField(
                value = chatRoomName,
                onValueChange = {chatRoomName = it}
            ) }
        )
    }
}


@Preview(showBackground = true)
@Composable
fun chat(){
    ChatRoomsListScreen()
}