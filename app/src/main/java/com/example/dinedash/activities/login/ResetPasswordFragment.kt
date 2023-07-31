package com.example.dinedash.activities.login

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.dinedash.databinding.FragmentResetPasswordBinding
import com.example.dinedash.utils.DineDashProgressBar
import com.example.dinedash.utils.DineDashSnackBar
import com.google.firebase.auth.FirebaseAuth


/**
 * A simple [Fragment] subclass.
 * Use the [ResetPasswordFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ResetPasswordFragment : Fragment() {
        private lateinit var binding: FragmentResetPasswordBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding =  FragmentResetPasswordBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.backbutton.setOnClickListener {
            findNavController().navigateUp()
        }
        binding.submitButton.setOnClickListener {
            resetUserPassword()
        }
    }



    private fun resetUserPassword() {
        val email = binding.emailIdEt.text.toString().trim { it <= ' ' }

        DineDashProgressBar.show(requireContext())

        FirebaseAuth.getInstance().sendPasswordResetEmail(email).addOnCompleteListener {
            DineDashProgressBar.hide()
            if (it.isSuccessful){
                DineDashSnackBar.show(requireView(), "Password reset successful!", false)
            }else {
                DineDashSnackBar.show(requireView(), it.exception.toString(), true)
                val action = RegisterFragmentDirections.actionRegisterFragmentToLoginFragment()
                findNavController().navigate(action)
            }
        }
    }

}