package com.example.czesioreminder.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

@Database(entities = [Task::class], version = 2, exportSchema = false)
abstract class TaskDatabase : RoomDatabase() {
    abstract fun taskDao(): TaskDao

    companion object {
        @Volatile
        private var Instance: TaskDatabase? = null

        // Dodaj migrację
        private val MIGRATION_1_2 = object : Migration(1, 2) {
            override fun migrate(database: SupportSQLiteDatabase) {
                // Jeśli dodałeś nową kolumnę, np. "priority", wykonaj migrację:
                database.execSQL("ALTER TABLE tasks ADD COLUMN priority INTEGER DEFAULT 0")
            }
        }

        fun getDatabase(context: Context): TaskDatabase {
            return Instance ?: synchronized(this) {
                Room.databaseBuilder(context, TaskDatabase::class.java, "task_database")
                    .addMigrations(MIGRATION_1_2) // Dodaj migrację do konfiguracji
                    .build()
                    .also { Instance = it }
            }
        }
    }
}