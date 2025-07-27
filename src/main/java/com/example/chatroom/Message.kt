package com.example.chatroom

data class Message(
    val senderId: String = "",
    val text: String = "",
    val senderFirstName: String = "",
    val timestamp: Long = 0L,
    val isSentByCurrentUser: Boolean = false
)