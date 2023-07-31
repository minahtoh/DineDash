package com.example.dinedash.activities.login

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.dinedash.databinding.FragmentLoginBinding
import com.example.dinedash.models.FireStoreClass
import com.example.dinedash.utils.DineDashProgressBar
import com.example.dinedash.utils.DineDashSnackBar
import com.google.firebase.auth.FirebaseAuth


/**
 * A simple [Fragment] subclass.
 * Use the [LoginFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class LoginFragment : Fragment() {
    private lateinit var binding: FragmentLoginBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentLoginBinding.inflate(inflater, container, false)

        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.loginButton.setOnClickListener {
            isDetailsValid()
            if (isDetailsValid()){
                DineDashProgressBar.show(requireContext())
                loginUser()
            }else{
                DineDashSnackBar.show(requireView(),"Invalid details, please check!", true)
            }
        }

        binding.registerText.setOnClickListener {
            val action = LoginFragmentDirections.actionLoginFragmentToRegisterFragment()
            findNavController().navigate(action)

        }

        binding.forgotPasswordTV.setOnClickListener {
            val action = LoginFragmentDirections.actionLoginFragmentToResetPasswordFragment()
            findNavController().navigate(action)
        }

    }

    private fun loginUser() {
        val email = binding.emailIdEt.text.toString().trim { it <= ' ' }
        val password = binding.passwordEt.text.toString().trim { it <= ' ' }
        FirebaseAuth.getInstance().signInWithEmailAndPassword(email,password)
            .addOnCompleteListener {
                DineDashProgressBar.hide()
                if (it.isSuccessful){
                    FireStoreClass().getUserDetails(requireActivity())
                }else{
                    DineDashSnackBar.show(requireView(), it.exception.toString(), true)
                }
            }
    }

    private fun isDetailsValid() : Boolean {
        return !(binding.emailIdEt.text.isNullOrEmpty()
                ||binding.passwordEt.text.isNullOrEmpty())

    }

}