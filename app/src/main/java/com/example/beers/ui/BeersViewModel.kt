package com.example.beers.ui

import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagedList
import com.example.beers.domain.GetBeerUseCase
import com.example.beers.model.BeerParam
import com.example.beers.model.BeerResponse
import kotlinx.coroutines.launch


class BeersViewModel(val getBeerUseCase: GetBeerUseCase) : ViewModel() {

    private val getBeerUseCaseResult = getBeerUseCase.resultLiveData

    val networkState = Transformations.switchMap(getBeerUseCaseResult) {
        it.networkState

    }

    val beerResult = Transformations.switchMap(getBeerUseCaseResult){
        it.pagedList
    }

    init {
        viewModelScope.launch {
            getBeerUseCase(null)
        }
    }

    fun retry(v: View) {
        getBeerUseCaseResult.value?.retry?.invoke()

    }

    fun getBeerByInterval(param: BeerParam?) {
        getBeerUseCaseResult.value?.refresh
        getBeerUseCase(param)
    }

}