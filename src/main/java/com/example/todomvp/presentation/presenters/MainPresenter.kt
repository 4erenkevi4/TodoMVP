package com.example.todomvp.presentation.presenters

import androidx.lifecycle.LiveData
import com.example.todomvp.data.model.Todo
import java.text.FieldPosition

interface MainPresenter {

    fun getAllTodos(): LiveData<List<Todo>>
    fun onTaskSwiped (position: Long)

}