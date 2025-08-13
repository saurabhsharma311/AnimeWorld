package com.example.animeworld.data.repository

import com.example.animeworld.data.local.FavoriteCharacterDao
import com.example.animeworld.data.mapper.toCharacter
import com.example.animeworld.data.mapper.toFavoriteEntity
import com.example.animeworld.domain.model.Character
import com.example.animeworld.domain.repsoitory.FavouriteCharacterRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class FavoriteCharacterRepositoryImpl @Inject constructor(private val dao: FavoriteCharacterDao) :
    FavouriteCharacterRepository {
    override fun getFavoriteCharacters(): Flow<List<Character>> {
        return dao.getAllFavourites().map { list ->
            list.map {
                it.toCharacter()
            }
        }
    }

    override suspend fun addToFavorites(character: Character) {
        dao.insert(character.toFavoriteEntity())
    }

    override suspend fun removeFromFavorites(character: Character) {
        dao.delete(character.toFavoriteEntity())
    }

    override suspend fun isCharacterFavorited(id: Int): Boolean {
        return dao.isCharacterFavorited(id)
    }

}
