package com.example.todomvp.presentation.presenters

import androidx.lifecycle.LiveData
import com.example.todomvp.data.model.Todo

interface MainPresenter {

    fun getAllTodos(): LiveData<List<Todo>>
    fun onTaskSwiped(position: Long)

}