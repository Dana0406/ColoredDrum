package com.example.coloreddrum

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.animation.Animation
import android.view.animation.DecelerateInterpolator
import android.view.animation.RotateAnimation
import android.widget.Button
import android.widget.TextView
import java.util.*

class MainActivity : AppCompatActivity() {
    private lateinit var buttonStart: Button
    private lateinit var coloredDrum: ColoredDrumView
    private lateinit var textInput: TextView

    val random = Random()
    var oldDegree = 0F
    var degree = 0F
    val factor = 25.7143F

    private val colors = arrayListOf<String>("Голубой", "Синий", "Фиолетовый","Красный", "Оранжевый", "Желтый", "Зеленый")

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        buttonStart = findViewById(R.id.startButton)
        coloredDrum = findViewById(R.id.colored_drum)
        textInput = findViewById(R.id.textView)
    }

    fun onClickStart(view: View) {
        oldDegree = degree % 360
        degree = (random.nextInt(3600) + 720).toFloat()
        
        val direction = -1

        var rotate = RotateAnimation(oldDegree, oldDegree + direction * degree, RotateAnimation.RELATIVE_TO_SELF, 0.5F,
            RotateAnimation.RELATIVE_TO_SELF, 0.5F)


        rotate.duration = 3600
        rotate.fillAfter = true
        rotate.interpolator = DecelerateInterpolator()

        rotate.setAnimationListener(object: Animation.AnimationListener {
            override fun onAnimationRepeat(animation: Animation?) {
                textInput.text = ""
            }

            override fun onAnimationEnd(animation: Animation?) {
                textInput.text = getResult(degree % 360)
            }

            override fun onAnimationStart(animation: Animation?) {

            }

        })
        coloredDrum.startAnimation(rotate)
    }

    private fun getResult(degree: Float): String{
        var text = ""

        var factor_x = 1
        var factor_y = 3

        for(i in 0..15){
            if(degree >= (factor * factor_x) && degree < (factor * factor_y)){
                text = colors[i]
            }
            factor_x+=2
            factor_y+=2
        }

        if(degree >= (factor * 15) && degree < 360 || degree>=0 && degree < (factor*1)){
            text = colors[colors.size - 1]
        }


        return text
    }

}