package kg.geeks.hw.taskapp.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kg.geeks.hw.taskapp.model.Task.Companion.TABLE_NAME

@Entity(tableName = TABLE_NAME)
data class Task(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "task_id")
    val id: Int? = null,
    @ColumnInfo(name = "task_title")
    var title: String? = null,
    @ColumnInfo(name = "task_desc")
    var desc: String? = null
) : java.io.Serializable {
    companion object {
        const val TABLE_NAME = "task_list_entities_table"
    }
}