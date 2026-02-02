package com.example.mytodoapp

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.floatingactionbutton.FloatingActionButton
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room

class MainActivity : AppCompatActivity() {

    // Definiujemy bazę danych i DAO jako zmienne klasowe
    private lateinit var database: AppDatabase
    private lateinit var taskDao: TaskDao

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // 1. Konfiguracja bazy danych Room
        database = Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java, "todo-db"
        ).allowMainThreadQueries() // Uwaga: Tylko do celów projektowych, by uprościć kod
            .build()

        taskDao = database.taskDao()

        // 2. Konfiguracja RecyclerView [cite: 21]
        val recyclerView = findViewById<RecyclerView>(R.id.recyclerViewTasks)
        recyclerView.layoutManager = LinearLayoutManager(this)

        // 3. Pobieranie PRAWDZIWYCH danych z bazy
        val tasksFromDb = taskDao.getAll()
        recyclerView.adapter = TaskAdapter(tasksFromDb)

        // 4. Obsługa przycisku "+" (Nawigacja) [cite: 17, 18]
        val fabAdd = findViewById<FloatingActionButton>(R.id.fabAdd)
        fabAdd.setOnClickListener {
            val intent = Intent(this, AddTaskActivity::class.java)
            startActivity(intent)
        }
    }

    // Odświeżanie listy po powrocie z ekranu dodawania
    override fun onResume() {
        super.onResume()
        val recyclerView = findViewById<RecyclerView>(R.id.recyclerViewTasks)
        recyclerView.adapter = TaskAdapter(taskDao.getAll())
    }
}