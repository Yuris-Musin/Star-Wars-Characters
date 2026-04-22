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
}

//import com.musin.starwarscharacters.data.local.CharacterDao
//import com.musin.starwarscharacters.data.mapper.CharacterMapper
//import com.musin.starwarscharacters.data.remote.api.StarWarsApi
//import com.musin.starwarscharacters.domain.repository.CharactersRepository
//import com.musin.starwarscharacters.domain.entity.Character
//import jakarta.inject.Inject
//import kotlinx.coroutines.flow.Flow
//import kotlinx.coroutines.flow.flow
//
//class CharactersRepositoryImpl @Inject constructor(
//    private val apiService: StarWarsApi,
//    private val characterDao: CharacterDao,
//    private val mapper: CharacterMapper
//) : CharactersRepository {
//
//    override fun getAllCharacters(): Flow<List<Character>> = flow {
//        // Сначала отдаём из кэша
//        characterDao.getAllCharacters()
//            .collect { cached ->
//                if (cached.isNotEmpty()) {
//                    emit(cached.map { mapper.dbModelToDomain(it) })
//                }
//            }
//    }
//
//    suspend fun fetchFromNetwork(): List<Character> {
//        val response = apiService.getCharacters()
//        return response.results.map { mapper.dtoToDomain(it) }
//    }
//}