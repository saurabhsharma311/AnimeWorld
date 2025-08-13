package com.example.animeworld.domain.useCase.favorites

import com.example.animeworld.domain.repsoitory.FavouriteCharacterRepository
import javax.inject.Inject

class IsCharacterFavoritedUseCase @Inject constructor(private val repository: FavouriteCharacterRepository) {
    suspend operator fun invoke(id:Int):Boolean{
        return repository.isCharacterFavorited(id)
    }
}