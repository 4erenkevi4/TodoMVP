package com.example.todomvp.presentation.presenters

import android.app.Application
import androidx.lifecycle.LiveData
import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import com.example.todomvp.presentation.views.EditView
import com.example.todomvp.data.model.Todo
import com.example.todomvp.data.repository.TodoRepositoryImpl
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

@InjectViewState
class EditPresentersImpl(application: Application): MvpPresenter<EditView>(), EditPresenter{

    private val disposable: CompositeDisposable = CompositeDisposable()
    private val repository: TodoRepositoryImpl by lazy {
        TodoRepositoryImpl(application)
    }

    override fun getTodoById(id: Long): LiveData<Todo> {
        return repository.getTodoById(id)
    }

    override fun insert(todo: Todo, next: () -> Unit) {
        disposable.add(repository.insert(todo).subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { next() }
        )
    }

    override fun delete(id: Long, next: () -> Unit) {
        disposable.add(repository.delete(id).subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { next() }
        )
    }







}