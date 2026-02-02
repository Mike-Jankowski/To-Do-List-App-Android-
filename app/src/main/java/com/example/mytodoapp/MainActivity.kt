package com.example.mytodoapp

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // 1. Znajdujemy przycisk po ID z pliku XML [cite: 18]
        val fabAdd = findViewById<FloatingActionButton>(R.id.fabAdd)

        // 2. Ustawiamy "słuchacza" kliknięć [cite: 18]
        fabAdd.setOnClickListener {
            // 3. Tworzymy Intencję, aby przejść do AddTaskActivity
            val intent = Intent(this, AddTaskActivity::class.java)
            startActivity(intent)
        }
    }
}