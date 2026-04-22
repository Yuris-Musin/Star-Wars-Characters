package com.musin.starwarscharacters.data.remote

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CharactersResponseDto(
    @SerialName("results")
    val results: List<CharactersResponseItemDto> = listOf(),
    @SerialName("count")
    val count: Int = 0,
    @SerialName("next")
    val next: String? = null
)