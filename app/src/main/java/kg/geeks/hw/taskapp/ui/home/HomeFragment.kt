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
import kg.geeks.hw.taskapp.ui.home.adapter.TaskAdapter

class HomeFragment : Fragment() {

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
        val data = App.db.taskDao().getAllTasks()
        adapter.addTasks(data)

        binding.apply {
            taskRecyclerview.adapter = adapter
            fab.setOnClickListener {
                findNavController().navigate(R.id.navigation_task)
            }
        }
    }

    private fun onItemClick(id: Int) {
        findNavController().navigate(
            R.id.navigation_task,
            bundleOf(KEY_FOR_OPEN_TASK_FRAGMENT to true, KEY_TASK_ID to id)
        )
    }

    private fun onDeleteClick(id: Int) {
        openAlertDialog(id)
    }

    private fun openAlertDialog(id: Int) {
        val alertDialog = AlertDialog.Builder(requireContext())
        alertDialog.apply {
            setTitle(R.string.menu)
            setMessage(R.string.delete_task)

            setPositiveButton(
                getString(R.string.yes)
            ) { dialog, message ->
                App.db.taskDao().deleteTask(id)
                val data = App.db.taskDao().getAllTasks()
                adapter.addTasks(data)
            }

            setNegativeButton(
                getString(R.string.no)
            ) { dialog, message -> dialog?.dismiss() }
        }
        alertDialog.create().show()
    }

    companion object {
        const val KEY_FOR_OPEN_TASK_FRAGMENT = "keyForUpdateTask"
        const val KEY_TASK_ID = "keyTaskId"
    }
}
