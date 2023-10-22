package com.example.androiddemos.util

import android.graphics.Color
import android.graphics.ColorMatrix
import android.graphics.ColorMatrixColorFilter
import android.graphics.drawable.Drawable
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView

object GrayScaleUtils {

    private val originalStates = mutableMapOf<View, ViewState>()

    fun applyGrayScale(view: View) {
        val grayScaleFilter = createGrayScaleFilter()
        saveOriginalState(view)
        applyGrayScaleRecursive(view, grayScaleFilter)
        disableInteraction(view)
    }

    fun disableInteraction(view: View) {
        setInteractionRecursive(view, false)
    }

    fun restoreView(view: View) {
        restoreRecursive(view)
        view.invalidate()
    }

    private fun saveOriginalState(view: View) {
        originalStates[view] = ViewState.capture(view)
    }

    private fun createGrayScaleFilter(): ColorMatrixColorFilter {
        val cm = ColorMatrix()
        cm.setSaturation(0f)
        return ColorMatrixColorFilter(cm)
    }

    private fun applyGrayScaleRecursive(view: View, grayScaleFilter: ColorMatrixColorFilter) {
        saveOriginalState(view)
        if (view is ViewGroup) {
            val viewGroup = view
            for (i in 0 until viewGroup.childCount) {
                val child = viewGroup.getChildAt(i)
                applyGrayScaleRecursive(child, grayScaleFilter)
            }
        }
        if (view is ImageView) {
            view.colorFilter = grayScaleFilter
        } else if (view is TextView) {
            val textView = view
            val currentTextColor = textView.currentTextColor
            if (currentTextColor == Color.BLACK || currentTextColor == Color.WHITE) {
                textView.setTextColor(Color.GRAY)
            } else {
                val grayColor = convertToGrayScale(currentTextColor)
                textView.setTextColor(grayColor)
            }
            textView.compoundDrawables.forEach { drawable ->
                drawable?.mutate()?.colorFilter = grayScaleFilter
            }
        }
        val background = view.background
        if (background != null) {
            background.mutate().colorFilter = grayScaleFilter
        }
    }

    private fun setInteractionRecursive(view: View, isEnabled: Boolean) {
        view.isEnabled = isEnabled
        if (view is ViewGroup) {
            for (i in 0 until view.childCount) {
                val child = view.getChildAt(i)
                setInteractionRecursive(child, isEnabled)
            }
        }
    }

    private fun restoreRecursive(view: View) {
        val originalState = originalStates[view]!!
        if (view is ViewGroup) {
            for (i in 0 until view.childCount) {
                val child = view.getChildAt(i)
                restoreRecursive(child)
            }
        }
        if (view is ImageView) {
            view.colorFilter = originalState.colorFilter
        } else if (view is TextView) {
            view.setTextColor(originalState.textColor)
            originalState.compoundDrawableColorFilters.forEachIndexed { index, colorFilter ->
                view.compoundDrawables[index]?.colorFilter = colorFilter
            }
        }
        originalState.background?.let {
            view.background = it
        }
        if (originalState.backgroundColorFilter != null) {
            view.background?.mutate()?.colorFilter = originalState.backgroundColorFilter
        }else{
            view.background?.mutate()?.clearColorFilter()
        }
        view.isEnabled = originalState.isEnabled
    }

    private fun convertToGrayScale(color: Int): Int {
        val red = Color.red(color)
        val green = Color.green(color)
        val blue = Color.blue(color)
        val grayValue = (0.299 * red + 0.587 * green + 0.114 * blue).toInt()
        return Color.rgb(grayValue, grayValue, grayValue)
    }

    private data class ViewState(
        val colorFilter: ColorMatrixColorFilter?,
        val textColor: Int,
        val compoundDrawableColorFilters: List<ColorMatrixColorFilter?>,
        val backgroundColorFilter: ColorMatrixColorFilter?,
        val background: Drawable?,
        val isEnabled: Boolean
    ) {
        companion object {
            fun capture(view: View): ViewState {
                val colorFilter = (view as? ImageView)?.colorFilter as? ColorMatrixColorFilter
                val textColor = (view as? TextView)?.currentTextColor ?: Color.TRANSPARENT
                val compoundDrawableColorFilters = (view as? TextView)?.compoundDrawables
                    ?.map { it?.colorFilter as? ColorMatrixColorFilter } ?: emptyList()
                val backgroundColorFilter = view.background?.colorFilter as? ColorMatrixColorFilter
                val background = view.background
                val isEnabled = view.isEnabled
                return ViewState(
                    colorFilter,
                    textColor,
                    compoundDrawableColorFilters,
                    backgroundColorFilter,
                    background,
                    isEnabled
                )
            }
        }
    }
}
