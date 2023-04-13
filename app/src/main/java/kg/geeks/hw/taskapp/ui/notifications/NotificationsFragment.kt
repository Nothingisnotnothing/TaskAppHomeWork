package kg.geeks.hw.taskapp.ui.notifications

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kg.geeks.hw.taskapp.databinding.FragmentNotificationsBinding
import kg.geeks.hw.taskapp.model.Car
import kg.geeks.hw.taskapp.ui.notifications.adapter.CarAdapter

class NotificationsFragment : Fragment() {

    private lateinit var binding: FragmentNotificationsBinding
    private lateinit var db: FirebaseFirestore
    private val adapter = CarAdapter()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentNotificationsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        db = FirebaseFirestore.getInstance()
        binding.recyclerView.adapter = adapter

        db.collection(FirebaseAuth.getInstance().currentUser?.uid.toString()).get()
            .addOnSuccessListener {
                val list = it.toObjects(Car::class.java)
                adapter.addCars(list)
            }
            .addOnFailureListener {
            }
    }
}
