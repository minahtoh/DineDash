package com.example.dinedash.utils

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.Context
import android.graphics.Typeface
import android.util.AttributeSet
import android.view.MotionEvent
import androidx.appcompat.widget.AppCompatButton


class DineDashButton(
    context: Context, attrs: AttributeSet) : AppCompatButton(context, attrs) {
    init {
        applyFont()
    }


    private fun applyFont() {
        val typeface: Typeface =
            Typeface.createFromAsset(context.assets, "MontserratAlternates-Bold.ttf")
        setTypeface(typeface)
    }

}