package com.example.animeworld.domain.repsoitory

import com.example.animeworld.domain.model.Character
import kotlinx.coroutines.flow.Flow

interface FavouriteCharacterRepository {

    fun getFavoriteCharacters() : Flow<List<Character>>

    suspend fun addToFavorites(character: Character)

    suspend fun removeFromFavorites(character: Character)

    suspend fun isCharacterFavorited(id:Int): Boolean
}