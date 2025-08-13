package com.example.animeworld.domain.useCase.favorites

import com.example.animeworld.domain.repsoitory.FavouriteCharacterRepository
import com.example.animeworld.domain.model.Character
import jakarta.inject.Inject
import kotlinx.coroutines.flow.Flow

class GetFavoriteCharactersUseCase @Inject constructor(private val repository : FavouriteCharacterRepository) {
    operator fun invoke(): Flow<List<Character>>{
        return repository.getFavoriteCharacters()
    }

}