package com.example.a24friend.database

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface ChatRoomDao{
    @Insert
    fun insert(chatRoom: ChatRoomEntity)

    @Query("SELECT * FROM chatRoom WHERE userID > :userID")
    fun getChatRoom(userID: String) : ChatRoomEntity

    @Query("DELETE FROM chatRoom")
    fun clear()
}

@Dao
interface MessageDao {
    @Insert
    fun insert(message: MessageEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(message: List<MessageEntity>)

    @Query("SELECT * FROM message ORDER BY time")
    fun getAllMessages() : LiveData<List<MessageEntity>>

    @Query("DELETE FROM message")
    fun clear()
}

@Database(entities = [MessageEntity::class, ChatRoomEntity::class], version = 1, exportSchema = false)
abstract class MessageDatabase : RoomDatabase() {
    abstract val messageDao: MessageDao
    abstract val chatRoomDao: ChatRoomDao
}

private lateinit var INSTANCE: MessageDatabase

fun getDatabase(context: Context): MessageDatabase {
    synchronized(MessageDatabase::class.java) {
        if (!::INSTANCE.isInitialized)  {
            INSTANCE = Room.databaseBuilder(context.applicationContext,
                MessageDatabase::class.java,
                "message_db").build()
        }
    }
    return INSTANCE
}