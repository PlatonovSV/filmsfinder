package com.praions.filmsfinder.ui.search

import android.content.Context
import android.graphics.PointF
import android.util.DisplayMetrics
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearSmoothScroller
import androidx.recyclerview.widget.RecyclerView

class FilmsFinderLayoutManager(context: Context?) : GridLayoutManager(context, 2) {

    private var twoElementsBeginningIndex = 2

    init {
        spanSizeLookup = object : SpanSizeLookup() {
            override fun getSpanSize(position: Int): Int {
                return when {
                    position < twoElementsBeginningIndex -> 2
                    else -> 1
                }
            }
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

    fun setTwoElementsBeginningIndex(index: Int) {
        twoElementsBeginningIndex = if (index > 2) index
        else 2
    }

    companion object {
        private const val MILLISECONDS_PER_INCH = 5f
    }
}
