package com.musin.starwarscharacters.domain.repository

import com.musin.starwarscharacters.domain.entity.Character
import kotlinx.coroutines.flow.Flow

interface CharactersRepository {

    fun getAllCharacters(): Flow<List<Character>>
    
    suspend fun getCharacterById(id: Int): Character
}