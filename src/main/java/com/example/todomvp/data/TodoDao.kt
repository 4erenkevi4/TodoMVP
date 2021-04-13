package com.example.todomvp.data
import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.todomvp.data.model.Todo

@Dao
interface TodoDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(todo: Todo)
    @Query("DELETE FROM todo WHERE id = :id")
    fun deleteById(id: Long)
    @Query("SELECT * FROM todo ORDER BY date ASC")
    fun getAllTodos(): LiveData<List<Todo>>
    @Query("SELECT * FROM todo WHERE id = :id")
    fun getTodoById(id: Long): LiveData<Todo>
}