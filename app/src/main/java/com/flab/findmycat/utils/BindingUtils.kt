package com.flab.findmycat.utils

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import coil.load
import coil.transform.CircleCropTransformation

@BindingAdapter("load_image")
fun loadImage(view: ImageView, url: String?) {
    view.load(url ?: "https://github.githubassets.com/images/modules/open_graph/github-mark.png") {
        crossfade(true)
        transformations(CircleCropTransformation())
    }
}
