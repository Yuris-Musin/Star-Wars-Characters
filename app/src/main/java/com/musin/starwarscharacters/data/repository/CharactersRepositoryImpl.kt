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

        val cached = characterDao.getCharacterById(id)
        if (cached != null) {
            return mapper.dbModelToDomain(cached)
        }

        val response = apiService.getCharacterById(id.toString())
        val character = mapper.dtoToDomain(response, id)

        characterDao.addCharacter(mapper.dtoToDbModel(response, id))
        
        return character
    }
    
    suspend fun fetchFromNetwork() {
        var page = 1
        var hasMore = true
        
        while (hasMore) {
            val response = apiService.getCharacters(page)
            response.results.forEach { dto ->
                val id = dto.url.trimEnd('/').substringAfterLast('/').toIntOrNull() ?: 0
                if (id > 0) {
                    characterDao.addCharacter(mapper.dtoToDbModel(dto, id))
                }
            }
            hasMore = response.next != null
            if (hasMore) page++
        }
    }
}