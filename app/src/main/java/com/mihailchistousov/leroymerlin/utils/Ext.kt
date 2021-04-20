package com.mihailchistousov.leroymerlin.utils

import android.app.Activity
import android.content.Context
import android.view.inputmethod.InputMethodManager
import androidx.transition.Transition

fun Transition.onAnimEnd(block: () -> Unit) {
    addListener(object : Transition.TransitionListener {
        override fun onTransitionStart(transition: Transition) {}
        override fun onTransitionEnd(transition: Transition) {
            block()
        }

        override fun onTransitionCancel(transition: Transition) {}
        override fun onTransitionPause(transition: Transition) {}
        override fun onTransitionResume(transition: Transition) {}
    })
}

fun Context.showKeyboard() {
    val inputMethodManager: InputMethodManager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    inputMethodManager.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0)
}

fun Activity.hideKeyboard() = window?.decorView?.let { (getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager).hideSoftInputFromWindow(it.windowToken, 0) } ?: false