package com.enesselcuk.githubapiapp.util

import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import androidx.core.view.isGone
import androidx.core.view.isVisible
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide


@BindingAdapter("app:avatarImage")
fun ImageView.setStoreUrl(imageIcon: String?) {
    Glide.with(context)
        .load(imageIcon)
        .fitCenter()
        .into(this)
}

@BindingAdapter("app:ViewState")
fun View.setProgress(visible: Boolean) {
    this.isVisible = if (visible) isGone else isVisible
}

