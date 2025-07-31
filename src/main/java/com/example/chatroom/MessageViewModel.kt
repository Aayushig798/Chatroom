package com.example.chatroom

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class MessageViewModel @Inject constructor(private val messageRepository: MessageRepository,
                                           private val userRepository: UserRepository) : ViewModel() {





    private val _messages = MutableLiveData<List<Message>>()
    val messages: LiveData<List<Message>> get() = _messages

    private val _isLoading = MutableLiveData<Boolean>(true)
    val isLoading: LiveData<Boolean> = _isLoading


    private val _roomId = MutableLiveData<String>()
    private val _currentUser = MutableLiveData<User>()
    val currentUser: LiveData<User> get() = _currentUser

    init {
        loadCurrentUser()
    }

    public fun loadCurrentUser() {
        viewModelScope.launch {
            when (val result = userRepository.getCurrentUser()) {
                is Result.Success -> {
                    _currentUser.value = result.data
                    Log.d("MessageViewModel", "User loaded: ${result.data}")
                }
                is Result.Error -> {
                    Log.e("MessageViewModel", "Failed to load user", result.exception)
                }
            }
        }
    }

    fun loadMessages() {
        viewModelScope.launch {
            messageRepository.getChatMessages(_roomId.value.toString())
                .collect { _messages.value = it }
        }
    }

    fun sendMessage(text: String) {
        Log.d("MessageViewModel", "sendMessage called")
        try{
            Log.d("messageview","in try block before if:${_currentUser.value}")
        if (_currentUser.value != null) {
            val message = Message(
                senderFirstName = _currentUser.value!!.firstName,
                senderId = _currentUser.value!!.email,
                text = text,
                timestamp =System.currentTimeMillis()
            )
            Log.d("messageview","in try block after if")
            viewModelScope.launch {
                Log.d("MessageViewModel", "Calling repository.sendMessage")
                when (messageRepository.sendMessage(_roomId.value.toString(), message)) {
                    is Result.Success -> Unit
                    is Result.Error -> {

                    }
                }
            }
        }
    }catch(e:Exception){
        Log.d("messageview model","IN EXCEPTION BLOCK",e)

        }
    }

    fun setRoomId(roomId: String) {
        Log.d("MessageViewModel", "setRoomId: $roomId")
        _roomId.value = roomId
        loadMessages()
    }
}