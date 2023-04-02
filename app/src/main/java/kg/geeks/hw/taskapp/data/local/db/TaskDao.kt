package kg.geeks.hw.taskapp.data.local.db

import androidx.room.*
import kg.geeks.hw.taskapp.model.Task

@Dao
interface TaskDao {

    @Insert
    fun insert(task: Task)

    @Update
    fun update(task: Task)

    @Delete
    fun delete(task: Task)

    @Query("SELECT * FROM task ORDER BY id ASC")
    fun getAll(): List<Task>

    @Query("SELECT * FROM task WHERE id = :id")
    fun getMovie(id: Int): List<Task>
}