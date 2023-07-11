package com.akshaymayekar.movielistingapp.di

import android.content.Context
import com.akshaymayekar.movielistingapp.data.MovieRepositoryImpl
import com.akshaymayekar.movielistingapp.data.service.MockRequestInterceptor
import com.akshaymayekar.movielistingapp.data.service.MovieListingService
import com.akshaymayekar.movielistingapp.domain.MovieRepository
import com.akshaymayekar.weatherapp.util.Const.KEY_API
import com.akshaymayekar.weatherapp.util.Const.WEB_API
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Named

@Module
@InstallIn(SingletonComponent::class)
class AppModules {

    @Provides
    @Named("WEB_API")
    fun provideWebAPI(): String = WEB_API

    @Provides
    @Named("KEY_API")
    fun provideKeyAPI(): String = KEY_API


    @Provides
    fun provideRetrofit(
        @Named("WEB_API") webAPI: String,
        @ApplicationContext appContext: Context,
    ): Retrofit {
        val client = OkHttpClient
            .Builder()
            .addInterceptor(
                HttpLoggingInterceptor()
                    .setLevel(HttpLoggingInterceptor.Level.BODY)
            )
            .addInterceptor(MockRequestInterceptor(appContext))
            .build()
        return Retrofit.Builder()
            .baseUrl(webAPI)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
    }

    @Provides
    fun provideNowPlayingService(
        retrofit: Retrofit
    ): MovieListingService = retrofit.create(MovieListingService::class.java)


    @Provides
    fun provideWeatherRepository(
        moviewListingService: MovieListingService,
        @Named("KEY_API") apiKey: String
    ): MovieRepository = MovieRepositoryImpl(
        moviewListingService = moviewListingService,
        apiKey = apiKey
    )
}