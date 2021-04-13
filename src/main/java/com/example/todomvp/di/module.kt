package com.example.todomvp.di

import com.example.todomvp.data.repository.TodoRepository
import com.example.todomvp.data.repository.TodoRepositoryImpl
import com.example.todomvp.presentation.presenters.EditPresenter
import com.example.todomvp.presentation.presenters.EditPresentersImpl
import com.example.todomvp.presentation.presenters.MainPresenter
import com.example.todomvp.presentation.presenters.MainPresentersImpl
import org.koin.dsl.module

val todoModule = module {
    single<TodoRepository> { TodoRepositoryImpl(get()) }
    single<EditPresenter> { EditPresentersImpl(get()) }
    single<MainPresenter> { MainPresentersImpl(get()) }
}





