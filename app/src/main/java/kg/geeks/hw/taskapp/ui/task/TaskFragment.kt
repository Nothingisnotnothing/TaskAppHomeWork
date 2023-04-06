package kg.geeks.hw.taskapp.ui.task

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import kg.geeks.hw.taskapp.App
import kg.geeks.hw.taskapp.R
import kg.geeks.hw.taskapp.databinding.FragmentTaskBinding
import kg.geeks.hw.taskapp.model.Task
import kg.geeks.hw.taskapp.ui.home.HomeFragment.Companion.KEY_FOR_OPEN_TASK_FRAGMENT
import kg.geeks.hw.taskapp.ui.home.HomeFragment.Companion.KEY_TASK_ID

class TaskFragment : Fragment() {

    private lateinit var binding: FragmentTaskBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentTaskBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        checkData()
    }

    private fun checkData() {
        if (arguments?.getBoolean(KEY_FOR_OPEN_TASK_FRAGMENT) == true){
            binding.apply {
                binding.btnSaveOrEdit.setText(R.string.edit)
                val task = arguments?.getInt(KEY_TASK_ID)?.let { App.db.taskDao().getTask(it) }
                etTitle.setText(task?.title.toString())
                etDesc.setText(task?.desc.toString())
                binding.btnSaveOrEdit.setOnClickListener {
                    if (binding.etTitle.text.isNotEmpty()) {
                        task?.let { task -> updateTask(task) }
                    } else etTitleError()
                }
            }
        } else {
            binding.btnSaveOrEdit.setOnClickListener {
                if (binding.etTitle.text.isNotEmpty()) {
                    saveTask()
                } else etTitleError()
            }
        }
    }

    private fun updateTask(task: Task) {
        if (binding.etTitle.text.isNotEmpty()) {
            task.title = binding.etTitle.text.toString()
            task.desc = binding.etDesc.text.toString()
        } else etTitleError()
        App.db.taskDao().update(task)
        findNavController().navigateUp()
    }

    private fun saveTask() {
        val data = Task(
            title = binding.etTitle.text.toString(),
            desc = binding.etDesc.text.toString()
        )
        App.db.taskDao().insert(data)
        findNavController().navigateUp()
    }

    private fun etTitleError() {
        binding.etTitle.error = getString(R.string.this_is_must_be_written)
    }
}