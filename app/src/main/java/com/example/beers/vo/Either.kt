package com.example.beers.vo

sealed class Either<out L, out R>{

    data class Left<out L>  (val a : L): Either<L, Nothing>()

    data class Right<out R>(val b: R): Either<Nothing, R>()

    val isRight
        get() = this is Right<R>

    val isLeft
        get() = this is Left<L>

    fun either(fl: (L) -> Unit, fr: (R) -> Unit){
        when(this){
            is Left -> fl(a)
            is Right -> fr(b)
        }
    }


}


fun <L> left(a: L) = Either.Left(a)

fun <R> right(b: R) = Either.Right(b)
