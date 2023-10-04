package com.example.dinedash.utils

import android.app.Dialog
import android.content.Context
import com.example.dinedash.R

object DineDashProgressBar {
    private var progressBar: Dialog? = null

    fun show(context: Context) {
        progressBar = Dialog(context)
        progressBar?.apply {
            setContentView(R.layout.progress_dialog)
            setCancelable(false)
            setCanceledOnTouchOutside(false)
            show()
        }
    }

    fun showWarning(context: Context){
        progressBar = Dialog(context)
        progressBar?.apply {
            setContentView(R.layout.progress_dialog)
            setTitle("Warning!!")
            setCancelable(false)
            show()
        }
    }
    fun hide(){
        progressBar?.dismiss()
        progressBar = null
    }
}