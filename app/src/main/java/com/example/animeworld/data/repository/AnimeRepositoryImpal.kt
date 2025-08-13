package com.example.animeworld.data.repository


import com.example.animeworld.data.mapper.toDomain
import com.example.animeworld.data.remote.AnimeApi
import com.example.animeworld.domain.model.Character
import com.example.animeworld.domain.repsoitory.AnimeRepository
import jakarta.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import androidx.paging.*
import com.example.animeworld.data.paging.CharacterPagingSource
import kotlinx.coroutines.flow.map

class AnimeRepositoryImpl @Inject constructor(
    private val api: AnimeApi
) : AnimeRepository {
    override fun getCharacters(query: String): Flow<PagingData<Character>> {
        return Pager(
            config = PagingConfig(
                pageSize = 20,
                enablePlaceholders = false
            ),
            pagingSourceFactory = { CharacterPagingSource(api, query) }
        ).flow.map { pagingData ->
            pagingData.map { it.toDomain() }
        }
    }


    override fun getCharacterById(id: Int): Flow<Character> = flow {
        val character = api.getCharacterById(id).data.toDomain()
        emit(character)
    }
}