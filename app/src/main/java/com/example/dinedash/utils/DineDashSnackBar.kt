package com.example.dinedash.utils

import android.content.Context
import android.content.res.Resources
import android.graphics.Color
import android.view.View
import com.example.dinedash.R
import com.google.android.material.snackbar.Snackbar

object DineDashSnackBar {
    private var snackbar: Snackbar? = null

    fun show(view: View, message:String, isError: Boolean){
        snackbar?.dismiss()
       snackbar = Snackbar.make(view, message,Snackbar.LENGTH_LONG)
        val snackBarView = snackbar?.view
        if(isError){
            snackBarView?.setBackgroundColor(Color.RED)
        }else{
            snackBarView?.setBackgroundColor(Color.GREEN)
        }
        snackbar?.show()
    }
}