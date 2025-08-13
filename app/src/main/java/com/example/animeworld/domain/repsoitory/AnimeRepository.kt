package com.example.animeworld.domain.repsoitory

import androidx.paging.PagingData
import com.example.animeworld.domain.model.Character
import kotlinx.coroutines.flow.Flow

interface AnimeRepository {
    fun getCharacters(query: String): Flow<PagingData<Character>>
    fun getCharacterById(id: Int): Flow<Character>
}