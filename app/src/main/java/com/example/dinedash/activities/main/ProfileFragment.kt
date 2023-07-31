package com.example.dinedash.activities.main

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.dinedash.databinding.FragmentProfileBinding
import com.example.dinedash.models.User
import com.example.dinedash.utils.Constants.PICK_IMAGE_REQUEST_CODE
import com.example.dinedash.utils.Constants.READ_STORAGE_PERMISSION_CODE
import com.example.dinedash.utils.Constants.showImageChooser
import com.example.dinedash.utils.DineDashSnackBar
import com.example.dinedash.utils.GlideLoader

/**
 * A simple [Fragment] subclass.
 * Use the [ProfileFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ProfileFragment : Fragment() {
    private lateinit var binding: FragmentProfileBinding
    private var user : User? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            user = it.getParcelable("user")
        }
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentProfileBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            if (user != null){
                firstNameEt.isEnabled = false
                firstNameEt.setText(user!!.firstName)
                lastNameEt.isEnabled = false
                lastNameEt.setText(user!!.lastName)
                emailIdEt.isEnabled = false
                emailIdEt.setText(user!!.email)
            }
            profilePicture.setOnClickListener {
                if(ContextCompat.checkSelfPermission(requireContext(),
                        Manifest.permission.READ_EXTERNAL_STORAGE)
                == PackageManager.PERMISSION_GRANTED){
//                    DineDashSnackBar.show(requireView(),"You already have read permission", false)
                    showImageChooser(requireActivity())
                }else{
                    ActivityCompat.requestPermissions(requireActivity(),
                        arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),
                        READ_STORAGE_PERMISSION_CODE )
                }
            }
        }
    }

    @Deprecated("Deprecated in Java")
    @Suppress("DEPRECATION")
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED){

            showImageChooser(requireActivity())
        }else{
            DineDashSnackBar.show(requireView(),"You need to allow permission to change profile picture!", true)
        }
    }


    @Deprecated("Deprecated in Java")
    @Suppress("DEPRECATION")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode == Activity.RESULT_OK){
            if(requestCode == PICK_IMAGE_REQUEST_CODE){
                if (data != null){
                    try {
                        val imageUri = data.data!!
                        GlideLoader(requireContext()).loadUserImage(imageUri,binding.profilePicture)
                        updateToFirebase()
                        Toast.makeText(requireContext(), "Okay Code", Toast.LENGTH_SHORT).show()
                    }catch (e: Exception){
                        e.printStackTrace()
                        DineDashSnackBar.show(requireView(), "Error selecting image", true)
                    }
                } else{
                    DineDashSnackBar.show(requireView(), "Image cannot be loaded", true)
                }
            }
        }else{
            Toast.makeText(requireContext(),"unable to select image", Toast.LENGTH_SHORT).show()
        }
    }


    private fun updateToFirebase() {
        TODO("Not yet implemented")
    }

}