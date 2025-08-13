package com.example.animeworld.di

import android.content.Context
import androidx.room.Room
import com.example.animeworld.data.local.AppDatabase
import com.example.animeworld.data.local.FavoriteCharacterDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {


    @Provides
    @Singleton
    fun provideAnimeDatabase( @ApplicationContext appcontext: Context): AppDatabase {
        return Room.databaseBuilder(appcontext, AppDatabase::class.java, "anime_db").build()
    }


    @Provides
    fun provideFavoriteCharacterDao(database: AppDatabase): FavoriteCharacterDao {
        return database.favoriteCharacterDao()
    }
}