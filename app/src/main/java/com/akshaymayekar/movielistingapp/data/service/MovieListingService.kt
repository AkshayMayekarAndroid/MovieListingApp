package com.akshaymayekar.movielistingapp.data.service

import com.akshaymayekar.movielistingapp.domain.model.Movie
import retrofit2.http.GET
import retrofit2.http.Headers

interface MovieListingService {

    @Headers("mock:true")
    @GET("/data/2.5/CONTENTLISTINGPAGE-PAGE1?")
    suspend fun getWeatherInformation(
        //  @Query("appid") api_key : String,
    ): Movie

    @Headers("mock:true")
    @GET("/data/2.5/CONTENTLISTINGPAGE-PAGE2?")
    suspend fun getWeatherInformation2(
        //  @Query("appid") api_key : String,
    ): Movie

    @Headers("mock:true")
    @GET("/data/2.5/CONTENTLISTINGPAGE-PAGE3?")
    suspend fun getWeatherInformation3(
        //  @Query("appid") api_key : String,
    ): Movie
}