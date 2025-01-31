package com.example.czesioreminder.data

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface TaskDao {
    @Query("SELECT * FROM tasks ORDER BY date ASC")
    fun getTasks(): Flow<List<Task>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(task: Task)

    @Query("SELECT * FROM tasks WHERE id = :id")
    suspend fun getTaskById(id: Int): Task?

    @Update
    suspend fun update(task: Task)

    @Delete
    suspend fun delete(task: Task)

    // Możesz dodać nowe zapytania, np. do filtrowania po priorytecie
    @Query("SELECT * FROM tasks WHERE priority = :priority ORDER BY date ASC")
    fun getTasksByPriority(priority: Int): Flow<List<Task>>
}