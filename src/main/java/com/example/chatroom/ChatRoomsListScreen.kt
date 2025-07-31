package com.example.chatroom

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.material3.OutlinedTextField
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.Center
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontStyle
import androidx.navigation.NavHostController
import com.google.rpc.context.AttributeContext.Resource


@SuppressLint("ResourceAsColor")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChatRoomsListScreen(navController : NavHostController, roomViewModel: RoomViewModel, onJoinClicked:(Room)->Unit) {

    var showDialog by remember { mutableStateOf(false) }
    var chatRoomName by remember { mutableStateOf("") }
    val rooms by roomViewModel.rooms.observeAsState(emptyList())
    val isloading by roomViewModel.isLoading.observeAsState(true)

    Column(modifier = Modifier.fillMaxSize()) {
        Row(modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween) {
            Text(
                text = "CHAT ROOMS", fontSize = 20.sp, fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .statusBarsPadding()
                    .padding(10.dp)
            )
            Text(
                text = "LOG OUT", fontSize = 20.sp, fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .statusBarsPadding()
                    .padding(10.dp).clickable { navController.navigate(Screen.SignupScreen.route) }
            )
        }
        if(isloading){
            CircularProgressIndicator(modifier = Modifier.align(Alignment.CenterHorizontally), color = colorResource(id = R.color.app_color))
        }else {
            LazyColumn(modifier = Modifier.padding(5.dp)) {
                items(rooms, key = { room -> room.id }) { room ->
                    RoomItem(room, onJoinClicked)
                }
            }
        }
            Button(onClick = {showDialog=true}, colors = ButtonColors(
                containerColor = colorResource(id=R.color.app_color),
                contentColor = Color.White,
                disabledContainerColor =colorResource(id=R.color.app_color),
                disabledContentColor = Color.White
            ), modifier = Modifier.fillMaxWidth().padding(10.dp)){
                Text("CREATE ROOM")
            }


        }




        if (showDialog) {
            AlertDialog(containerColor = Color.LightGray,title = { Text("CREATE A NEW ROOM") },
                onDismissRequest = { showDialog = false },
                confirmButton = {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceEvenly
                    ) {
                        Button(onClick = {
                            if (chatRoomName.isNotBlank()) {
                                roomViewModel.createRoom(chatRoomName.trim())
                                showDialog = false
                                chatRoomName = ""
                            }
                        }, colors = ButtonColors(
                            containerColor = colorResource(id=R.color.app_color),
                            contentColor = Color.White,
                            disabledContainerColor =colorResource(id=R.color.app_color),
                            disabledContentColor = Color.White)) { Text("ADD") }
                        Button(onClick = { showDialog = false }, colors = ButtonColors(
                            containerColor = colorResource(id=R.color.app_color),
                            contentColor = Color.White,
                            disabledContainerColor =colorResource(id=R.color.app_color),
                            disabledContentColor = Color.White)) { Text("CANCEL") }
                    }
                },
                text = {
                    OutlinedTextField(
                        value = chatRoomName,
                        onValueChange = { chatRoomName = it }
                    )
                }
            )
        }
    }




@Composable
fun RoomItem(room:Room ,onJoinClicked: (Room) -> Unit){

        Card(modifier = Modifier.fillMaxWidth().padding(10.dp).border(width = 2.dp,Color.LightGray,shape =RoundedCornerShape(10.dp)), colors = CardColors(
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
                    containerColor = colorResource(id=R.color.app_color),
                    contentColor = Color.White,
                    disabledContainerColor =colorResource(id=R.color.app_color),
                    disabledContentColor = Color.White
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
