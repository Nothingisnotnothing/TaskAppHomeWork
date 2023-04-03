package kg.geeks.hw.taskapp.ui.profile

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import kg.geeks.hw.taskapp.data.local.Pref
import kg.geeks.hw.taskapp.databinding.FragmentProfileBinding
import kg.geeks.hw.taskapp.utils.loadImageGlide

class ProfileFragment : Fragment() {

    private lateinit var binding: FragmentProfileBinding
    private lateinit var pref: Pref
    private lateinit var launcherSetImageView : ActivityResultLauncher<Intent>

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
        binding.apply {
            etProfileUsername.setText(pref.loadEtText())
            imgProfile.loadImageGlide(pref.loadImagePath())
            Log.d("kamino", pref.loadImagePath().toString())
        }
    }

    private fun openGallery() {
        launcherSetImageView = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result: ActivityResult ->
            if (result.resultCode == Activity.RESULT_OK && result.data != null) {
                val photoUri = result.data?.data
                binding.imgProfile.setImageURI(photoUri)
                pref.saveImagePath(photoUri.toString())
            }
        }

        binding.imgProfile.setOnClickListener {
            val intent = Intent(Intent.ACTION_OPEN_DOCUMENT)
            intent.type = "image/*"
            launcherSetImageView.launch(intent)
        }
    }

    override fun onStop() {
        super.onStop()
        pref.saveEtText(binding.etProfileUsername.text.toString())
    }

}