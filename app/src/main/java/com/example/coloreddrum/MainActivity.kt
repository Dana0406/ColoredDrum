package com.example.coloreddrum

import android.annotation.SuppressLint
import android.content.res.Resources
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.animation.Animation
import android.view.animation.DecelerateInterpolator
import android.view.animation.RotateAnimation
import android.widget.Button
import android.widget.ImageView
import android.widget.SeekBar
import com.example.coloreddrum.customViews.ColoredDrumView
import com.example.coloreddrum.customViews.CustomTextView
import com.squareup.picasso.Picasso
import java.util.*

class MainActivity : AppCompatActivity() {
    private lateinit var buttonStart: Button
    private lateinit var buttonReset: Button
    private lateinit var coloredDrum: ColoredDrumView
    private lateinit var customText: CustomTextView
    private lateinit var image: ImageView
    private lateinit var sizeSeekBar: SeekBar

    private var oldDegree = 0F
    private var degree = 0F

    private val random = Random()
    private val factor = 25.7143F

    private val colors = arrayListOf<String>(
        "Желтый",
        "Оранжевый",
        "Красный",
        "Фиолетовый",
        "Синий",
        "Голубой",
        "Зеленый"
    )

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        buttonStart = findViewById(R.id.startButton)
        buttonReset = findViewById(R.id.resetButton)
        coloredDrum = findViewById(R.id.colored_drum)
        customText = findViewById(R.id.customText)
        image = findViewById(R.id.image)
        sizeSeekBar = findViewById(R.id.sizeSeekBar)

        sizeSeekBar.setOnSeekBarChangeListener(object: SeekBar.OnSeekBarChangeListener{
            override fun onProgressChanged(p0: SeekBar?, progress: Int, p2: Boolean) {
                val newRadius = calculateNewRadius(progress)
                coloredDrum.setRadius(newRadius)
            }

            override fun onStartTrackingTouch(p0: SeekBar?) {

            }

            override fun onStopTrackingTouch(p0: SeekBar?) {

            }

        })
    }

    fun onClickStart(view: View) {
        oldDegree = degree % 360
        degree = (random.nextInt(3600) + 720).toFloat()

        var rotate = RotateAnimation(
            oldDegree, degree, RotateAnimation.RELATIVE_TO_SELF, 0.5F,
            RotateAnimation.RELATIVE_TO_SELF, 0.5F
        )

        rotate.duration = (random.nextInt(4000) + 2000).toLong()
        rotate.fillAfter = true
        rotate.interpolator = DecelerateInterpolator()

        rotate.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationRepeat(animation: Animation?) {

            }

            override fun onAnimationEnd(animation: Animation?) {
                handleResult(getResult(degree % 360))
            }

            override fun onAnimationStart(animation: Animation?) {

            }

        })
        coloredDrum.startAnimation(rotate)
    }

    private fun getResult(degree: Float): String {
        var text = ""

        var factor_x = 1
        var factor_y = 3

        var temp = 0

        while(temp < 7){
            if (degree >= (factor * factor_x) && degree < (factor * factor_y)) {
                text = colors[temp]
            }
            factor_x += 2
            factor_y += 2
            temp++
        }

        if (degree >= (factor * 13) && degree < 360 || degree >= 0 && degree < (factor * 1)) {
            text = colors[colors.size - 1]
        }

        return text
    }

    fun onClickReset(view: View) {
        image.visibility = View.GONE
        customText.visibility = View.GONE
    }

    private fun handleResult(result: String) {
        when (result) {
            "Красный", "Желтый", "Голубой", "Фиолетовый" -> {
                customText.setText(result)
                customText.visibility = View.VISIBLE
                image.visibility = View.GONE
            }
            "Оранжевый", "Зеленый", "Синий" -> {

                var randomHash = UUID.randomUUID().toString()
                var imageUrl = "https://loremflickr.com/320/240?${randomHash}"
                Picasso.get().load(imageUrl).into(image)
                image.visibility = View.VISIBLE
                customText.visibility = View.GONE
            }
            else -> {
                image.visibility = View.GONE
                customText.visibility = View.GONE
            }
        }
    }

    private fun calculateNewRadius(progress: Int): Float {
        val displayMetrics = Resources.getSystem().displayMetrics
        val screenWidth = displayMetrics.widthPixels
        val screenHeight = displayMetrics.heightPixels

        val minRadius = Math.min(screenWidth, screenHeight) / 4
        val maxRadius = Math.min(screenWidth, screenHeight) / 2.7

        val newRadius = minRadius + (maxRadius - minRadius) * progress / sizeSeekBar.max

        return newRadius.toFloat()
    }

}