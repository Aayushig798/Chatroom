@file:Suppress("UNREACHABLE_CODE")

package com.example.chatroom

import android.annotation.SuppressLint
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.ime
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import kotlinx.coroutines.launch


@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun ChatScreen(navController: NavController ,roomID : String , roomName : String,messageViewModel: MessageViewModel) {
    val colorScheme = MaterialTheme.colorScheme
    var text by remember { mutableStateOf("") }
    val messages by messageViewModel.messages.observeAsState(emptyList())
    val coroutineScope = rememberCoroutineScope()
    var someoneIsTyping by remember { mutableStateOf(false) }
    val currentUser by messageViewModel.currentUser.observeAsState()



//    val scaffoldState = rememberScaffoldState()
    val listState = rememberLazyListState()
    val showScrollToBottomButton by remember {
        derivedStateOf {
            // Show button if not already near the bottom
            val lastVisible = listState.layoutInfo.visibleItemsInfo.lastOrNull()?.index
            val totalItems = listState.layoutInfo.totalItemsCount
            lastVisible != null && lastVisible < totalItems - 1
        }
    }
//    val imeBottom = WindowInsets.ime.getBottom(LocalDensity.current) //every time the keyboard height changes, the list scrolls to the latest message.
    LaunchedEffect(currentUser){
        currentUser?.let {
            messageViewModel.observeTypingStatus(
                roomId = roomID,
                currentUserID = it.email,
                onTypingChanged = { isTyping->
                    someoneIsTyping = isTyping

                }
            )
        }
    }

    LaunchedEffect(messages.size){
        if (messages.isNotEmpty()) {
            listState.animateScrollToItem(messages.lastIndex)
        }
    }
//    LaunchedEffect(imeBottom) {
//        if (messages.isNotEmpty()) {
//            listState.animateScrollToItem(messages.lastIndex)
//        }
//    }
    LaunchedEffect(roomID) {
        messageViewModel.setRoomId(roomID)
    }
    Scaffold(topBar = { TopChatBar(roomName) { navController.navigate(Screen.ChatRoomsScreen.route)} }) {
        Box(modifier = Modifier
            .fillMaxSize()
            .padding(it)
            .background(colorScheme.background)) {

            Column(modifier = Modifier
                .fillMaxSize()
                .statusBarsPadding()) {
                LazyColumn(modifier = Modifier.weight(1f),state = listState) {
                    items(messages) { message ->
                        ChatMessageItem(
                            message.copy(
                                isSentByCurrentUser = message.senderId == messageViewModel.currentUser.value?.email
                            )

                        )
                    }
                }
                if (someoneIsTyping) {
                    Text(
                        text = "Someone is typing...",
                        style = MaterialTheme.typography.bodyMedium,
                        color = Color.Gray
                    )
                }
                Surface(modifier = Modifier
                    .navigationBarsPadding()
                    .imePadding()
                    .padding(10.dp)
                    .border(
                        width = 2.dp,
                        color = colorScheme.outline,
                        shape = RoundedCornerShape(5.dp)
                    )) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        BasicTextField(
                            value = text,
                            onValueChange = { text = it
                                currentUser?.let { it1 ->
                                    messageViewModel.setTypingStatus(roomID,
                                        it1.email,it.isNotEmpty())
                                }
                            },
                            textStyle = LocalTextStyle.current.copy(
                                color = MaterialTheme.colorScheme.onSurface
                            ),
                            modifier = Modifier
                                .weight(1f)
                                .height(56.dp)
                                .padding(12.dp)
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
//            Box(modifier = Modifier
//                .fillMaxSize()
//                .padding(it)
//            ) {
//                Column(
//                    modifier = Modifier
//                        .fillMaxSize()
//                        .statusBarsPadding()
//                ) {
//                    // Your LazyColumn and Input UI...
//                }

                // ✅ FAB to scroll to bottom
                if (showScrollToBottomButton) {
                    AnimatedVisibility(visible = showScrollToBottomButton,
                        modifier = Modifier
                            .align(Alignment.BottomEnd)
                            .navigationBarsPadding()
                            .imePadding()
                            .padding(end = 16.dp, bottom = 70.dp)) {
                             FloatingActionButton(
                            onClick = {
                                coroutineScope.launch {
                                    if (messages.isNotEmpty()) {
                                        listState.animateScrollToItem(messages.lastIndex)
                                    }
                                }
                            },
                            modifier = Modifier

                                .size(30.dp), // Adjust bottom to stay above keyboard/input
                            containerColor = colorScheme.primary,
                        ) {
                            Icon(
                                imageVector = Icons.Default.KeyboardArrowDown,
                                contentDescription = "Scroll to Bottom",
                                tint = colorScheme.onPrimary
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