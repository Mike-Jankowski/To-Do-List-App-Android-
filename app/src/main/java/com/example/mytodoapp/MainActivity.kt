package com.example.mytodoapp

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.floatingactionbutton.FloatingActionButton
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room

class MainActivity : AppCompatActivity() {

    private lateinit var database: AppDatabase
    private lateinit var taskDao: TaskDao
    private lateinit var recyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // 1. Konfiguracja bazy danych Room
        database = Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java, "todo-db"
        ).allowMainThreadQueries().build()

        taskDao = database.taskDao()

        // 2. Konfiguracja RecyclerView
        recyclerView = findViewById(R.id.recyclerViewTasks)
        recyclerView.layoutManager = LinearLayoutManager(this)

        // 3. Ładowanie danych i ustawienie adaptera z logiką usuwania
        refreshTaskList()

        // 4. Obsługa przycisku "+"
        val fabAdd = findViewById<FloatingActionButton>(R.id.fabAdd)
        fabAdd.setOnClickListener {
            val intent = Intent(this, AddTaskActivity::class.java)
            startActivity(intent)
        }
    }

    // Wyodrębniona funkcja do odświeżania listy (unikanie powtórzeń kodu)
    private fun refreshTaskList() {
        val tasks = taskDao.getAll()
        recyclerView.adapter = TaskAdapter(
            tasks,
            { task ->
                taskDao.delete(task)
                refreshTaskList()
            },
            { updatedTask ->
                taskDao.update(updatedTask)
            }
        )
    }

    override fun onResume() {
        super.onResume()
        // Odświeżamy listę przy każdym powrocie do ekranu głównego
        refreshTaskList()
    }
}