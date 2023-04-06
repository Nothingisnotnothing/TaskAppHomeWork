package kg.geeks.hw.taskapp.ui.home.adapter

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import kg.geeks.hw.taskapp.databinding.ItemTaskBinding
import kg.geeks.hw.taskapp.model.Task

class TaskAdapter(
    private val onDeleteClick: (id: Int) -> Unit,
    private val onItemClick: (id: Int) -> Unit
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

    fun getTask(position: Int): Int? {
        return taskList[position].id
    }

    inner class TaskViewHolder(private val binding: ItemTaskBinding) :
        ViewHolder(binding.root) {

        fun bind(task: Task) {
            binding.apply {
                tvTitle.text = task.title
                tvDesc.text = task.desc
                if (adapterPosition % 2 == 0) {
                    itemView.setBackgroundColor(Color.BLACK)
                    tvTitle.setTextColor(Color.WHITE)
                    tvDesc.setTextColor(Color.WHITE)
                } else {
                    itemView.setBackgroundColor(Color.WHITE)
                    tvTitle.setTextColor(Color.BLACK)
                    tvDesc.setTextColor(Color.BLACK)
                }
            }
            itemView.setOnLongClickListener {
                onDeleteClick(getTask(adapterPosition)!!)
                false
            }
            itemView.setOnClickListener {
                onItemClick(getTask(adapterPosition)!!)
            }
        }
    }
}