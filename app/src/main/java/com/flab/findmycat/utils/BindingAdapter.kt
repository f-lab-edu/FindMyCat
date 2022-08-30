package com.flab.findmycat.utils

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.flab.findmycat.domain.Cat
import com.flab.findmycat.domain.Image
import com.flab.findmycat.ui.detail.CatDetailAdapter
import com.flab.findmycat.ui.list.CatListAdapter

@BindingAdapter("imageUrl")
fun setImageUrl(imageView: ImageView, url: String?) {
    url?.let {
        imageView.load(url)
    }
}

@BindingAdapter("listDataCats")
fun bindListDataCats(recyclerView: RecyclerView, data: List<Cat>?) {
    val adapter = recyclerView.adapter as CatListAdapter
    adapter.submitList(data)
}

@BindingAdapter("listDataCatDetails")
fun bindListDataCatDetails(recyclerView: RecyclerView, data: List<Image>?) {
    val adapter = recyclerView.adapter as CatDetailAdapter
    adapter.submitList(data)
}
