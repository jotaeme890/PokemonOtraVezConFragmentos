package com.turing.alan.pokemonotravezconfragmentos.data.api

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.turing.alan.pokemonotravezconfragmentos.data.model.Pokemon
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path


interface  PokemonApi {
    @GET("api/v2/pokemon/{name}/")
    suspend fun fetchPokemon(@Path("name") id:String):PokemonDetailResponse

    @GET("api/v2/pokemon/")
    suspend fun fetchPokemonList():PokemonListResponse
}

class PokemonRepository private constructor(private val api:PokemonApi) {

    private val _pokemon = MutableLiveData<PokemonListApiModel>()
    val pokemon: LiveData<PokemonListApiModel>
        get() = _pokemon

    companion object {
        private var _INSTANCE: PokemonRepository? = null
        fun getInstance(): PokemonRepository {
            val retrofit = Retrofit.Builder()
                .baseUrl("https://pokeapi.co/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
            val pokemonApi = retrofit.create(PokemonApi::class.java)
            _INSTANCE = _INSTANCE ?: PokemonRepository(pokemonApi)
            return _INSTANCE!!
        }
    }

    suspend fun fetch() {
        val pokemonList = api.fetchPokemonList()

        val pokemonListWithDetails = pokemonList.results.map {
            val detailResponse = api.fetchPokemon(it.name)
            PokemonApiModel(
                detailResponse.id,
                detailResponse.name,
                detailResponse.weight,
                detailResponse.height,
                detailResponse.sprites.front_default
            )
        }
        _pokemon.value = PokemonListApiModel(pokemonListWithDetails)
    }

    suspend fun fetchByName(name: String): PokemonApiModel {
        val detailResponse = api.fetchPokemon(name)
        return PokemonApiModel(
            detailResponse.id,
            detailResponse.name,
            detailResponse.weight,
            detailResponse.height,
            detailResponse.sprites.front_default
        )
    }
}