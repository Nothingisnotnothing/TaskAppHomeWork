package kg.geeks.hw.taskapp.data.local.db

import androidx.room.Database
import androidx.room.RoomDatabase
import kg.geeks.hw.taskapp.model.Task

@Database(entities = [Task::class], version = 1, exportSchema = false)
abstract class AppDataBase : RoomDatabase() {
    abstract fun taskDao(): TaskDao
}