package com.musin.starwarscharacters.data.repository

import com.musin.starwarscharacters.data.local.CharacterDao
import com.musin.starwarscharacters.data.mapper.CharacterMapper
import com.musin.starwarscharacters.data.remote.api.StarWarsApi
import com.musin.starwarscharacters.domain.entity.Character
import com.musin.starwarscharacters.domain.repository.CharactersRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class CharactersRepositoryImpl @Inject constructor(
    private val apiService: StarWarsApi,
    private val characterDao: CharacterDao,
    private val mapper: CharacterMapper
) : CharactersRepository {

    override fun getAllCharacters(): Flow<List<Character>> {
        return characterDao.getAllCharacters().map { list ->
            list.map { mapper.dbModelToDomain(it) }
        }
    }
    
    override suspend fun getCharacterById(id: Int): Character {
        // Try to get from cache first
        val cached = characterDao.getAllCharacters().value.find { it.id == id }
        if (cached != null) {
            return mapper.dbModelToDomain(cached)
        }
        
        // Fetch from network if not in cache
        val response = apiService.getCharacterById(id.toString())
        val character = mapper.dtoToDomain(response, id)
        
        // Save to cache
        characterDao.addCharacter(mapper.dtoToDbModel(response, id))
        
        return character
    }
    
    suspend fun fetchFromNetwork() {
        val response = apiService.getCharacters()
        response.results.forEachIndexed { index, dto ->
            val id = index + 1
            characterDao.addCharacter(mapper.dtoToDbModel(dto, id))
        }
    }
}