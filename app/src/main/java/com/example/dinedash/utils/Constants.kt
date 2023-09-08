package com.example.dinedash.utils

import android.app.Activity
import android.net.Uri
import android.webkit.MimeTypeMap

object Constants {
    const val USERS = "users"
    const val READ_STORAGE_PERMISSION_CODE = 99
    const val PICK_IMAGE_REQUEST_CODE = 66
    const val MALE = "Male"
    const val FEMALE = "Female"
    const val GENDER = "gender"
    const val MOBILE = "mobile"
    const val PROFILE_PICTURE = "profilePicture"
    const val DELIVERY_FEE = 4500

    fun getFileExtension(activity: Activity, uri: Uri?): String?{
        return MimeTypeMap.getSingleton()
            .getExtensionFromMimeType(activity.contentResolver.getType(uri!!))
    }

}