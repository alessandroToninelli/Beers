package com.example.beers.vo


sealed class NetworkState(
    val status: Status,
    val msg: String? = null
) {


    object LOADED : NetworkState(Status.SUCCESS)

    object LOADING : NetworkState(Status.LOADING)

    data class ERROR(val message: String?) : NetworkState(Status.ERROR, message)

}