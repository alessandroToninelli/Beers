package com.example.beers.ui

import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView

class BeerViewHolder<out T: ViewDataBinding> constructor(val binding: T): RecyclerView.ViewHolder(binding.root)