package com.example.todomvp.presentation.presenters

import android.app.Application
import androidx.lifecycle.LiveData
import com.arellomobile.mvp.MvpPresenter
import com.example.todomvp.data.model.Todo
import com.example.todomvp.presentation.views.MainView
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import com.example.todomvp.data.repository.TodoRepositoryImpl

class MainPresentersImpl(application: Application):MvpPresenter<MainView>(), MainPresenter {

    private val repository:TodoRepositoryImpl = TodoRepositoryImpl(application)
    private val disposable: CompositeDisposable = CompositeDisposable()
    private val todos: LiveData<List<Todo>> by lazy {
        repository.getAllTodos()
    }
    override fun getAllTodos() = todos


    override fun onTaskSwiped(position: Long) {
        disposable.add(
            repository.delete(position).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe()
        )
    }



}




