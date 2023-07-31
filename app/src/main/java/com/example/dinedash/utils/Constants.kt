package com.example.dinedash.utils

import android.app.Activity
import android.content.Intent
import android.provider.MediaStore
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment

object Constants {
    const val USERS = "users"
    const val READ_STORAGE_PERMISSION_CODE = 99
    const val PICK_IMAGE_REQUEST_CODE = 66

    fun showImageChooser(fragment: Activity){
        val galleryIntent = Intent(
            Intent.ACTION_PICK,
            MediaStore.Images.Media.EXTERNAL_CONTENT_URI)

        fragment.startActivityForResult(galleryIntent, PICK_IMAGE_REQUEST_CODE)
    }
}