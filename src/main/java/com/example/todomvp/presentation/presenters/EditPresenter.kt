package com.example.todomvp.presentation.presenters

import androidx.lifecycle.LiveData
import com.example.todomvp.data.model.Todo

interface EditPresenter {

    fun getTodoById(id: Long): LiveData<Todo>

    fun insert(todo: Todo, next: () -> Unit)

    fun delete(id: Long, next: () -> Unit)
}