package com.turing.alan.pokemonotravezconfragmentos.ui.list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.turing.alan.pokemonotravezconfragmentos.data.api.PokemonApiModel
import com.turing.alan.pokemonotravezconfragmentos.data.api.PokemonListApiModel
import com.turing.alan.pokemonotravezconfragmentos.data.api.PokemonRepository
import com.turing.alan.pokemonotravezconfragmentos.data.model.Pokemon
import kotlinx.coroutines.launch

// ViewModel es una clase para administrar la Ui
class PokemonListViewModel(): ViewModel() {
    // Creamos una instancia del PokemonRepository
    private val repository = PokemonRepository.getInstance()
    // Creamos un Observable
    private val _pokemonUi = MutableLiveData<List<Pokemon>>()
    val pokemonUi: LiveData<List<Pokemon>>
        get() = _pokemonUi
    // Observa cambios en PokemonApiModel, cuando tiene un cambio de valor, actualiza el valor de _pokemonUi con un nuevo objeto Pokemon
    private val observer = Observer<PokemonListApiModel> {
        //val pokemon: Pokemon
        _pokemonUi.value = it.list.map { pokemon ->
            Pokemon(pokemon.id, pokemon.name, pokemon.weight, pokemon.height, pokemon.front)
        }
    }
    // Cuando inicie haga la función fetch
    init {
        fetch()
    }

    private fun fetch() {
        // Estaremos observando siempre al observable de pokemon
        repository.pokemon.observeForever(observer)
        // Inicia una corrutina dentro del ámbito del ViewModel (viewModelScope) y llama al método fetch del repositorio para obtener datos
        viewModelScope.launch {
            repository.fetch()
        }
    }
    // Este método se llama cuando el ViewModel está a punto de ser destruido.
    override fun onCleared() {
        super.onCleared()
        // Borramos el Observer del LiveData para que no haya leaks de memoria
        repository
            .pokemon
            .removeObserver(observer)
    }
}