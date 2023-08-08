package com.example.dinedash.models

import android.app.Activity
import android.content.ContentValues.TAG
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.dinedash.R
import com.example.dinedash.activities.main.MainActivity
import com.example.dinedash.activities.main.ProfileFragment
import com.example.dinedash.activities.main.ProfileFragmentDirections
import com.example.dinedash.utils.Constants.USERS
import com.example.dinedash.utils.Constants.getFileExtension
import com.example.dinedash.utils.DineDashProgressBar
import com.example.dinedash.utils.DineDashSnackBar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference


class FireStoreClass {
    private val mFireStore = FirebaseFirestore.getInstance()

    fun registerUser(activity: Fragment, user: User){

        mFireStore.collection("users")
            .document(user.uid)
            .set(user, SetOptions.merge())
            .addOnSuccessListener {
                DineDashProgressBar.hide()
            //    DineDashSnackBar.show(activity.requireView(),"user succesfully registered",false)
            }.addOnFailureListener{
                DineDashProgressBar.hide()
            }
    }

    fun getUserId(): String {
        val user = FirebaseAuth.getInstance().currentUser
        return user?.uid ?: ""
    }

    fun getUserDetails(activity: Activity){
        mFireStore.collection(USERS)
            .document(getUserId())
            .get()
            .addOnSuccessListener {
                Document ->
                val intent = Intent(activity.baseContext, MainActivity::class.java)
                val user = Document.toObject(User::class.java)
                val bundle = Bundle()
                bundle.putParcelable("user",user)
                intent.putExtras(bundle)
                activity.startActivity(intent)
                activity.finish()
            }
            .addOnFailureListener {
                Toast.makeText(activity.baseContext,"Error ${it.message}", Toast.LENGTH_SHORT)
                    .show()
            }
    }

    fun updateUserProfileData( fragment: Fragment, userHashMap: HashMap<String,Any>){
        mFireStore.collection(USERS)
            .document(getUserId())
            .update(userHashMap)
            .addOnSuccessListener {
                DineDashProgressBar.hide()
                when(fragment){
                    is ProfileFragment-> {
                        val action = ProfileFragmentDirections.actionProfileFragmentToHomeFragment()
                        fragment.findNavController().navigate(action)
                    }
                }
                Toast.makeText(fragment.requireContext(),"Details updated successfully!", Toast.LENGTH_SHORT).show()

            }
            .addOnFailureListener {
                DineDashProgressBar.hide()
                Toast.makeText(fragment.requireContext(),"Error updating details", Toast.LENGTH_SHORT).show()
                Log.e(TAG, "updateUserProfileData: ${it.message}")
            }
    }

    fun uploadImageToCloud(activity: Activity, imageFileUri : Uri?){
        val sRef: StorageReference =FirebaseStorage.getInstance().reference
            .child("User" + System.currentTimeMillis() + "." + getFileExtension(activity,imageFileUri))

        sRef.putFile(imageFileUri!!)
            .addOnSuccessListener { taskSnapshot->
                Log.e(TAG, "uploadImageToCloud:${taskSnapshot.metadata!!.reference!!.downloadUrl} ", )
                taskSnapshot.metadata!!.reference!!.downloadUrl.addOnSuccessListener {

                    Toast.makeText(activity.baseContext,"Image upload successful", Toast.LENGTH_SHORT)
                        .show()
                }

            }
            .addOnFailureListener {
                    Toast.makeText(activity.baseContext,"Image upload failed", Toast.LENGTH_SHORT)
                        .show()
            }
    }
}