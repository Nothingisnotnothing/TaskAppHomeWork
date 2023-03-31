package kg.geeks.hw.taskapp.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Task(
    @PrimaryKey(autoGenerate = true)
    val id: Int? = null,
    @ColumnInfo(name = "task_title")
    var title: String? = null,
    @ColumnInfo(name = "task_desc")
    var desc: String? = null
) : java.io.Serializable