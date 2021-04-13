package com.example.todomvp.di

import android.app.Application
import com.example.todomvp.data.repository.TodoRepository
import com.example.todomvp.data.repository.TodoRepositoryImpl
import org.koin.dsl.module

val TodoModule = module{
    single<TodoRepository> { TodoRepositoryImpl(Application()) }
}