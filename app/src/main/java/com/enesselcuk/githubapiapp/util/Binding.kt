package com.enesselcuk.githubapiapp.util

import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide



@BindingAdapter("app:avatarImage")
fun ImageView.setStoreUrl(imageIcon: String?) {
    Glide.with(context)
        .load(imageIcon)
        .fitCenter()
        .into(this)
}


@BindingAdapter("app:progressbar")
fun ProgressBar.setProgress(visible: Boolean) {
    this.progress = if (visible) View.VISIBLE else View.GONE
}

