package com.example.dinedash.utils

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.provider.MediaStore
import android.webkit.MimeTypeMap
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat.startActivityForResult
import androidx.fragment.app.Fragment
import java.net.URI

object Constants {
    const val USERS = "users"
    const val READ_STORAGE_PERMISSION_CODE = 99
    const val PICK_IMAGE_REQUEST_CODE = 66
    const val MALE = "Male"
    const val FEMALE = "Female"
    const val GENDER = "gender"
    const val MOBILE = "mobile"
    const val PROFILE_PICTURE = "profilePicture"

    fun getFileExtension(activity: Activity, uri: Uri?): String?{
        return MimeTypeMap.getSingleton()
            .getExtensionFromMimeType(activity.contentResolver.getType(uri!!))
    }

}