package com.example.animeworld.data.remote

import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface AnimeApi {


    @GET("characters")
    suspend fun getCharacters(
        @Query("page") page: Int = 1,
        @Query("name") query: String? = null
    ): CharacterResponse

    @GET("characters/{id}")
    suspend fun getCharacterById(
        @Path("id") id: Int
    ): CharacterDetailResponse


}