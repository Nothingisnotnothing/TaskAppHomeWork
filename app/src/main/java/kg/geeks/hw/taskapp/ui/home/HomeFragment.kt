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

class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    private val adapter = TaskAdapter(::onLongClick, ::onItemClick)

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
        setData()

        binding.apply {
            taskRecyclerview.adapter = adapter
            fab.setOnClickListener {
                findNavController().navigate(R.id.navigation_task)
            }
        }
    }

    private fun onLongClick(task: Task) {
        openAlertDialog(task)
    }

    private fun onItemClick(task: Task) {
        findNavController().navigate(R.id.navigation_task, bundleOf(KEY_FOR_UPDATE_TASK to true))
    }

    private fun openAlertDialog(task: Task) {
        val alertDialog = AlertDialog.Builder(requireContext())
        alertDialog.apply {
            setTitle(R.string.menu)
            setMessage(R.string.delete_task)

            setPositiveButton(
                getString(R.string.yes)
            ) { dialog, message ->
                App.db.taskDao().delete(task)
                dialog.dismiss()
                setData()
            }
            setNegativeButton(
                getString(R.string.no)
            ) { dialog, message -> dialog?.dismiss() }
        }
        alertDialog.create().show()
    }

    private fun setData() {
        val data = App.db.taskDao().getAll()
        adapter.addTasks(data)
    }

    companion object {
        const val KEY_FOR_UPDATE_TASK = "keyForUpdateTask"
    }
}