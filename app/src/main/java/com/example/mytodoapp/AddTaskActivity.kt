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
                android.util.Log.i("ToDoApp_Log", "Dodano nowe zadanie do bazy danych")

                Toast.makeText(this, "Zapisano w bazie!", Toast.LENGTH_SHORT).show()

                val builder = androidx.core.app.NotificationCompat.Builder(this, "TODO_CHANNEL")
                    .setSmallIcon(android.R.drawable.ic_dialog_info)
                    .setContentTitle("Sukces!")
                    .setContentText("Dodano zadanie: $taskName")
                    .setPriority(androidx.core.app.NotificationCompat.PRIORITY_DEFAULT)

                with(androidx.core.app.NotificationManagerCompat.from(this)) {
                    notify(1, builder.build())
                }

                finish()
            } else {
                // Walidacja - nie pozwalamy zapisać pustego zadania
                editTextTaskName.error = "Wpisz nazwę zadania!"
            }
        }
    }

    private fun createNotificationChannel() {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            val name = "TodoNotifications"
            val descriptionText = "Powiadomienia o zadaniach"
            val importance = android.app.NotificationManager.IMPORTANCE_DEFAULT
            val channel = android.app.NotificationChannel("TODO_CHANNEL", name, importance).apply {
                description = descriptionText
            }
            val notificationManager: android.app.NotificationManager =
                getSystemService(android.content.Context.NOTIFICATION_SERVICE) as android.app.NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }
}