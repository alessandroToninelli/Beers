package com.example.beers.data

import androidx.paging.ItemKeyedDataSource
import androidx.paging.PageKeyedDataSource
import com.example.beers.api.BeerApi
import com.example.beers.model.BeerResponse
import com.example.beers.vo.Either
import com.example.beers.vo.left
import com.example.beers.vo.right
import com.example.networkcalladapterlib.ResponseNetworkEmpty
import com.example.networkcalladapterlib.ResponseNetworkError
import com.example.networkcalladapterlib.ResponseNetworkSuccess
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlin.Exception

abstract class PageDataSourceProvider<Response>{


    abstract suspend fun loadInitialCall(params: PageKeyedDataSource.LoadInitialParams<Int>): Either<Exception,Response>

    abstract suspend fun loadAfterCall(params: PageKeyedDataSource.LoadParams<Int>): Either<Exception,Response>

    open fun loadBeforeCall(params: PageKeyedDataSource.LoadParams<Int>): Either<Exception,Response>?{
        return null
    }

}




