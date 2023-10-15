package com.example.dinedash.utils

import android.app.Dialog
import android.content.Context
import android.view.LayoutInflater
import com.example.dinedash.databinding.ProgressDialogBinding

object DineDashProgressBar {
    private var progressBar: Dialog? = null
    lateinit var binding : ProgressDialogBinding

    fun show(context: Context) {
        binding = ProgressDialogBinding.inflate(LayoutInflater.from(context))
        progressBar = Dialog(context)
        progressBar?.apply {
            setContentView(binding.root)
            setCancelable(false)
            setCanceledOnTouchOutside(false)
            show()
        }
    }

    fun showLogin(context: Context){
        binding = ProgressDialogBinding.inflate(LayoutInflater.from(context))
        binding.loadingText.text = "Logging in..."
        progressBar = Dialog(context)
        progressBar?.apply {
            setContentView(binding.root)
            setCancelable(false)
            setCanceledOnTouchOutside(false)
            show()
        }
    }


    fun showRegistration(context: Context){
        binding = ProgressDialogBinding.inflate(LayoutInflater.from(context))
        binding.loadingText.text = "Registering User..."
        progressBar = Dialog(context)
        progressBar?.apply {
            setContentView(binding.root)
            setCancelable(false)
            setCanceledOnTouchOutside(false)
            show()
        }
    }

    fun hide(){
        progressBar?.dismiss()
        progressBar = null
    }
}
