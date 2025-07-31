package com.example.chatroom

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    fun provideFirebaseAuth(): FirebaseAuth = FirebaseAuth.getInstance()

    @Provides
    fun provideFirebaseFirestore(): FirebaseFirestore = FirebaseFirestore.getInstance()

    @Provides
    fun provideUserRepository(auth: FirebaseAuth , firestore :FirebaseFirestore): UserRepository {
        return UserRepository(auth,firestore)
    }

    @Provides
    fun provideRoomRepository(firestore :FirebaseFirestore):RoomRepository{
        return RoomRepository(firestore)
    }

    @Provides
    fun provideMessageRepository(firestore :FirebaseFirestore):MessageRepository{
        return MessageRepository(firestore)
    }
}