package com.tupleinfotech.matrixclick

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.PointF
import android.view.View

/**
 * @Author: athulyatech
 * @Date: 5/20/24
 */
class RayOverlayView(context: Context) : View(context) {

    private val paint = Paint().apply {
        color = Color.YELLOW // Change this to your desired color
        strokeWidth = 5f
        style = Paint.Style.STROKE
    }
    private val lines = mutableListOf<Pair<PointF, PointF>>()

    fun addRay(start: PointF, end: PointF) {
        lines.add(Pair(start, end))
        invalidate() // Trigger a redraw
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        for (line in lines) {
            canvas.drawLine(line.first.x, line.first.y, line.second.x, line.second.y, paint)
        }
    }
}
