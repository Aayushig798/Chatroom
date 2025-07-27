package com.example.chatroom

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.lifecycle.viewmodel.viewModelFactory
import kotlinx.coroutines.launch

class RoomViewModel(private val roomRepository: RoomRepository):ViewModel() {

    private val _rooms = MutableLiveData<List<Room>>()
    val rooms:LiveData<List<Room>> get() = _rooms

    init{
       RoomRepository(Injection.instance())
        loadRooms()
    }
    fun createRoom(name:String){
        viewModelScope.launch {
            roomRepository.createRoom(name)
        }
    }

     fun loadRooms() {
         viewModelScope.launch {
             when (val result = roomRepository.getRooms()) {
                 is Result.Success -> _rooms.value = result.data
                 is Result.Error -> TODO()
             }
         }
     }
}