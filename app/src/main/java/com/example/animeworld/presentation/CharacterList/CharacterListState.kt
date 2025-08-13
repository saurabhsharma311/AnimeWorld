package com.example.animeworld.presentation.CharacterList

import com.example.animeworld.domain.model.Character

data class CharacterListState(
    val characters: List<Character> = emptyList(),
    val filteredCharacters : List<Character> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null,
    val searchQuery: String? = ""
)
