package com.example.animeworld.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface FavoriteCharacterDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(character: FavoriteCharacterEntity)

    @Delete
    suspend fun delete(character: FavoriteCharacterEntity)

    @Query("SELECT * FROM favorite_characters")
    fun getAllFavourites(): Flow<List<FavoriteCharacterEntity>>

    @Query("SELECT EXISTS (SELECT 1 FROM favorite_characters WHERE id = :id)")
    suspend fun isCharacterFavorited(id: Int): Boolean

}