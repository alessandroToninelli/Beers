package com.example.beers.data

import androidx.lifecycle.Transformations
import androidx.paging.PageKeyedDataSource
import androidx.paging.toLiveData
import com.example.beers.api.BeerApi
import com.example.beers.domain.GetBeerUseCase
import com.example.beers.model.BeerParam
import com.example.beers.model.BeerResponse
import com.example.beers.vo.Either
import com.example.beers.vo.Listing
import com.example.beers.vo.left
import com.example.beers.vo.right
import com.example.networkcalladapterlib.ResponseNetworkEmpty
import com.example.networkcalladapterlib.ResponseNetworkError
import com.example.networkcalladapterlib.ResponseNetworkSuccess

interface BeerRepository {

    suspend fun loadBeer(beerParam: BeerParam?): Listing<BeerResponse>
}

class BeerRepo(private val api: BeerApi) : BeerRepository {


    override suspend fun loadBeer(beerParam: BeerParam?): Listing<BeerResponse> {


        return PageDataSource.Builder.build(object : PageDataSourceProvider<List<BeerResponse>>(){

            override suspend fun loadAfterCall(params: PageKeyedDataSource.LoadParams<Int>): Either<Exception, List<BeerResponse>> {


              return  (beerParam?.let {
                    api.getBeersByInterval(
                        params.key,
                        params.requestedLoadSize,
                        "${beerParam.brewed_before_month}-${beerParam.brewed_before_year}",
                        "${beerParam.brewed_after_month}-${beerParam.brewed_after_year}"
                    )

                } ?: api.getBeers(params.key,params.requestedLoadSize)).run {
                    when(this) {
                        is ResponseNetworkSuccess -> right(this.body)
                        is ResponseNetworkEmpty -> left(Exception(this.message))
                        is ResponseNetworkError -> left(this.exception)
                    }
                }

            }

            override suspend fun loadInitialCall(params: PageKeyedDataSource.LoadInitialParams<Int>): Either<Exception, List<BeerResponse>> {

                return  (beerParam?.let {
                    api.getBeersByInterval(
                        1,
                        params.requestedLoadSize,
                        "${beerParam.brewed_before_month}-${beerParam.brewed_before_year}",
                        "${beerParam.brewed_after_month}-${beerParam.brewed_after_year}"
                    )

                } ?: api.getBeers(1,params.requestedLoadSize)).run {
                    when(this) {
                        is ResponseNetworkSuccess -> right(this.body)
                        is ResponseNetworkEmpty -> left(Exception(this.message))
                        is ResponseNetworkError -> left(this.exception)
                    }
                }

            }

        }, 10)

    }

}