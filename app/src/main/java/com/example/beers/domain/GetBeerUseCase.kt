package com.example.beers.domain

import androidx.lifecycle.Transformations
import com.example.beers.data.BeerRepository
import com.example.beers.model.BeerParam
import com.example.beers.model.BeerResponse
import com.example.beers.vo.Either
import com.example.beers.vo.Listing

class GetBeerUseCase(private val repo: BeerRepository) :
    PagedUseCase<BeerParam, BeerResponse>() {

    override suspend fun exec(param: BeerParam?): Listing<BeerResponse> {
        return repo.loadBeer(param)
    }


}