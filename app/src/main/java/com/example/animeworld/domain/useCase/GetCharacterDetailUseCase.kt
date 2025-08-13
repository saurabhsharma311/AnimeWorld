package com.example.animeworld.domain.useCase

import com.example.animeworld.domain.model.Character
import com.example.animeworld.domain.repsoitory.AnimeRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetCharacterDetailUseCase @Inject constructor(private val repository: AnimeRepository) {
    operator fun invoke(id: Int) : Flow<Character> {
        return repository.getCharacterById(id)
    }
}