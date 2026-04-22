package com.musin.starwarscharacters.data.remote.api

import com.musin.starwarscharacters.data.remote.CharactersResponseDto
import com.musin.starwarscharacters.data.remote.CharactersResponseItemDto
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface StarWarsApi {

    @GET("swapi/people/")
    suspend fun getCharacters(
        @Query("page") page: Int = 1
    ): CharactersResponseDto

    @GET("swapi/people/{id}/")
    suspend fun getCharacterById(
        @Path("id") id: String
    ): CharactersResponseItemDto
}