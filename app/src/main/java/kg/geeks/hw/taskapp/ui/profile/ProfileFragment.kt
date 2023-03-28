package kg.geeks.hw.taskapp.ui.profile

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.net.toUri
import kg.geeks.hw.taskapp.data.local.Pref
import kg.geeks.hw.taskapp.databinding.FragmentProfileBinding
import kg.geeks.hw.taskapp.utils.loadImageGlide

class ProfileFragment : Fragment() {

    companion object {
        const val KEY_FOR_LOAD_IMAGE = 998
    }

    private lateinit var binding: FragmentProfileBinding
    private lateinit var pref: Pref

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        pref = Pref(requireContext())
        openGallery()
        binding.etProfileUsername.setText(pref.loadEtText())
        //работает с Glide а не с setImageUri (по какой причине непонятно)
        binding.imgProfile.loadImageGlide(pref.loadImagePath())
    }

    //работает также как и startActivityForResult
    private fun openGallery() {
        binding.imgProfile.setOnClickListener {
            val intent = Intent(Intent.ACTION_OPEN_DOCUMENT)
            intent.type = "image/*"
            launcher.launch(intent)
        }
    }

    private val launcher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result: ActivityResult ->
            if (result.resultCode == Activity.RESULT_OK && result.data != null) {
                val image = result.data?.data
                binding.imgProfile.setImageURI(image.toString().toUri())
                pref.saveImagePath(image.toString())
            }
        }

    override fun onStop() {
        super.onStop()
        pref.saveEtText(binding.etProfileUsername.text.toString())
    }

}