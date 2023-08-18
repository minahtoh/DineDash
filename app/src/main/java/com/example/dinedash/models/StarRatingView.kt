package com.example.dinedash.models

import android.content.Context
import android.graphics.Color
import android.util.AttributeSet
import android.widget.ImageView
import android.widget.LinearLayout
import com.example.dinedash.R


class StarRatingView(context : Context, attrs:AttributeSet?):LinearLayout(context,attrs) {
    private val stars = arrayOfNulls<ImageView>(5)
    var rating: Int = 0
        set(value) {
            field = value
            updateStars()
        }

    init {
        for (i in stars.indices) {
            val star = ImageView(context)
            star.setImageResource(R.drawable.baseline_star_rate_24)
            stars[i] = star
            star.setOnClickListener {
                rating = i+1
            }
            addView(star)
        }
        updateStars()
    }

    private fun updateStars() {
        for (i in stars.indices) {
            val star = stars[i]
            star?.setColorFilter(if (i < rating) Color.YELLOW else Color.GRAY)
        }
    }

}