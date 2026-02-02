package com.example.mytodoapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Znajdujemy przycisk "+" z naszego XMLa
        val fabAdd = findViewById<com.google.android.material.floatingactionbutton.FloatingActionButton>(R.id.fabAdd)

        // Ustawiamy interakcję (Wymaganie nr 2 [cite: 18])
        fabAdd.setOnClickListener {
            // Nawigacja do drugiego ekranu (Wymaganie nr 1 i 17 [cite: 17])
            val intent = android.content.Intent(this, AddTaskActivity::class.java)
            startActivity(intent)
        }
    }
}