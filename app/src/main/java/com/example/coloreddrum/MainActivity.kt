package com.example.coloreddrum

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.animation.Animation
import android.view.animation.DecelerateInterpolator
import android.view.animation.RotateAnimation
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import com.squareup.picasso.Picasso
import java.util.*

class MainActivity : AppCompatActivity() {
    private lateinit var buttonStart: Button
    private lateinit var buttonReset: Button
    private lateinit var coloredDrum: ColoredDrumView
    private lateinit var textInput: TextView
    private lateinit var customText: CustomTextView
    private lateinit var image: ImageView

    val random = Random()
    var oldDegree = 0F
    var degree = 0F
    val factor = 25.7143F

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
        textInput = findViewById(R.id.textView)
        customText = findViewById(R.id.customText)
        image = findViewById(R.id.image)
    }

    fun onClickStart(view: View) {
        oldDegree = degree % 360
        degree = (random.nextInt(3600) + 720).toFloat()

        var rotate = RotateAnimation(
            oldDegree, degree, RotateAnimation.RELATIVE_TO_SELF, 0.5F,
            RotateAnimation.RELATIVE_TO_SELF, 0.5F
        )

        rotate.duration = 3600
        rotate.fillAfter = true
        rotate.interpolator = DecelerateInterpolator()

        rotate.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationRepeat(animation: Animation?) {
                textInput.text = ""
            }

            override fun onAnimationEnd(animation: Animation?) {
                var textResult = getResult(degree % 360)
                textInput.text = textResult +" " + degree + " " + oldDegree
                handleResult(textResult)
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
}