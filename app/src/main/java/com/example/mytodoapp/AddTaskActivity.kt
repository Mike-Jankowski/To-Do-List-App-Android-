package com.example.mytodoapp

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.room.Room // Dodaj ten import

class AddTaskActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_task)

        // 1. Inicjalizacja bazy danych (aby móc zapisać dane)
        val database = Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java, "todo-db"
        ).allowMainThreadQueries().build()

        // 2. Znajdujemy elementy z pliku activity_add_task.xml [cite: 18]
        val editTextTaskName = findViewById<EditText>(R.id.editTextTaskName)
        val buttonSave = findViewById<Button>(R.id.buttonSave)

        // 3. Obsługa kliknięcia przycisku Zapisz [cite: 18]
        buttonSave.setOnClickListener {
            val taskName = editTextTaskName.text.toString()

            if (taskName.isNotEmpty()) {
                // TWORZYMY OBIEKT ZADANIA I ZAPISUJEMY W BAZIE
                val newTask = Task(title = taskName)
                database.taskDao().insert(newTask)

                Toast.makeText(this, "Zapisano w bazie!", Toast.LENGTH_SHORT).show()

                // Zamyka ten ekran i wraca do poprzedniego (MainActivity) [cite: 17]
                finish()
            } else {
                // Walidacja - nie pozwalamy zapisać pustego zadania
                editTextTaskName.error = "Wpisz nazwę zadania!"
            }
        }
    }
}