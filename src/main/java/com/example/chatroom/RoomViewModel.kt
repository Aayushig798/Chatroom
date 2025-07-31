package com.example.chatroom

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.lifecycle.viewmodel.viewModelFactory

import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class RoomViewModel@Inject constructor( private val roomRepository:RoomRepository)
:ViewModel() {


    private val _rooms = MutableLiveData<List<Room>>()
    val rooms:LiveData<List<Room>> get() = _rooms

    init{
        loadRooms()
    }
    fun createRoom(name:String){
        viewModelScope.launch {
            roomRepository.createRoom(name)
            loadRooms()
        }
    }

     fun loadRooms() {
         viewModelScope.launch {
             when (val result = roomRepository.getRooms()) {
                 is Result.Success -> _rooms.value = result.data
                 is Result.Error ->{}
             }
         }
     }
}