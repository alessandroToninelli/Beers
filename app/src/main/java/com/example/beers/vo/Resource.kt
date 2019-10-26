package com.example.beers.vo

sealed class Resource<out T>(val status: Status, open val data: T?, open val message: String?) {

    data class Success<out T>(override val data: T) : Resource<T>(Status.SUCCESS, data, null)
    data class Loading<out T>(override val data: T?) : Resource<T>(Status.LOADING, data, null)
    data class Error<out T>(override val message: String?, override val data: T?) :
        Resource<T>(Status.ERROR, data, null)

}
