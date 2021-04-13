package com.example.todomvp.presentation

import android.app.DatePickerDialog
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.arellomobile.mvp.presenter.InjectPresenter
import com.example.todomvp.R
import com.example.todomvp.data.model.Todo
import com.example.todomvp.presentation.presenters.EditPresenter
import com.example.todomvp.presentation.presenters.EditPresentersImpl
import com.example.todomvp.presentation.views.EditView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import java.text.SimpleDateFormat
import java.util.*

class EditActivity : AppCompatActivity(), EditView {

    private lateinit var deleteFab: FloatingActionButton
    private lateinit var doneFab: FloatingActionButton

    private lateinit var descriptionText: EditText
    private lateinit var todoEditText: EditText
    private lateinit var dateView: TextView
    private var textData: TextView? = null

    @InjectPresenter
    lateinit var editPresenter:EditPresentersImpl

            override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit)
        deleteFab = findViewById(R.id.deleteFab)
        doneFab = findViewById(R.id.doneFab)
        todoEditText = findViewById(R.id.todoEditText)
        descriptionText = findViewById(R.id.description_text)
        textData?.findViewById<TextView>(R.id.data)
                editPresenter = EditPresentersImpl(application)

        val id = intent.getLongExtra("id", -1L)
        if (id == -1L) {
            insertMode()
        } else {
            updateMode(id)
        }
        dateView = findViewById(R.id.date_view)
        dateView.setOnClickListener {
            showDatePicker()
        }
    }


    override fun showDatePicker() {
        val dateView: TextView = findViewById(R.id.date_view)
        dateView.text = SimpleDateFormat("dd.MM.yyyy").format(System.currentTimeMillis())
        val cal = Calendar.getInstance()
        val dateSetListener =
            DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
                cal.set(Calendar.YEAR, year)
                cal.set(Calendar.MONTH, monthOfYear)
                cal.set(Calendar.DAY_OF_MONTH, dayOfMonth)
                val myFormat = "dd.MM.yyyy"
                val sdf = SimpleDateFormat(myFormat, Locale.US)
                dateView.text = sdf.format(cal.time)
            }
        dateView.setOnClickListener {
            DatePickerDialog(
                this, dateSetListener,
                cal.get(Calendar.YEAR),
                cal.get(Calendar.MONTH),
                cal.get(Calendar.DAY_OF_MONTH)
            ).show()
        }
    }





    override fun insertMode() {
        deleteFab.visibility = View.GONE
        doneFab.setOnClickListener {
            insertTodo()
        }
    }

    override fun updateMode(id: Long) {
        editPresenter.getTodoById(id).observe(this, androidx.lifecycle.Observer { todo ->
            todo?.let {
                todoEditText.setText(todo.title)
                descriptionText.setText(todo.description)
                dateView.text = todo.date
            }
        })
        doneFab.setOnClickListener {
            updateTodo(id)
        }
        deleteFab.setOnClickListener {
            deleteTodo(id)
        }
    }

    override fun insertTodo() {
        val todo = Todo(
            0,
            todoEditText.text.toString(), descriptionText.text.toString(), dateView.text.toString()
        )
        editPresenter.insert(todo) { finish() }
    }

    override fun updateTodo(id: Long) {
        val todo = Todo(id,
            todoEditText.text.toString(), descriptionText.text.toString(), dateView.text.toString()
        )
        editPresenter.insert(todo) { finish() }
    }

    override fun deleteTodo(id: Long) {
        editPresenter.delete(id) { finish() }
    }

}









