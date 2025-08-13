package com.example.animeworld.di

import com.example.animeworld.data.remote.AnimeApi
import com.example.animeworld.data.repository.FavoriteCharacterRepositoryImpl
import com.example.animeworld.domain.repsoitory.FavouriteCharacterRepository
import com.example.animeworld.utils.constants.BASE_URL
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
 class AppModule {

    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit =
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    @Provides
    @Singleton
    fun provideAnimeApi(retrofit: Retrofit): AnimeApi =
        retrofit.create(AnimeApi::class.java)


}