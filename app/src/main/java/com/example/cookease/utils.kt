package com.example.cookease

import android.view.View
import android.widget.ProgressBar

fun ProgressBar.setInProgress(inProgress: Boolean) {
    visibility = if (inProgress) View.VISIBLE else View.GONE
}

