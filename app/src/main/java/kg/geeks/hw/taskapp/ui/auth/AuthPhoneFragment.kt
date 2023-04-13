package kg.geeks.hw.taskapp.ui.auth

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.google.firebase.FirebaseException
import com.google.firebase.auth.*
import kg.geeks.hw.taskapp.R
import kg.geeks.hw.taskapp.databinding.FragmentAuthPhoneBinding
import java.util.concurrent.TimeUnit

class AuthPhoneFragment : Fragment() {
    private lateinit var binding: FragmentAuthPhoneBinding
    private lateinit var auth: FirebaseAuth
    private var storedVerificationId = ""


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAuthPhoneBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        auth = FirebaseAuth.getInstance()
        binding.btnPhone.setOnClickListener {
            checkPhone()
        }
        binding.btnVerification.setOnClickListener {
            checkCode()
        }
    }

    private fun checkPhone() {
        binding.apply {
            if (etPhone.text?.length == 13) {
                auth()
            } else {
                etPhone.error = "Example: +996999888777"
            }
        }
    }

    private fun auth() {
        auth.useAppLanguage()
        val options = PhoneAuthOptions.newBuilder(auth)
            .setPhoneNumber(binding.etPhone.text.toString())
            .setTimeout(60L, TimeUnit.SECONDS)
            .setActivity(requireActivity())
            .setCallbacks(callbacks)
            .build()
        PhoneAuthProvider.verifyPhoneNumber(options)
    }

    private fun checkCode() {
        val credential = PhoneAuthProvider.getCredential(
            storedVerificationId,
            binding.etVerificationCode.text.toString()
        )
        signInWithPhoneAuthCredential(credential)
    }

    private val callbacks = object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
        override fun onVerificationCompleted(credential: PhoneAuthCredential) {
            signInWithPhoneAuthCredential(credential)
        }

        override fun onVerificationFailed(e: FirebaseException) {
        }

        override fun onCodeSent(
            verificationId: String,
            token: PhoneAuthProvider.ForceResendingToken
        ) {
            storedVerificationId = verificationId
        }
    }

    private fun signInWithPhoneAuthCredential(credential: PhoneAuthCredential) {
        auth.signInWithCredential(credential)
            .addOnCompleteListener(requireActivity()) { task ->
                if (task.isSuccessful) {
                    findNavController().navigate(R.id.navigation_home)
                } else {
                    if (task.exception is FirebaseAuthInvalidCredentialsException) {
                    }
                }
            }
    }
}