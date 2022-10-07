package com.flab.findmycat.ui.detail

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.flab.findmycat.databinding.CatDetailItemBinding
import com.flab.findmycat.domain.Image
import com.flab.findmycat.util.LoadMoreListener

class CatDetailAdapter(
    private val lifecycleOwner: LifecycleOwner,
    private val catDetailViewModel: LoadMoreListener
) : ListAdapter<Image, CatDetailAdapter.CatDetailViewHolder>(DiffCallback) {

    class CatDetailViewHolder(
        private val binding: CatDetailItemBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(image: Image?) {
            binding.imageUrl = image?.url
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CatDetailViewHolder {
        val binding = CatDetailItemBinding.inflate(LayoutInflater.from(parent.context))
        binding.lifecycleOwner = lifecycleOwner
        return CatDetailViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CatDetailViewHolder, position: Int) {
        val image = getItem(position)
        holder.bind(image)
        if (itemCount - position < 2) {
            catDetailViewModel.loadMore()
        }
    }

    companion object DiffCallback : DiffUtil.ItemCallback<Image>() {
        override fun areItemsTheSame(oldItem: Image, newItem: Image): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Image, newItem: Image): Boolean {
            return oldItem == newItem
        }
    }
}
