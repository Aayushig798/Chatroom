package com.example.chatroom

import android.annotation.SuppressLint
import android.app.Activity
import android.graphics.Paint.Align
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box

import androidx.compose.foundation.layout.Column

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
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
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DismissibleNavigationDrawer
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconToggleButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TextFieldColors
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.Center
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.style.TextDecoration
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.google.rpc.context.AttributeContext.Resource
import kotlinx.coroutines.launch



@SuppressLint("ResourceAsColor")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChatRoomsListScreen(navController : NavHostController, authViewModel: AuthViewModel, roomViewModel: RoomViewModel, onJoinClicked:(Room)->Unit) {

    var showDialog by remember { mutableStateOf(false) }
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)

    val controller = rememberNavController()
    val navBackStackEntry by controller.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    val scope = rememberCoroutineScope()
    var chatRoomName by remember { mutableStateOf("") }
    val rooms by roomViewModel.rooms.observeAsState(emptyList())
    val isloading by roomViewModel.isLoading.observeAsState(true)
    val context = LocalContext.current




    ModalNavigationDrawer(drawerState = drawerState ,
                            drawerContent = {
                                Column(modifier = Modifier
                                    .fillMaxHeight()
                                    .width(280.dp) // Half-width drawer
                                    .background(Color.White)
                                    .statusBarsPadding()
                                    .padding(16.dp)){
                                LazyColumn {
                                    items(drawerItemsList) { item ->
                                        DrawerItem(
                                            selected = currentRoute == item.dRoute,
                                            item = item
                                        ) {
                                            scope.launch {
                                                drawerState.close()
                                            }
                                        }
                                    }
                                }
                                    Spacer(modifier = Modifier.height(5.dp))
                                    Text(text = "LOG OUT", modifier = Modifier.clickable { navController.navigate(Screen.LoginScreen.route)
                                                authViewModel.logOut()})

                                }
                            }) {

        Scaffold(

            topBar = {
                topChatRoomScreenBar("CHAT ROOM",
                    onMenuClick = { scope.launch { drawerState.open() } })
            },
            floatingActionButton = {
                FloatingActionButton(onClick = {showDialog = true},
                                        containerColor = MaterialTheme.colorScheme.primary,
                                        contentColor = MaterialTheme.colorScheme.onPrimary) {
                    Icon(Icons.Default.Add, contentDescription = "Add")

                }
            }

            ) {


                if (isloading) {
                    Box(modifier = Modifier.fillMaxSize()) {
                        CircularProgressIndicator(
                            modifier = Modifier.align(Center),
                            color = MaterialTheme.colorScheme.onBackground
                        )
                    }
                } else {
                    LazyColumn(modifier = Modifier.padding(it)) {
                        items(rooms, key = { room -> room.id }) { room ->
                            RoomItem(room, onJoinClicked)
                        }
                    }
                }
            }
//                Button(
//                    onClick = { showDialog = true }, colors = ButtonColors(
//                        containerColor = MaterialTheme.colorScheme.primary,
//                        contentColor = MaterialTheme.colorScheme.onPrimary,
//                        disabledContainerColor = MaterialTheme.colorScheme.primary,
//                        disabledContentColor = MaterialTheme.colorScheme.onPrimary
//                    ), modifier = Modifier
//                        .fillMaxWidth()
//                        .padding(10.dp)
//                ) {
//                    Text("CREATE ROOM")
//                }

        }








        if (showDialog) {
            AlertDialog(containerColor =    MaterialTheme.colorScheme.surface,
                title = { Text("CREATE A NEW ROOM",color = MaterialTheme.colorScheme.onSurface,
                    textDecoration = TextDecoration.Underline) },
                onDismissRequest = { showDialog = false },
                confirmButton = {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Button(onClick = {
                            if (chatRoomName.isNotBlank()) {
                                roomViewModel.createRoom(chatRoomName.trim())
                                showDialog = false
                                chatRoomName = ""
                            }
                        }, colors = ButtonColors(
                            containerColor = MaterialTheme.colorScheme.primary,
                            contentColor = MaterialTheme.colorScheme.onPrimary,
                            disabledContainerColor =MaterialTheme.colorScheme.primary,
                            disabledContentColor = MaterialTheme.colorScheme.onPrimary)) { Text("ADD") }
                        Button(onClick = { showDialog = false }, colors = ButtonColors(
                            containerColor = MaterialTheme.colorScheme.primary,
                            contentColor = MaterialTheme.colorScheme.onPrimary,
                            disabledContainerColor =MaterialTheme.colorScheme.primary,
                            disabledContentColor = MaterialTheme.colorScheme.onPrimary)) { Text("CANCEL") }
                    }
                },
                text = {
                    OutlinedTextField(
                        value = chatRoomName,
                        onValueChange = { chatRoomName = it },
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = MaterialTheme.colorScheme.onSurface,
                            unfocusedBorderColor =  MaterialTheme.colorScheme.onSurface
                        )

                    )
                }
            )
        }
    }




@Composable
fun RoomItem(room:Room ,onJoinClicked: (Room) -> Unit){

        Card(modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp)
            .border(width = 2.dp, Color.LightGray, shape = RoundedCornerShape(10.dp)), colors = CardColors(
            containerColor = Color.White,
            contentColor = Color.Black,
            disabledContainerColor = Color.White,
            disabledContentColor = Color.Black
        )
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = room.name, color = Color.Black,
                    fontSize = 20.sp, fontStyle = FontStyle.Italic, fontWeight = FontWeight.Bold,
                    modifier = Modifier.align(Alignment.CenterVertically)
                )
                Button(onClick = {onJoinClicked(room)}, colors = ButtonColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    contentColor = MaterialTheme.colorScheme.onPrimary,
                    disabledContainerColor =MaterialTheme.colorScheme.primary,
                    disabledContentColor = MaterialTheme.colorScheme.onPrimary
                )) {
                    Text("JOIN")
                }
            }
        }
    }

//
//
@Preview(showBackground = true)
@Composable
fun chat() {
    RoomItem(Room("", "FOREIGN")){}
}
