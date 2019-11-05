package com.example.beers.binding

import androidx.paging.PagedList

interface BindablePagingListAdapter<T> {
    fun setData(list: PagedList<T>)
}
