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
import kg.geeks.hw.taskapp.ui.home.HomeFragment.Companion.KEY_FOR_UPDATE_TASK

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
        initListeners()
    }

    private fun initListeners() {
        if (arguments?.getBoolean(KEY_FOR_UPDATE_TASK) == true) {
            binding.apply {
                //TODO сюда нужно передать значение(объект), а не позицию и здесь вызвать update в БД
//                etTitle.setText(task.title)
//                etDesc.setText(task.desc)
                btnSaveOrEdit.setText(R.string.edit)
                binding.btnSaveOrEdit.setOnClickListener {
                    if (binding.etTitle.text.isNotEmpty()) {
                        findNavController().navigateUp()
                    } else etTitleError()
                }
            }
        } else {
            binding.apply {
                binding.btnSaveOrEdit.setOnClickListener {
                    if (binding.etTitle.text.isNotEmpty()) {
                        save()
                    } else etTitleError()
                }
            }
        }
    }

    private fun etTitleError() {
        binding.etTitle.error = getString(R.string.this_is_must_be_written)
    }

    private fun update() {
        val data  = Task( id = id, title = binding.etTitle.text.toString(), desc = binding.etDesc.text.toString())
        App.db.taskDao().update(data)
        findNavController().navigateUp()
    }

    private fun save() {
        val data = Task(
            title = binding.etTitle.text.toString(),
            desc = binding.etDesc.text.toString()
        )
        App.db.taskDao().insert(data)
        findNavController().navigateUp()
    }
}