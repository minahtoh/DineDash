package com.example.dinedash.activities.main

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.view.isNotEmpty
import androidx.fragment.app.Fragment
import com.example.dinedash.activities.SettingsActivity
import com.example.dinedash.databinding.FragmentProfileBinding
import com.example.dinedash.models.FireStoreClass
import com.example.dinedash.models.User
import com.example.dinedash.utils.Constants.FEMALE
import com.example.dinedash.utils.Constants.GENDER
import com.example.dinedash.utils.Constants.MALE
import com.example.dinedash.utils.Constants.MOBILE
import com.example.dinedash.utils.Constants.PICK_IMAGE_REQUEST_CODE
import com.example.dinedash.utils.Constants.PROFILE_PICTURE
import com.example.dinedash.utils.Constants.READ_STORAGE_PERMISSION_CODE
import com.example.dinedash.utils.DineDashProgressBar
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
    private lateinit var userHashMap: HashMap<String, Any>
    private var imageUri: Uri? = null
    private var imageUrl: String? = null

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
                if (user!!.mobile.isNotEmpty()){
                    phoneNumberEt.setText(user!!.mobile)
                    phoneNumberEt.isEnabled=false
                }
                if (user!!.gender.isNotEmpty()){
                    if (user!!.gender == MALE){
                        genderMale.isChecked = true
                    }else{
                        genderFemale.isChecked = true
                    }
                }
            }
            profilePicture.setOnClickListener {
                if(ContextCompat.checkSelfPermission(requireContext(),
                        Manifest.permission.READ_EXTERNAL_STORAGE)
                == PackageManager.PERMISSION_GRANTED){
                    showImageChooser()
                }else{
                    ActivityCompat.requestPermissions(requireActivity(),
                        arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),
                        READ_STORAGE_PERMISSION_CODE )
                }
            }
            saveButton.setOnClickListener {
               updateDetails()
                if (imageUri !=null){
                    FireStoreClass().uploadImageToCloud(requireActivity(),imageUri,this@ProfileFragment)}
                DineDashProgressBar.show(requireContext())
               FireStoreClass().updateUserProfileData(this@ProfileFragment,userHashMap)
            }


            settingsButton.setOnClickListener {
                val intent = Intent(requireContext(), SettingsActivity::class.java)
                startActivity(intent)
            }
        }
    }

    private fun updateDetails() {
        userHashMap = HashMap()
        binding.apply {
            if (phoneNumber.isNotEmpty()){
                val mobileNumber = phoneNumberEt.text.toString().trim { it <= ' ' }
                userHashMap[MOBILE] = mobileNumber
            }
            if (genderMale.isChecked){
                userHashMap[GENDER] = MALE
            }else {
                userHashMap[GENDER] = FEMALE
            }
            if (imageUrl != null){
                userHashMap[PROFILE_PICTURE] = imageUrl!!
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

            showImageChooser()
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
                        imageUri = data.data!!
                        GlideLoader(requireContext()).loadUserImage(imageUri!!,binding.profilePicture)

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


    fun updateToFirebase(url:String) {
        //TODO: IMPLEMENT
        imageUrl = url
       // Toast.makeText(requireContext(),"updated to fayabasee", Toast.LENGTH_SHORT).show()
    }


    private fun showImageChooser(){
        val galleryIntent = Intent(
            Intent.ACTION_PICK,
            MediaStore.Images.Media.EXTERNAL_CONTENT_URI)

        startActivityForResult(galleryIntent, PICK_IMAGE_REQUEST_CODE)
    }

}