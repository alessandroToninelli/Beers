package com.example.beers.binding

import android.view.View
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.paging.PagedList
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.beers.R
import com.example.beers.model.BeerResponse
import com.example.beers.ui.BeersViewModel
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import java.lang.Exception
import kotlin.contracts.contract

@Suppress("UNCHECKED_CAST")
@BindingAdapter("pagedList")
fun <T> setupRecyclerViewAdapter(view: RecyclerView, list: PagedList<T>?) {

    list?.let {
        if (view.adapter is BindablePagingListAdapter<*>) {
            (view.adapter as BindablePagingListAdapter<T>).setData(list)
        }
    }
}

@BindingAdapter("imageUrl")
fun imageUrl(view: ImageView, url: String) {

    val picasso = Picasso.get()
    picasso.load(url)
        .placeholder(R.drawable.bianco)

        .error(R.drawable.no_image_available)
        .into(view, object : Callback {
            override fun onSuccess() {
            }

            override fun onError(e: Exception?) {

            }
        })

}


@BindingAdapter("showHide")
fun showHide(view: View, b: Boolean) {
    view.visibility = if (b) View.VISIBLE else View.GONE
}


