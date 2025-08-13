package com.example.animeworld.presentation.CharacterDetail

import com.example.animeworld.domain.model.Character

data class CharacterDetailState(
    val character: Character? = null,
    val isLoading: Boolean = false,
    val error: String? = null
)
