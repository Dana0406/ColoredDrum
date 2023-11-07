package com.example.coloreddrum

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View

class ColoredDrumView(context: Context, attrs:  AttributeSet) :
View(context,attrs){
    private val paint = Paint()
    private val rainbowColors = intArrayOf(
        Color.parseColor("#ff0000"), Color.parseColor("#ffA500"),
        Color.parseColor("#ffff00"), Color.parseColor("#008000"),
        Color.parseColor("#42aaff"), Color.parseColor("#0000ff"), Color.parseColor("#8b00ff"))

    init {
        paint.style = Paint.Style.FILL
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        val centerX = width / 2
        val centerY = height / 2
        val radius = Math.min(centerX, centerY).toFloat()

        val angle = 360f / rainbowColors.size

        for (i in rainbowColors.indices) {
            paint.color = rainbowColors[i]
            canvas.drawArc(centerX - radius, centerY - radius, centerX + radius, centerY + radius, i * angle, angle, true, paint)
        }
    }

}