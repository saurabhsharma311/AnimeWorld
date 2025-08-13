package com.example.animeworld.data.remote

data class CharacterDto(
    val mal_id: Int,
    val name: String,
    val images: Images
)

data class Images(
    val jpg: ImageDetail
)

data class ImageDetail(
    val image_url: String
)