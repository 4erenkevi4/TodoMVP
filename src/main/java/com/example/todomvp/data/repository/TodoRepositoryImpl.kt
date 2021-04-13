package com.example.todomvp.data.repository
import android.app.Application
import androidx.lifecycle.LiveData
import com.example.todomvp.data.model.Todo
import com.example.todomvp.data.TodoDao
import com.example.todomvp.data.TodoRoomDatabase
import io.reactivex.Observable

class TodoRepositoryImpl(application: Application):
    TodoRepository {
    private val todoDao: TodoDao by lazy {
        val db = TodoRoomDatabase.getInstance(application)!!
        db.todoDao()
    }
    private val todos: LiveData<List<Todo>> by lazy {
        todoDao.getAllTodos()
    }

    override fun getAllTodos(): LiveData<List<Todo>> {
        return todos
    }

    override fun getTodoById(id: Long): LiveData<Todo> {
        return todoDao.getTodoById(id)
    }

    override fun insert(todo: Todo): Observable<Unit> {
        return Observable.fromCallable { todoDao.insert(todo) }
    }

    override fun delete(id: Long): Observable<Unit> {
        return Observable.fromCallable { todoDao.deleteById(id) }
    }
}
