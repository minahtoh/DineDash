package com.example.dinedash.models

import android.net.Uri
import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class User(
    val uid : String = "",
    val firstName: String = "",
    val lastName: String = "",
    val email: String = "",
    val profilePicture: String = "",
    val gender: String = "",
    val mobile: String = ""
    ) : Parcelable
