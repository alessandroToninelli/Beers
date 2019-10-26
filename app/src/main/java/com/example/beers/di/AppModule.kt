package com.example.beers.di

import com.example.beers.api.BeerApi
import com.example.beers.data.BeerRepo
import com.example.beers.data.BeerRepository
import com.example.beers.domain.GetBeerUseCase
import com.example.beers.ui.BeersViewModel
import com.example.networkcalladapterlib.NetworkCallAdapterFactory
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


val appModule = module {

    factory {
        Retrofit.Builder()
            .baseUrl("https://api.punkapi.com/v2/")
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(NetworkCallAdapterFactory())
            .build()
            .create(BeerApi::class.java)
    }

    single {
        OkHttpClient.Builder().addInterceptor(HttpLoggingInterceptor().apply { this.level= HttpLoggingInterceptor.Level.BODY }).build()
    }

    single<BeerRepository>{ BeerRepo(get()) }

    single { GetBeerUseCase(get()) }

    viewModel { BeersViewModel(get()) }

}
