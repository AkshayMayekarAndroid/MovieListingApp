package com.akshaymayekar.movielistingapp.domain

import com.akshaymayekar.movielistingapp.domain.model.Movie
import com.akshaymayekar.movielistingapp.util.Response
import kotlinx.coroutines.flow.Flow

interface MovieRepository {
    fun getMovieInformation(page: Int): Flow<Response<Movie>>
}