package com.example.chatroom

import android.annotation.SuppressLint
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp


@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun ChatScreen(roomID : String ,messageViewModel: MessageViewModel) {
    var text by remember { mutableStateOf("") }
    val messages by messageViewModel.messages.observeAsState(emptyList())
    LaunchedEffect(roomID) {
        messageViewModel.setRoomId(roomID)
    }
    Box(modifier = Modifier.fillMaxSize()) {
        Column(modifier = Modifier.fillMaxSize().statusBarsPadding()) {
            LazyColumn(modifier = Modifier.weight(1f)) {
            items(messages){
                message->
                ChatMessageItem(
                    message.copy(
                        isSentByCurrentUser = message.senderId == messageViewModel.currentUser.value?.email)

                )
            }
            }
            Surface (modifier = Modifier.navigationBarsPadding().padding(10.dp)){
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    BasicTextField(
                        value = text,
                        onValueChange = { text = it },
                        modifier = Modifier.weight(1f).height(56.dp)
                    )

                    IconButton(onClick = {
                        if (text.isNotBlank()) {
              messageViewModel.sendMessage(text.trim())
                            text = ""
                        }
           messageViewModel.loadMessages()
                    }
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.Send,
                            contentDescription = null,
                            tint = Color.Black
                        )
                    }
                }
            }

        }
    }
}

//@SuppressLint("NewApi")
//@Preview(showBackground = true)
//@Composable
//fun Chat_Preview(){
//   ChatScreen("")
//}