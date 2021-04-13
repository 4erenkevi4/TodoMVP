package com.example.todomvp.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.todomvp.data.model.Todo

@Database(entities = [Todo::class], version = 1)
abstract class TodoRoomDatabase : RoomDatabase() {
    abstract fun todoDao(): TodoDao
    companion object {
        private var INSTANCE: TodoRoomDatabase? = null

        fun getInstance(context: Context): TodoRoomDatabase? {
            return INSTANCE ?: synchronized(TodoRoomDatabase::class) {
                INSTANCE ?: Room.databaseBuilder(
                    context.applicationContext,
                    TodoRoomDatabase::class.java, "todo_database"
                )
                    .build().also {
                        INSTANCE = it
                    }
            }
        }
    }
}
