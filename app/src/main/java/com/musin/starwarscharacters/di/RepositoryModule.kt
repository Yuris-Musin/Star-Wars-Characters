package com.musin.starwarscharacters.di

import com.musin.starwarscharacters.data.repository.CharactersRepositoryImpl
import com.musin.starwarscharacters.domain.repository.CharactersRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface RepositoryModule {

    @Binds
    @Singleton
    fun bindCharactersRepository(
        impl: CharactersRepositoryImpl
    ): CharactersRepository
}