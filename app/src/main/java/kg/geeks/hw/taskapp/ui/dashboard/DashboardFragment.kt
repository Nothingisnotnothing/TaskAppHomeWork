package kg.geeks.hw.taskapp.ui.dashboard

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kg.geeks.hw.taskapp.data.local.Pref
import kg.geeks.hw.taskapp.databinding.FragmentDashboardBinding
import kg.geeks.hw.taskapp.model.Car
import kg.geeks.hw.taskapp.utils.showToast

class DashboardFragment : Fragment() {

    private lateinit var binding : FragmentDashboardBinding
    private lateinit var pref: Pref
    private lateinit var db: FirebaseFirestore

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDashboardBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        pref = Pref(requireContext())
        db = FirebaseFirestore.getInstance()

        binding.btnSave.setOnClickListener {
            saveCar()
        }
    }

    private fun saveCar() {
        val name = binding.etTitle.text.toString()
        val model = binding.etDesc.text.toString()
        val car = Car(name, model)

        //добавление item в БД
        db.collection(FirebaseAuth.getInstance().currentUser?.uid.toString()).document().set(car)
            .addOnSuccessListener {
                showToast("Данные сохранены")
                binding.etTitle.text?.clear()
                binding.etDesc.text?.clear()
            }
            .addOnFailureListener {
            }
    }
}