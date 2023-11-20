package com.turing.alan.pokemonotravezconfragmentos.data.api

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.gson.annotations.SerializedName
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import retrofit2.http.GET
import retrofit2.http.Path

data class PokemonListResponse private constructor(
    val results: List<PokemonListItem>
)

data class PokemonListItem(
    val name: String
)

data class PokemonDetailResponse(
    val id: Int,
    val name: String,
    val weight:Double,
    val height:Double,
    val sprites: PokemonSpriteResponse
)

data class PokemonSpriteResponse(
    val front_default: String?
)