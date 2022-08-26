package com.flab.findmycat.ui.list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.flab.findmycat.databinding.CatItemBinding
import com.flab.findmycat.domain.Cat

interface CatClickListener {
    fun onClick(cat: Cat)
}

class CatListAdapter(
    private val lifecycleOwner: LifecycleOwner,
    private val clickListener: CatClickListener
) : ListAdapter<Cat, CatListAdapter.CatListViewHolder>(DiffCallback) {

    class CatListViewHolder(
        private var binding: CatItemBinding,
        private val lifecycleOwner: LifecycleOwner,
        private val clickListener: CatClickListener
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(cat: Cat) {
            binding.lifecycleOwner = lifecycleOwner
            binding.clickListener = clickListener
            binding.cat = cat
        }
    }

    companion object DiffCallback : DiffUtil.ItemCallback<Cat>() {
        override fun areItemsTheSame(oldItem: Cat, newItem: Cat): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Cat, newItem: Cat): Boolean {
            return oldItem.name == newItem.name
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): CatListViewHolder {
        return CatListViewHolder(
            CatItemBinding.inflate(LayoutInflater.from(parent.context)),
            lifecycleOwner,
            clickListener
        )
    }

    override fun onBindViewHolder(holder: CatListViewHolder, position: Int) {
        val cat = getItem(position)
        holder.bind(cat)
    }
}
