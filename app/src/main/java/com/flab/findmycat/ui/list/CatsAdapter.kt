package com.flab.findmycat.ui.list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.flab.findmycat.databinding.CatItemBinding
import com.flab.findmycat.domain.Cat
import com.flab.findmycat.util.LoadMoreListener

interface CatClickListener {
    fun onClick(cat: Cat)
}

class CatsAdapter(
    private val lifecycleOwner: LifecycleOwner,
    private val clickListener: CatClickListener,
    private val loadMoreListener: LoadMoreListener
) : ListAdapter<Cat, CatsAdapter.CatsViewHolder>(DiffCallback) {

    class CatsViewHolder(
        private val binding: CatItemBinding,
        private val clickListener: CatClickListener
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(cat: Cat) {
            binding.clickListener = clickListener
            binding.cat = cat
        }
    }

    companion object DiffCallback : DiffUtil.ItemCallback<Cat>() {
        override fun areItemsTheSame(oldItem: Cat, newItem: Cat): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Cat, newItem: Cat): Boolean {
            return oldItem == newItem
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CatsViewHolder {
        val binding = CatItemBinding.inflate(LayoutInflater.from(parent.context))
        binding.lifecycleOwner = lifecycleOwner
        return CatsViewHolder(binding, clickListener)
    }

    override fun onBindViewHolder(holder: CatsViewHolder, position: Int) {
        val cat = getItem(position)
        if (itemCount - position < 2) {
            loadMoreListener.loadMore()
        }
        holder.bind(cat)
    }
}
