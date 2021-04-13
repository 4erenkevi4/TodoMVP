package com.example.todomvp.presentation.views

import com.arellomobile.mvp.MvpView
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType

@StateStrategyType(value = AddToEndSingleStrategy::class)
interface EditView: MvpView {
    fun showDatePicker()
    fun insertMode()
    fun updateMode(id: Long)
    fun insertTodo()
    fun updateTodo(id: Long)
    fun deleteTodo(id: Long)
}