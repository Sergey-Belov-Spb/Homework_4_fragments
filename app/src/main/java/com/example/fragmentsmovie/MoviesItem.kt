package com.example.fragmentsmovie

data class MoviesItem (
    val id: Int,
    val name: String,
    val image: String,
    var inFavorite: Boolean
)