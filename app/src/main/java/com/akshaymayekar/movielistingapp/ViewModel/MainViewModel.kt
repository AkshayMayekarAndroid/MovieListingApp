package com.akshaymayekar.movielistingapp.ViewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.akshaymayekar.movielistingapp.domain.MovieRepository
import com.akshaymayekar.movielistingapp.domain.model.Content
import com.akshaymayekar.movielistingapp.domain.model.Movie
import com.akshaymayekar.movielistingapp.ui.adapter.PaginationListener
import com.akshaymayekar.movielistingapp.util.Response
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val movieRepository: MovieRepository
) : ViewModel() {

    var currentPage: Int = PaginationListener.PAGE_START
    var isLastPagee = false
    private var totalPage = 10
    var isLoadingg = false

    private var _movieState = MutableStateFlow<Response<Movie>>(Response.Success(null))
    val movieState: StateFlow<Response<Movie>> = _movieState

    var _list = mutableListOf<Content>();
    val list : List<Content> = _list


    fun getMovieListing(page: Int) {


        viewModelScope.launch(Dispatchers.IO) {
            movieRepository.getMovieInformation(page).collect{
                    _movieState.value = it

            }
        }
    }
}