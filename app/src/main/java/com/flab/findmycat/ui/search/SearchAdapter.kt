package com.flab.findmycat.ui.search

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.flab.findmycat.data.database.model.Repo
import com.flab.findmycat.databinding.ItemSearchRepositoryBinding

class SearchAdapter : RecyclerView.Adapter<SearchAdapter.SearchViewHolder>() {

    var repos: List<Repo>? = null
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun getItemCount() = repos?.size ?: 0

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        SearchViewHolder(ItemSearchRepositoryBinding.inflate(LayoutInflater.from(parent.context)))

    override fun onBindViewHolder(holder: SearchViewHolder, position: Int) {
        holder.binding.repo = repos?.get(position)
    }

    class SearchViewHolder(val binding: ItemSearchRepositoryBinding) :
        RecyclerView.ViewHolder(binding.root)
}
