package com.example.mytodoapp

import android.graphics.Paint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

// Dodaliśmy onDeleteClick, aby MainActivity wiedziało, kiedy usunąć zadanie
class TaskAdapter(
    private val tasks: List<Task>,
    private val onDeleteClick: (Task) -> Unit
) : RecyclerView.Adapter<TaskAdapter.TaskViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_task, parent, false)
        return TaskViewHolder(view)
    }

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        val task = tasks[position]
        holder.title.text = task.title
        holder.checkBox.isChecked = task.isDone

        // Funkcja pomocnicza do wyglądu (przekreślenie)
        fun updateTaskView(isDone: Boolean) {
            if (isDone) {
                holder.title.paintFlags = holder.title.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
                holder.title.alpha = 0.5f
            } else {
                holder.title.paintFlags = holder.title.paintFlags and Paint.STRIKE_THRU_TEXT_FLAG.inv()
                holder.title.alpha = 1.0f
            }
        }

        // Ustawienie początkowego wyglądu
        updateTaskView(task.isDone)

        // Obsługa kliknięcia w kosz
        holder.deleteBtn.setOnClickListener {
            onDeleteClick(task)
        }

        // Obsługa zmiany statusu (opcjonalne wizualnie)
        holder.checkBox.setOnCheckedChangeListener { _, isChecked ->
            updateTaskView(isChecked)
        }
    } // <-- Tutaj brakowało domknięcia onBindViewHolder!

    override fun getItemCount() = tasks.size

    class TaskViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val title: TextView = view.findViewById(R.id.textViewTitle)
        val checkBox: CheckBox = view.findViewById(R.id.checkBoxDone)
        val deleteBtn: ImageButton = view.findViewById(R.id.buttonDelete)
    }
}