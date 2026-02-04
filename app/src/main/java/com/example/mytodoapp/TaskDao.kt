package com.example.mytodoapp
import androidx.room.*

@Dao
interface TaskDao {
    @Insert
    fun insert(task: Task)

    @Query("SELECT * FROM tasks")
    fun getAll(): List<Task>

    @Delete
    fun delete(task: Task)

    @Update
    fun update(task: Task)
}