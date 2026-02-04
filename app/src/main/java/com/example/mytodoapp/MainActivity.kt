package com.example.mytodoapp

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainActivity : AppCompatActivity() {

    private lateinit var database: AppDatabase
    private lateinit var taskDao: TaskDao
    private lateinit var recyclerView: RecyclerView
    private val TAG = "ToDoApp_Log"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Log.d(TAG, "MainActivity uruchomione")

        database = Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java, "todo-db"
        ).allowMainThreadQueries().build()
        taskDao = database.taskDao()

        recyclerView = findViewById(R.id.recyclerViewTasks)
        recyclerView.layoutManager = LinearLayoutManager(this)

        refreshTaskList()

        findViewById<FloatingActionButton>(R.id.fabAdd).setOnClickListener {
            startActivity(Intent(this, AddTaskActivity::class.java))
        }
    }

    private fun refreshTaskList() {
        val tasks = taskDao.getAll()
        recyclerView.adapter = TaskAdapter(
            tasks,
            onDeleteClick = { task ->
                taskDao.delete(task)
                Log.i(TAG, "Usunięto zadanie: ${task.title}")
                refreshTaskList()
            },
            onStatusChange = { updatedTask ->
                taskDao.update(updatedTask)
                Log.d(TAG, "Zmieniono status zadania: ${updatedTask.title}")
            }
        )
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_about -> {
                startActivity(Intent(this, AboutActivity::class.java))
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onResume() {
        super.onResume()
        refreshTaskList()
    }
}