package kg.geeks.hw.taskapp.ui.task

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.navigation.fragment.findNavController
import kg.geeks.hw.taskapp.App
import kg.geeks.hw.taskapp.R
import kg.geeks.hw.taskapp.databinding.FragmentTaskBinding
import kg.geeks.hw.taskapp.model.Task
import kg.geeks.hw.taskapp.ui.home.HomeFragment.Companion.KEY_FOR_OPEN_PROFILE_FRAGMENT
import kg.geeks.hw.taskapp.ui.home.HomeFragment.Companion.KEY_FOR_UPDATE_TASK

class TaskFragment : Fragment() {

    companion object {
        const val KEY_FOR_UPDATED_TASK = "keyForUpdatedTask"
        const val KEY_FOR_OPEN_HOME_FRAGMENT = "keyForOpenHomeFragment"
    }

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
        if (arguments?.getBoolean(KEY_FOR_OPEN_PROFILE_FRAGMENT) == true) {
            val taskData = arguments?.getSerializable(KEY_FOR_UPDATE_TASK) as Task
            binding.apply {
                etTitle.setText(taskData.title)
                etDesc.setText(taskData.desc)
                btnSaveOrEdit.setText(R.string.edit)
                btnSaveOrEdit.setOnClickListener {
                    if (binding.etTitle.text.isNotEmpty()) {
                        taskData.title = binding.etTitle.text.toString()
                        taskData.desc = binding.etDesc.text.toString()
                        findNavController().navigate(
                            R.id.navigation_home,
                            bundleOf(
                                KEY_FOR_UPDATED_TASK to taskData,
                                KEY_FOR_OPEN_HOME_FRAGMENT to true
                            )
                        )
                    } else etTitleError()
                }
            }
        } else {
            binding.btnSaveOrEdit.setOnClickListener {
                if (binding.etTitle.text.isNotEmpty()) {
                    save()
                } else etTitleError()
            }
        }
    }

    private fun etTitleError() {
        binding.etTitle.error = getString(R.string.this_is_must_be_written)
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