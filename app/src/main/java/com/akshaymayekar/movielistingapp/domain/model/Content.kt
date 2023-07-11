package com.akshaymayekar.movielistingapp.domain.model

import com.google.gson.annotations.SerializedName

data class Content(
    val name: String,
    @SerializedName("poster-image")
    val poster_image: String
)