package com.example.animeworld.di

import com.example.animeworld.data.repository.AnimeRepositoryImpl
import com.example.animeworld.data.repository.FavoriteCharacterRepositoryImpl
import com.example.animeworld.domain.repsoitory.AnimeRepository
import com.example.animeworld.domain.repsoitory.FavouriteCharacterRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindAnimeRepository(
        impal: AnimeRepositoryImpl
    ) : AnimeRepository


    @Binds
    abstract fun bindFavoriteRepository(impl: FavoriteCharacterRepositoryImpl): FavouriteCharacterRepository
}