package com.example.mytodoapp

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class AddTaskActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_task)

        // 1. Znajdujemy elementy z pliku activity_add_task.xml
        val editTextTaskName = findViewById<EditText>(R.id.editTextTaskName)
        val buttonSave = findViewById<Button>(R.id.buttonSave)

        // 2. Obsługa kliknięcia przycisku Zapisz
        buttonSave.setOnClickListener {
            val taskName = editTextTaskName.text.toString()

            if (taskName.isNotEmpty()) {
                // Tymczasowa informacja dla Ciebie, że działa
                Toast.makeText(this, "Zapisano: $taskName", Toast.LENGTH_SHORT).show()

                // Zamyka ten ekran i wraca do poprzedniego (MainActivity)
                finish()
            } else {
                // Walidacja - nie pozwalamy zapisać pustego zadania
                editTextTaskName.error = "Wpisz nazwę zadania!"
            }
        }
    }
}