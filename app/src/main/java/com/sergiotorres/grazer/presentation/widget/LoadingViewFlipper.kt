package com.sergiotorres.grazer.presentation.widget

import android.content.Context
import android.util.AttributeSet
import android.view.Gravity
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.ViewFlipper
import com.sergiotorres.grazer.R

private const val CONTENT_VIEW_INDEX = 0
private const val LOADING_VIEW_INDEX = 1
private const val ERROR_VIEW_INDEX = 2

class LoadingViewFlipper : ViewFlipper {

    constructor(context: Context) : super(context)

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)

    private val progressView by lazy {
        ProgressBar(context).apply {
            val params = LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            )
            params.gravity = Gravity.CENTER
            layoutParams = params
        }
    }

    private val errorView by lazy {
        TextView(context).apply {
            val params = LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            )
            params.gravity = Gravity.CENTER
            layoutParams = params
        }
    }

    override fun onFinishInflate() {
        super.onFinishInflate()
        check(childCount <= 1) { "Can host only one direct child" }

        addView(progressView, LOADING_VIEW_INDEX)
        addView(errorView, ERROR_VIEW_INDEX)

        setErrorViewTitle(context.getString(R.string.content_error_default))

        if (!isInEditMode) {
            displayedChild = LOADING_VIEW_INDEX
        }
    }

    fun showLoading() {
        displayedChild = LOADING_VIEW_INDEX
    }

    fun showError() {
        displayedChild = ERROR_VIEW_INDEX
    }

    fun showContent() {
        displayedChild = CONTENT_VIEW_INDEX
    }

    fun setErrorViewTitle(errorMessage: String) {
        errorView.text = errorMessage
    }

}
