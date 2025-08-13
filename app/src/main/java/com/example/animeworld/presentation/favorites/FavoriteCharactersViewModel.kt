package com.example.animeworld.presentation.favorites

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.animeworld.data.local.FavoriteCharacterEntity
import com.example.animeworld.data.mapper.toCharacter
import com.example.animeworld.domain.model.Character
import com.example.animeworld.domain.useCase.favorites.AddToFavoritesUseCase
import com.example.animeworld.domain.useCase.favorites.GetFavoriteCharactersUseCase
import com.example.animeworld.domain.useCase.favorites.RemoveFromFavoritesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch


@HiltViewModel
class FavoriteCharactersViewModel @Inject constructor(
    private val getFavoriteCharactersUseCase: GetFavoriteCharactersUseCase,
    private val removeFromFavoritesUseCase: RemoveFromFavoritesUseCase,
    private val addToFavoritesUseCase: AddToFavoritesUseCase
) : ViewModel() {

    val favoriteCharacters: StateFlow<List<Character>> =
        getFavoriteCharactersUseCase()
            .stateIn(
                viewModelScope,
                SharingStarted.WhileSubscribed(5000),
                emptyList()
            )

    fun removeFromFavorites(character: Character) {
        viewModelScope.launch {
            removeFromFavoritesUseCase(character)
        }
    }

    fun addToFavorites(favoriteCharacter: FavoriteCharacterEntity) {
        viewModelScope.launch {
            try {
                Log.d("FavoriteVM", "Successfully added to favorites : ${favoriteCharacter.name} ")
                addToFavoritesUseCase(favoriteCharacter.toCharacter())
            }
            catch (e: Exception){
                Log.d("FavoriteVM", "Error adding to favorites : ${e.message} ")
            }
        }
    }
}





