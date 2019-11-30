package com.constancio.presentation.ui.binding

import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import androidx.databinding.BindingAdapter

/**
 * Created by Constancio on 27/01/19.
 */
object BindingAdapters {

    @JvmStatic
    @BindingAdapter("android:visibility")
    fun View.setVisible(isVisible: Boolean?) {
        visibility = if (isVisible == true) VISIBLE else GONE
    }
}