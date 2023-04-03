package kg.geeks.hw.taskapp.ui.home

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import kg.geeks.hw.taskapp.App
import kg.geeks.hw.taskapp.R
import kg.geeks.hw.taskapp.databinding.FragmentHomeBinding
import kg.geeks.hw.taskapp.model.Task
import kg.geeks.hw.taskapp.ui.home.adapter.TaskAdapter
import kg.geeks.hw.taskapp.ui.task.TaskFragment.Companion.KEY_FOR_OPEN_HOME_FRAGMENT
import kg.geeks.hw.taskapp.ui.task.TaskFragment.Companion.KEY_FOR_UPDATED_TASK

class HomeFragment : Fragment() {

    companion object {
        const val KEY_FOR_UPDATE_TASK = "keyForUpdateTask"
        const val KEY_FOR_OPEN_PROFILE_FRAGMENT = "keyForOpenProfileFragment"
    }

    private lateinit var binding: FragmentHomeBinding
    private val adapter = TaskAdapter(::onDeleteClick, ::onItemClick)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        checkTaskUpdate()
        val data = App.db.taskDao().getAll()
        adapter.addTasks(data)

        binding.apply {
            taskRecyclerview.adapter = adapter
            fab.setOnClickListener {
                findNavController().navigate(R.id.navigation_task)
            }
        }
    }

    private fun checkTaskUpdate() {
        if (arguments?.getBoolean(KEY_FOR_OPEN_HOME_FRAGMENT) == true) {
            val updatedTask = arguments?.getSerializable(KEY_FOR_UPDATED_TASK) as Task
            App.db.taskDao().update(updatedTask)
        }
    }

    private fun onDeleteClick(position: Int) {
        openAlertDialog(position)
    }

    private fun onItemClick(position: Int) {
        val task = adapter.getTask(position)
        findNavController().navigate(
            R.id.navigation_task,
            bundleOf(KEY_FOR_UPDATE_TASK to task, KEY_FOR_OPEN_PROFILE_FRAGMENT to true)
        )
    }

    private fun openAlertDialog(position: Int) {
        val builderAlertDialog = AlertDialog.Builder(requireContext())
        builderAlertDialog.apply {
            setTitle(R.string.menu)
            setMessage(R.string.delete_task)

            setPositiveButton(
                getString(R.string.yes)
            ) { dialog, message ->
                App.db.taskDao().delete(adapter.getTask(position))
                val data = App.db.taskDao().getAll()
                adapter.addTasks(data)
            }

            setNegativeButton(
                getString(R.string.no)
            ) { dialog, message -> dialog?.dismiss() }
        }
        val alertDialog1 = builderAlertDialog.create()
        alertDialog1.show()
    }
}