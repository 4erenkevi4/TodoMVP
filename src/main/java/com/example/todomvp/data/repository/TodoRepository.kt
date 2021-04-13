package com.example.todomvp.data.repository

import androidx.lifecycle.LiveData
import com.example.todomvp.data.model.Todo
import io.reactivex.Observable

interface TodoRepository {
    fun getAllTodos(): LiveData<List<Todo>>
    fun getTodoById(id: Long): LiveData<Todo>
    fun insert(todo: Todo): Observable<Unit>
    fun delete(id: Long): Observable<Unit>
}