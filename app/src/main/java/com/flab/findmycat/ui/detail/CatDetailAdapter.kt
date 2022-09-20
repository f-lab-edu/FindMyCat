package com.flab.findmycat.ui.detail

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.flab.findmycat.databinding.CatDetailItemBinding
import com.flab.findmycat.domain.Image

class CatDetailAdapter(
    private val lifecycleOwner: LifecycleOwner
) : ListAdapter<Image, CatDetailAdapter.CatDetailViewHolder>(DiffCallback) {

    class CatDetailViewHolder(
        private var binding: CatDetailItemBinding,
        private val lifecycleOwner: LifecycleOwner,
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(image: Image) {
            binding.lifecycleOwner = lifecycleOwner
            binding.image = image
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

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): CatDetailViewHolder {
        return CatDetailViewHolder(
            CatDetailItemBinding.inflate(LayoutInflater.from(parent.context)),
            lifecycleOwner
        )
    }

    override fun onBindViewHolder(holder: CatDetailViewHolder, position: Int) {
        val cat = getItem(position)
        holder.bind(cat)
    }
}
