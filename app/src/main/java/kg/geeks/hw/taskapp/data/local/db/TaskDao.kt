package kg.geeks.hw.taskapp.data.local.db

import androidx.room.*
import kg.geeks.hw.taskapp.model.Task
import kg.geeks.hw.taskapp.model.Task.Companion.TABLE_NAME

//SELECT * FROM "название таблицы" WHERE "название переменной сущности" =:"название переменной" DAO"
@Dao
interface TaskDao {

    @Insert
    fun insert(task: Task)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun update(task: Task)

    @Query("SELECT * FROM $TABLE_NAME WHERE task_id = :taskId")
    fun getTask(taskId: Int): Task

    @Query("DELETE FROM $TABLE_NAME WHERE task_id = :taskId")
    fun deleteTask(taskId: Int)

    @Query("SELECT * FROM $TABLE_NAME ORDER BY task_id ASC")
    fun getAllTasks(): List<Task>
}