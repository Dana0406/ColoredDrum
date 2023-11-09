package com.example.coloreddrum.customViews

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View

class CustomTextView(context: Context, attrs: AttributeSet) : View(context, attrs) {
    private val textPaint = Paint()
    private var displayText = ""

    init {
        textPaint.color = Color.BLACK
        textPaint.textSize = 36f
        textPaint.textAlign = Paint.Align.CENTER
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        canvas.drawText(displayText, width / 2f, height / 2f, textPaint)
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)

        textPaint.textSize = width / 10f
        invalidate()
    }

    fun setText(text: String) {
        displayText = text
        invalidate()
    }


}
