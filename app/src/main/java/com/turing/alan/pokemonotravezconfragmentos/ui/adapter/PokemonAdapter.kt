package com.turing.alan.pokemonotravezconfragmentos.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.NavHostFragment.Companion.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.turing.alan.pokemonotravezconfragmentos.data.model.Pokemon
import com.turing.alan.pokemonotravezconfragmentos.databinding.PokemonItemBinding
import com.turing.alan.pokemonotravezconfragmentos.ui.list.PokemonListFragment
import com.turing.alan.pokemonotravezconfragmentos.ui.list.PokemonListFragmentDirections

class PokemonAdapter(
    val onClick:((View, Pokemon)->Unit)
) : ListAdapter<Pokemon, PokemonAdapter.PokemonViewHolder>(PokemonDiffCallBack()) {

    inner class PokemonViewHolder(val binding: PokemonItemBinding): RecyclerView.ViewHolder(binding.root) {
        fun bindTask(p:Pokemon){
            binding.pokemonName.text = p.name
            binding.pokemonImg.load(p.front)
            binding.card.setOnClickListener {
                onClick(it,p)
            }
        }
    }

    private class PokemonDiffCallBack : DiffUtil.ItemCallback<Pokemon>() {
        override fun areItemsTheSame(oldItem: Pokemon, newItem: Pokemon): Boolean =
            oldItem == newItem

        override fun areContentsTheSame(oldItem: Pokemon, newItem: Pokemon): Boolean =
            oldItem == newItem
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PokemonViewHolder {
        val binding = PokemonItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false,)
        return PokemonViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PokemonViewHolder, position: Int) {
        val task = getItem(position)
        holder.bindTask(task)
    }
}