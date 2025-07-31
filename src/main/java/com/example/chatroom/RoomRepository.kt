package com.example.chatroom

import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class RoomRepository @Inject constructor(private val firestore: FirebaseFirestore) {
    suspend fun createRoom( name:String) :Result<Unit> =
        try{
            val room = Room(name=name)
            Log.d("RoomRepository", "Creating room: $room")
            firestore.collection("rooms").add(room).await()
            Log.d("RoomRepository", "Room created successfully")
            Result.Success(Unit)
        }catch(e:Exception){
            Log.e("RoomRepository", "Failed to create room", e)
            Result.Error(e)
        }


    suspend fun getRooms() :Result<List<Room>> =
        try{
            val querySnapshot = firestore.collection("rooms").get().await()
            val rooms = querySnapshot.documents.map{
                document->
                document.toObject(Room::class.java)!!.copy(id=document.id)
            }
            Result.Success(rooms)
        }catch(e:Exception){
            Result.Error(e)
        }
}