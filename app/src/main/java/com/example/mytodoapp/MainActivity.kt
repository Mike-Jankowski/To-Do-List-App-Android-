package com.example.mytodoapp

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.floatingactionbutton.FloatingActionButton
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // setContentView MUSI być przed szukaniem widoków
        setContentView(R.layout.activity_main)

        // 1. Konfiguracja RecyclerView (Wymaganie nr 5)
        val recyclerView = findViewById<RecyclerView>(R.id.recyclerViewTasks)
        recyclerView.layoutManager = LinearLayoutManager(this)

        // Tymczasowe dane do testu (zanim podepniemy bazę danych Room)
        val dummyTasks = listOf(
            Task(title = "Zrobić projekt z mobilnych"),
            Task(title = "Wysłać kod na Git"),
            Task(title = "Dokończyć dokumentację")
        )

        // Ustawienie adaptera, który stworzyłeś wcześniej
        recyclerView.adapter = TaskAdapter(dummyTasks)

        // 2. Znajdujemy przycisk po ID z pliku XML (Wymaganie nr 2) [cite: 18]
        val fabAdd = findViewById<FloatingActionButton>(R.id.fabAdd)

        // 3. Obsługa kliknięcia - nawigacja (Wymaganie nr 1) [cite: 17, 18]
        fabAdd.setOnClickListener {
            val intent = Intent(this, AddTaskActivity::class.java)
            startActivity(intent)
        }
    }
}