package kg.geeks.hw.taskapp.ui.profile

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity.RESULT_OK
import androidx.core.net.toUri
import kg.geeks.hw.taskapp.data.local.Pref
import kg.geeks.hw.taskapp.databinding.FragmentProfileBinding

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
        binding.imgProfile.setImageURI(pref.loadImagePath()?.toUri())
    }

    private fun openGallery() {
        binding.imgProfile.setOnClickListener {
            val intent = Intent(Intent.ACTION_OPEN_DOCUMENT)
            intent.type = "image/*"
            startActivityForResult(intent, KEY_FOR_LOAD_IMAGE)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == KEY_FOR_LOAD_IMAGE && resultCode == RESULT_OK && data != null) {
            val image = data.data
            binding.imgProfile.setImageURI(data.data)
            pref.saveImagePath(image.toString())
        }
    }

    override fun onStop() {
        super.onStop()
        pref.saveEtText(binding.etProfileUsername.text.toString())
    }

}