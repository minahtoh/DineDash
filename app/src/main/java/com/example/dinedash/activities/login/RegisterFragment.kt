package com.example.dinedash.activities.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.dinedash.databinding.FragmentRegisterBinding
import com.example.dinedash.models.FireStoreClass
import com.example.dinedash.models.User
import com.example.dinedash.utils.DineDashProgressBar
import com.example.dinedash.utils.DineDashSnackBar
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser


/**
 * A simple [Fragment] subclass.
 * Use the [RegisterFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class RegisterFragment : Fragment() {
    private lateinit var binding: FragmentRegisterBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentRegisterBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //Handle Register Function
        binding.apply {
            backbutton.setOnClickListener {
                findNavController().navigateUp()
            }
            agreementCheckbox.setOnCheckedChangeListener { _, isChecked ->
                registerButton.isEnabled = isChecked  }

            registerButton.setOnClickListener {
                if (firstNameEt.text.isNullOrEmpty()){
                    DineDashSnackBar.show(view,"Please enter your first name", true)
                }
                else if(lastNameEt.text.isNullOrEmpty()){
                    DineDashSnackBar.show(view, "Please enter your last name", true)
                }
                else if(emailIdEt.text.isNullOrEmpty()){
                    DineDashSnackBar.show(view, "Please enter your Email address", true)
                }
                else if(passwordEt.text.isNullOrEmpty()){
                    DineDashSnackBar.show(view, "Please enter a password", true)
                }
                else if(confirmPasswordEt.text.isNullOrEmpty()||
                    confirmPasswordEt.text.toString() != passwordEt.text.toString()){
                    DineDashSnackBar.show(view, "Please confirm your password", true)
                }else{
                    DineDashProgressBar.showRegistration(requireContext())
                    registerUser()
                }
            }

            loginText.setOnClickListener {
                val action = RegisterFragmentDirections.actionRegisterFragmentToLoginFragment()
                findNavController().navigate(action)
            }
        }
    }

    private fun registerUser() {
        val firstName = binding.firstNameEt.text.toString().trim { it <= ' ' }
        val lastName = binding.lastNameEt.text.toString().trim { it <= ' ' }
        val email = binding.emailIdEt.text.toString().trim { it <= ' ' }
        val password = binding.passwordEt.text.toString().trim { it <= ' ' }

        FirebaseAuth.getInstance().createUserWithEmailAndPassword(email,password)
            .addOnCompleteListener { task: Task<AuthResult> ->
                try {
                    if (task.isSuccessful) {
                        // User registration successful
                        val user: FirebaseUser? = FirebaseAuth.getInstance().currentUser
                        val newUser = User(
                            uid = user!!.uid,
                            firstName = firstName,
                            lastName = lastName,
                            email = email)
                        FireStoreClass().registerUser(this, newUser)}
                }catch (e:Exception){
                        DineDashProgressBar.hide()
                        // Handle registration errors
                        val exception = task.exception
                        DineDashSnackBar.show(requireView(),exception.toString(),true)
                        // Handle specific error cases or display an error message to the user.
                } finally{
                        DineDashSnackBar.show(requireView(),"Account Created Successfully!", false)
                        findNavController().navigate(RegisterFragmentDirections.actionRegisterFragmentToLoginFragment())

                    }

                }
    }
}

