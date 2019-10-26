package com.example.beers.api

import com.example.beers.model.BeerResponse
import com.example.networkcalladapterlib.ResponseNetwork
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface BeerApi {


    @GET("beers")
    suspend fun getBeers(@Query("page") page: Int, @Query("per_page") perPage: Int): ResponseNetwork<List<BeerResponse>>

    @GET("beers")
    suspend fun getBeersByInterval(@Query("page") page: Int, @Query("per_page") perPage: Int, @Query("brewed_before") before: String, @Query("brewed_after") after: String): ResponseNetwork<List<BeerResponse>>




}