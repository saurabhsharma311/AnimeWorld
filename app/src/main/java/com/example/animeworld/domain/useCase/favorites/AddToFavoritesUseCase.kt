package com.example.animeworld.domain.useCase.favorites

import com.example.animeworld.domain.repsoitory.FavouriteCharacterRepository
import com.example.animeworld.domain.model.Character
import javax.inject.Inject

class AddToFavoritesUseCase @Inject constructor(private val repository: FavouriteCharacterRepository) {
  suspend operator fun invoke(character: Character) {
        repository.addToFavorites(character)

    }
}