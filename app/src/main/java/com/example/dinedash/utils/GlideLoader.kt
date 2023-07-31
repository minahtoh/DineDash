package com.example.dinedash.utils

import android.content.Context
import android.net.Uri
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.example.dinedash.R

class GlideLoader( val context: Context) {
    fun loadUserImage( imageUri: Uri, imageView: ImageView){
      try {
          Glide
            .with(context).
            load(imageUri)
            .centerCrop()
            .placeholder(R.drawable.baseline_person_picture)
            .into(imageView)
    }catch (e:Exception){
        e.printStackTrace()
    }
    }
}