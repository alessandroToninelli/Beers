package com.example.beers.data

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource


class DataSourceFactory<Type>{

    val source = MutableLiveData<PageDataSource<Type>>()

    fun factory(provider: PageDataSourceProvider<List<Type>>) =
        object : DataSource.Factory<Int, Type>() {
            override fun create(): DataSource<Int, Type> {
                return PageDataSource(provider).also { source.postValue(it) }
            }
        }
}

