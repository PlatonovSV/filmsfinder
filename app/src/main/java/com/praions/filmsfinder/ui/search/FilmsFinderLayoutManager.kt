package com.praions.filmsfinder.ui.search

import android.content.Context
import android.content.res.Configuration
import android.graphics.PointF
import android.util.DisplayMetrics
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearSmoothScroller
import androidx.recyclerview.widget.RecyclerView

class FilmsFinderLayoutManager(context: Context?, deviceOrientation: Int) : GridLayoutManager(
    context,
    if (deviceOrientation == Configuration.ORIENTATION_PORTRAIT) PORTRAIT_ORIENTATION_SPAN_COUNT else LANDSCAPE_ORIENTATION_SPAN_COUNT
) {

    private var spanSizeCalculator = SpanSizeCalculator(deviceOrientation)

    init {
        spanSizeLookup = object : SpanSizeLookup() {
            override fun getSpanSize(position: Int) = spanSizeCalculator.calculate(position)
        }
    }

    override fun smoothScrollToPosition(
        recyclerView: RecyclerView, state: RecyclerView.State?, position: Int
    ) {
        val linearSmoothScroller: LinearSmoothScroller =
            object : LinearSmoothScroller(recyclerView.context) {
                override fun computeScrollVectorForPosition(targetPosition: Int): PointF? {
                    return super.computeScrollVectorForPosition(targetPosition)
                }

                override fun calculateSpeedPerPixel(displayMetrics: DisplayMetrics): Float {
                    return MILLISECONDS_PER_INCH / displayMetrics.densityDpi
                }
            }

        linearSmoothScroller.targetPosition = position
        startSmoothScroll(linearSmoothScroller)
    }

    fun setGenresCount(count: Int) {
        if (count >= 0) {
            spanSizeCalculator.genresCount = count
        }
    }

    companion object {
        private const val MILLISECONDS_PER_INCH = 5f
        private const val PORTRAIT_ORIENTATION_SPAN_COUNT = 2
        private const val LANDSCAPE_ORIENTATION_SPAN_COUNT = 4
    }
}

class SpanSizeCalculator(
    val deviceOrientation: Int, var genresCount: Int = 0
) {
    fun calculate(position: Int): Int {
        return if (deviceOrientation == Configuration.ORIENTATION_PORTRAIT) {
            when {
                position < genresCount + 2 -> 2
                else -> 1
            }
        } else {
            when {
                position == 0 -> 4
                position < genresCount + 1 -> 2
                position == genresCount + 1 -> 4
                else -> 1
            }
        }
    }
}
