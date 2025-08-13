package com.example.animeworld.data.mapper

import com.example.animeworld.data.local.FavoriteCharacterEntity
import com.example.animeworld.data.remote.CharacterDto
import com.example.animeworld.domain.model.Character

// API → Domain
fun CharacterDto.toDomain(): Character {
    return Character(
        id = mal_id,
        name = name,
        imageUrl = images.jpg.image_url
    )
}

// Entity -> Domain

fun FavoriteCharacterEntity.toDomain():Character{
    return Character(
        id = id,
        name = name,
        imageUrl = imageUrl
    )
}

// Domain -> Entity

fun Character.toFavoriteEntity():FavoriteCharacterEntity{
    return FavoriteCharacterEntity(
        id = id,
        name = name,
        imageUrl = imageUrl
    )
}

fun FavoriteCharacterEntity.toCharacter(): Character {
    return Character(
        id = id,
        name = name,
        imageUrl = imageUrl

    )
}