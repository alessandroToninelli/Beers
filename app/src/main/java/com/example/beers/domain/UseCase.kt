package com.example.beers.domain

import android.provider.Contacts
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import com.example.beers.vo.Either
import com.example.beers.vo.Listing
import com.example.beers.vo.Resource
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

abstract class UseCase<P, R> : AbstractUseCase<P, Either<Exception, R>>() {

    var resultLiveData = MutableLiveData<Resource<R>>()

    abstract override suspend fun exec(param: P?): Either<Exception, R>

    override fun invoke(param: P?, onResult: (Either<Exception, R>) -> Unit) {
        CoroutineScope(Dispatchers.IO).launch {
            resultLiveData.postValue(Resource.Loading(null))
            exec(param).also {
                onResult(it)
                it.either(
                    {
                        resultLiveData.postValue(Resource.Error(it.localizedMessage, null))
                    }, {
                        resultLiveData.postValue(Resource.Success(it))
                    })
            }

        }
    }
}

abstract class PagedUseCase<P, R> : AbstractUseCase<P, Listing<R>>() {

    var resultLiveData = MutableLiveData<Listing<R>>()

    abstract override suspend fun exec(param: P?): Listing<R>

    override fun invoke(param: P?, onResult: (Listing<R>) -> Unit) {
        CoroutineScope(Dispatchers.IO).launch {
            exec(param).also {
                resultLiveData.postValue(it)
                onResult(it)
            }

        }
    }

}

abstract class AbstractUseCase<P, R> {

    protected abstract suspend fun exec(param: P?): R

    abstract operator fun invoke(param: P?, onResult: ((R) -> Unit) = {})
}