package com.example.todomvp.presentation

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.animation.AnimationUtils
import android.widget.Switch
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.constraintlayout.motion.widget.MotionLayout
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.arellomobile.mvp.presenter.InjectPresenter
import com.example.todomvp.R
import com.example.todomvp.data.model.Todo
import com.example.todomvp.presentation.adapter.TodoAdapter
import com.example.todomvp.presentation.presenters.MainPresentersImpl
import com.example.todomvp.presentation.views.MainView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import java.util.*
import kotlin.collections.ArrayList

class MainActivity : AppCompatActivity(), MainView {
    private val list: ArrayList<Todo> by lazy { arrayListOf<Todo>() }

    @SuppressLint("UseSwitchCompatOrMaterialCode")
    private lateinit var switchTheme: Switch
    private lateinit var recyclerView: RecyclerView
    private lateinit var fab: FloatingActionButton
    private val CHEK_STATE = "CHEK_STATE"
    private val NIGHT_THEME = "NIGHT_THEME"
    lateinit var container: MotionLayout

    @InjectPresenter
    lateinit var mainPresenter: MainPresentersImpl

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (chekTheme()) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            setTheme(R.style.MyThemeDark)
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            setTheme(R.style.MyTheme)
        }
        setContentView(R.layout.activity_main)
        val adapter = TodoAdapter(list) { id ->
            startActivity(Intent(this@MainActivity, EditActivity::class.java).putExtra("id", id))
        }
        mainPresenter = MainPresentersImpl(application)
        mainPresenter.getAllTodos().observe(this, Observer {
            list.clear()
            list.addAll(it!!)
            adapter.notifyDataSetChanged()
        })
        setFab()
        setRecyclerview(adapter)
        setChangeTheme()
    }

    override fun setRecyclerview(adapter: TodoAdapter) {
        recyclerView = findViewById(R.id.recycler_view)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter
        ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(
            ItemTouchHelper.UP or ItemTouchHelper.DOWN,
            ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
        ) {
            override fun getMovementFlags(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder
            ): Int {
                val dragFlags = ItemTouchHelper.UP or ItemTouchHelper.DOWN
                val swipeFlags = ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
                return makeMovementFlags(dragFlags, swipeFlags)
            }

            override fun onMove(
                p0: RecyclerView,
                p1: RecyclerView.ViewHolder,
                p2: RecyclerView.ViewHolder
            ): Boolean {
                val sourcePosition = p1.layoutPosition
                val targetPosition = p2.layoutPosition
                Collections.swap(list, sourcePosition, targetPosition)
                adapter.notifyItemMoved(sourcePosition, targetPosition)
                return true
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                mainPresenter.onTaskSwiped(viewHolder.bindingAdapterPosition.toLong())
            }
        }
        ).attachToRecyclerView(recyclerView)
    }

    override fun setFab() {
        val animForFab = AnimationUtils.loadAnimation(this, R.anim.fab_go)
        fab = findViewById(R.id.fab)
        fab.startAnimation(animForFab)
        fab.setOnClickListener {
            container = findViewById(R.id.motion_container)
            container.transitionToEnd()
            container.setTransitionListener(
                object : MotionLayout.TransitionListener {
                    override fun onTransitionStarted(p0: MotionLayout?, p1: Int, p2: Int) {
                    }

                    override fun onTransitionChange(
                        motionLayout: MotionLayout?,
                        startId: Int,
                        endId: Int,
                        progress: Float
                    ) {
                    }

                    override fun onTransitionCompleted(
                        motionLayout: MotionLayout?,
                        currentId: Int
                    ) {
                        container.transitionToStart()
                        if (currentId == R.id.ending_set) {

                            startActivity(Intent(this@MainActivity, EditActivity::class.java))
                        }
                    }

                    override fun onTransitionTrigger(
                        p0: MotionLayout?,
                        p1: Int,
                        p2: Boolean,
                        p3: Float
                    ) {

                    }
                })
        }
    }

    override fun setChangeTheme() {
        switchTheme = findViewById(R.id.switch_theme)
        if (chekTheme()) {
            switchTheme.isChecked = true
        }
        switchTheme.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                saveTheme(true)
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                saveTheme(false)
            }
        }
    }

    private fun saveTheme(state: Boolean) {
        val sharedPreferences: SharedPreferences =
            this.getSharedPreferences(CHEK_STATE, Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putBoolean(NIGHT_THEME, state)
        editor.apply()
    }

    private fun chekTheme(): Boolean {
        val sharedPreferences: SharedPreferences =
            this.getSharedPreferences(CHEK_STATE, Context.MODE_PRIVATE)
        return sharedPreferences.getBoolean(NIGHT_THEME, false)
    }
}


