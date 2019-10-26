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

@BindingAdapter("android:adapterSetup", "android:list")
fun setupRecyclerViewAdapter(view: RecyclerView, viewModel: BeersViewModel, list: PagedList<BeerResponse>?) {

    view.layoutManager = LinearLayoutManager(view.context)
    view.adapter = viewModel.adapter
    list?.let {
        viewModel.adapter.submitList(it)
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