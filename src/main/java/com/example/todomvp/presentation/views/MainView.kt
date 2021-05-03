package com.example.todomvp.presentation.views

import com.arellomobile.mvp.MvpView
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType
import com.example.todomvp.presentation.adapter.TodoAdapter

@StateStrategyType(value = AddToEndSingleStrategy::class)
interface MainView: MvpView  {


    fun setRecyclerview( adapter: TodoAdapter)

    fun setFab()

    fun setChangeTheme()


}