package kg.geeks.hw.taskapp.ui.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import kg.geeks.hw.taskapp.databinding.ItemTaskBinding
import kg.geeks.hw.taskapp.model.Task

class TaskAdapter(
    private val onDeleteClick: (position: Int) -> Unit,
    private val onItemClick: (position: Int) -> Unit
) : Adapter<TaskAdapter.TaskViewHolder>() {

    private var taskList: ArrayList<Task> = arrayListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        return TaskViewHolder(
            ItemTaskBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int = taskList.size

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        holder.bind(taskList[position])
    }

    fun addTasks(task: List<Task>) {
        taskList.clear()
        taskList.addAll(task)
        notifyDataSetChanged()
    }

    fun getTask(position: Int): Task {
        return taskList[position]
    }

    inner class TaskViewHolder(private val binding: ItemTaskBinding) :
        ViewHolder(binding.root) {

        fun bind(task: Task) {
            binding.apply {
                tvTitle.text = task.title
                tvDesc.text = task.desc
            }
            itemView.setOnLongClickListener {
                onDeleteClick(adapterPosition)
                false
            }
            itemView.setOnClickListener {
                onItemClick(adapterPosition)
            }
        }

    }
}