package com.akshaymayekar.movielistingapp.data

import com.akshaymayekar.movielistingapp.data.service.MovieListingService
import com.akshaymayekar.movielistingapp.domain.model.Movie
import com.akshaymayekar.movielistingapp.util.Response
import com.akshaymayekar.movielistingapp.domain.MovieRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MovieRepositoryImpl @Inject constructor(
    private var moviewListingService: MovieListingService,
    private val apiKey: String,
) : MovieRepository {

    lateinit var responseApi: Movie

    override fun getMovieInformation(page: Int): Flow<Response<Movie>> =
        flow {
            try {
                emit(Response.Loading)

                if (page == 1) {
                    responseApi = moviewListingService.getWeatherInformation()
                } else if (page == 2) {
                    responseApi = moviewListingService.getWeatherInformation2()
                } else if (page == 3) {
                    responseApi = moviewListingService.getWeatherInformation3()
                }
                emit(Response.Success(responseApi))
            } catch (e: Exception) {
                emit(Response.Failure(e))
            }
        }.flowOn(Dispatchers.IO)
}