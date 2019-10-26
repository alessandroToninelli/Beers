package com.example.beers.data

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.paging.DataSource
import androidx.paging.PageKeyedDataSource
import androidx.paging.toLiveData
import com.example.beers.model.BeerResponse
import com.example.beers.vo.Listing
import com.example.beers.vo.NetworkState
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.security.Key

class PageDataSource<Type>(private val provider: PageDataSourceProvider<List<Type>>) :
    PageKeyedDataSource<Int, Type>() {

    private val sourceScope: CoroutineScope = CoroutineScope(Dispatchers.IO)

    private var retry: (() -> Any)? = null

    val networkState = MutableLiveData<NetworkState>()

    fun retryAllFailed() {
        val prevRetry = retry
        retry = null
        prevRetry?.let {
            sourceScope.launch {
                it()
            }
        }
    }


    override fun loadInitial(
        params: LoadInitialParams<Int>,
        callback: LoadInitialCallback<Int, Type>
    ) {

        networkState.postValue(NetworkState.LOADING)
        sourceScope.launch {
            provider.loadInitialCall(params).either(
                {
                    retry = { loadInitial(params, callback) }
                    val error = NetworkState.ERROR(it.message ?: "unknown error")
                    networkState.postValue(error)
                },
                {
                    retry = null
                    networkState.postValue(NetworkState.LOADED)
                    callback.onResult(it, 0, 2)
                })
        }
    }


    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, Type>) {

        networkState.postValue(NetworkState.LOADING)
        sourceScope.launch {
            provider.loadAfterCall(params).either(
                {
                    retry = { loadAfter(params, callback) }
                    val error = NetworkState.ERROR(it.message ?: "unknown error")
                    networkState.postValue(error)
                },
                {
                    retry = null
                    networkState.postValue(NetworkState.LOADED)
                    callback.onResult(it, params.key.inc())
                })
        }
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, Type>) { /*nothing*/ }

    object Builder{

        inline fun <reified Type> build(provider: PageDataSourceProvider<List<Type>>, size: Int): Listing<Type> {
            val builder = DataSourceFactory<Type>()
            val livePagedList = builder.factory(provider).toLiveData(size)
            val networkState = Transformations.switchMap(builder.source) {
                it.networkState
            }

            return Listing(
                pagedList = livePagedList,
                networkState = networkState,
                retry = {
                    builder.source.value?.retryAllFailed()
                },
                refresh = {
                    builder.source.value?.invalidate()
                }

            )

        }

    }

}

