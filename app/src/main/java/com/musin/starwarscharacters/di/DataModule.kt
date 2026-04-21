package com.musin.starwarscharacters.di

import android.content.Context
import androidx.room.Room
import com.musin.starwarscharacters.data.local.CharacterDao
import com.musin.starwarscharacters.data.local.CharactersDataBase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface DataModule {

    companion object {

        @Provides
        @Singleton
        fun provideCharactersDataBase(
            @ApplicationContext context: Context
        ): CharactersDataBase {
            return Room.databaseBuilder(
                context = context,
                klass = CharactersDataBase::class.java,
                name = "characters.db"
            ).build()
        }

        @Provides
        @Singleton
        fun provideCharacterDao(
            dataBase: CharactersDataBase
        ): CharacterDao = dataBase.characterDao()
    }
}