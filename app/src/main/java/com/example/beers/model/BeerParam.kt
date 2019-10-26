package com.example.beers.model

data class BeerParam(
    val brewed_before_month: Int,
    val brewed_before_year: Int,
    val brewed_after_month: Int,
    val brewed_after_year: Int

)