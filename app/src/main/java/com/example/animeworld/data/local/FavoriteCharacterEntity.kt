package com.example.animeworld.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "favorite_characters")
data class FavoriteCharacterEntity(
    @PrimaryKey val id: Int,
    val name: String,
    val imageUrl: String
)

