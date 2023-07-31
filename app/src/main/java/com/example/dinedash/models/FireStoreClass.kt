package com.example.dinedash.models

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.dinedash.R
import com.example.dinedash.activities.main.MainActivity
import com.example.dinedash.utils.Constants.USERS
import com.example.dinedash.utils.DineDashProgressBar
import com.example.dinedash.utils.DineDashSnackBar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions


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

}