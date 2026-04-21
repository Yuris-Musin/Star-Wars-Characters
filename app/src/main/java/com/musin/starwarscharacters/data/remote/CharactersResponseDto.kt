package com.musin.starwarscharacters.data.remote

import kotlinx.serialization.Serializable

@Serializable
data class CharactersResponseDto(
    val characters: List<CharactersResponseItemDto> = listOf()
)