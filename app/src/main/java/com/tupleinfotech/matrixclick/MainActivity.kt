package com.tupleinfotech.matrixclick

import android.graphics.PointF
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.GridLayoutManager
import com.tupleinfotech.matrixclick.databinding.ActivityMainBinding
import kotlin.math.abs

class MainActivity : AppCompatActivity() {

    private var _binding : ActivityMainBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.recyclerView.layoutManager = GridLayoutManager(this, 5)
        binding.recyclerView.adapter = MatrixAdapter(5) { row, col ->
            onMatrixItemClickHorizontalLines(row, col)
            onMatrixItemClickVerticalLines(row, col)
            onMatrixItemClick(row, col)
        }
    }

    //horizontal lines
    private fun onMatrixItemClickHorizontalLines(row: Int, col: Int) {
        val itemCount = binding.recyclerView.childCount
        val recyclerView = binding.recyclerView
        val container = binding.main
        val rayOverlay = RayOverlayView(this)

        container.addView(rayOverlay)

        for (i in 0 until itemCount) {
            val view = recyclerView.getChildAt(i)
            val position = recyclerView.getChildAdapterPosition(view)
            val currentRow = position / 5
            val currentCol = position % 5

            if (currentRow == row) { // Only consider horizontal lines
                val startX = view.x + view.width / 2
                val startY = view.y + view.height / 2

                // Determine end points based on direction
                val endX: Float
                val endY: Float

                if (currentCol < col) { // Line goes to the right
                    endX = recyclerView.width.toFloat()
                    endY = startY
                } else { // Line goes to the left
                    endX = 0f
                    endY = startY
                }

                rayOverlay.addRay(PointF(startX, startY), PointF(endX, endY))
            }

        }

        // Remove the overlay after 1 second
        Handler(Looper.getMainLooper()).postDelayed({
            container.removeView(rayOverlay)
        }, 200) // 1000 milliseconds = 1 second
    }

    //vertical lines
    private fun onMatrixItemClickVerticalLines(row: Int, col: Int) {
        val itemCount = binding.recyclerView.childCount
        val recyclerView = binding.recyclerView
        val container = binding.main
        val rayOverlay = RayOverlayView(this)

        container.addView(rayOverlay)

        for (i in 0 until itemCount) {
            val view = recyclerView.getChildAt(i)
            val position = recyclerView.getChildAdapterPosition(view)
            val currentRow = position / 5
            val currentCol = position % 5

            if (currentCol == col) { // Only draw vertical lines
                val startX = view.x + view.width / 2
                val startY = if (currentRow == row) view.y + view.height / 2 else 0f // Start from the center of the clicked item

                val endX = startX
                val endY = if (currentRow == row) recyclerView.height.toFloat() else view.y + view.height.toFloat() // End at the bottom of the RecyclerView or the bottom of the current item

                rayOverlay.addRay(PointF(startX, startY), PointF(endX, endY))
            }
        }

        // Remove the overlay after 1 second
        Handler(Looper.getMainLooper()).postDelayed({
            container.removeView(rayOverlay)
        }, 200) // 1000 milliseconds = 1 second
    }

    //Both Side Diagonal Lines
    private fun onMatrixItemClick(row: Int, col: Int) {
        val recyclerView = binding.recyclerView
        val container = binding.main
        val rayOverlay = RayOverlayView(this)
        container.addView(rayOverlay)

        val clickedView = recyclerView.findViewHolderForAdapterPosition(row * 5 + col)?.itemView
        val clickedViewCenterX = clickedView?.x?.plus(clickedView.width / 2) ?: 0f
        val clickedViewCenterY = clickedView?.y?.plus(clickedView.height / 2) ?: 0f
        val recyclerViewWidth = recyclerView.width.toFloat()
        val recyclerViewHeight = recyclerView.height.toFloat()

        // Calculate end points for diagonal lines
        val endX1Top = 0f
        val endY1Top = 0f
        val endX2Top = recyclerViewWidth
        val endY2Top = 0f

        val endX1Bottom = 0f
        val endY1Bottom = recyclerViewHeight
        val endX2Bottom = recyclerViewWidth
        val endY2Bottom = recyclerViewHeight

        // Add diagonal lines to overlay
        rayOverlay.addRay(PointF(clickedViewCenterX, clickedViewCenterY), PointF(endX1Top, endY1Top))
        rayOverlay.addRay(PointF(clickedViewCenterX, clickedViewCenterY), PointF(endX2Top, endY2Top))
        rayOverlay.addRay(PointF(clickedViewCenterX, clickedViewCenterY), PointF(endX1Bottom, endY1Bottom))
        rayOverlay.addRay(PointF(clickedViewCenterX, clickedViewCenterY), PointF(endX2Bottom, endY2Bottom))

        // Remove the overlay after 1 second
        Handler(Looper.getMainLooper()).postDelayed({
            container.removeView(rayOverlay)
        }, 200) // 200 milliseconds for demonstration
    }

}