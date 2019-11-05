package com.example.beers.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.paging.PagedList
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.AsyncDifferConfig
import androidx.recyclerview.widget.DiffUtil
import com.example.beers.R
import com.example.beers.binding.BindablePagingListAdapter
import com.example.beers.databinding.BeerItemBinding
import com.example.beers.databinding.BeersFragmentBinding
import com.example.beers.model.BeerResponse


class BeerAdapter(
    private val callback: (BeerResponse) -> Unit
) : PagedListAdapter<BeerResponse, BeerViewHolder<BeerItemBinding>>(DIFF_CALLBACK), BindablePagingListAdapter<BeerResponse> {

    override fun setData(list: PagedList<BeerResponse>) {
        submitList(list)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): BeerViewHolder<BeerItemBinding> {

        val binding = DataBindingUtil.inflate<BeerItemBinding>(
            LayoutInflater.from(parent.context),
            R.layout.beer_item,
            parent,
            false
        )

        binding.root.setOnClickListener {
            binding.beer?.let {
                callback.invoke(it)
            }
        }

        return BeerViewHolder(binding)

    }

    override fun onBindViewHolder(holder: BeerViewHolder<BeerItemBinding>, position: Int) {
        holder.binding.beer = getItem(position)
    }

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<BeerResponse>() {
            override fun areItemsTheSame(oldItem: BeerResponse, newItem: BeerResponse): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: BeerResponse, newItem: BeerResponse): Boolean {
                return oldItem.description == newItem.description
            }

        }

    }
}
