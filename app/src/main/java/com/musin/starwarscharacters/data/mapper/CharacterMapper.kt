package com.musin.starwarscharacters.data.mapper

import com.musin.starwarscharacters.data.local.CharacterDbModel
import com.musin.starwarscharacters.data.remote.CharactersResponseItemDto
import com.musin.starwarscharacters.domain.entity.Character
import jakarta.inject.Inject

class CharacterMapper @Inject constructor() {

    fun dtoToDomain(dto: CharactersResponseItemDto): Character {
        return Character(
            eyeColor = dto.eyeColor,
            hairColor = dto.hairColor,
            height = dto.height,
            mass = dto.mass,
            name = dto.name
        )
    }

    fun dtoToDbModel(dto: CharactersResponseItemDto): CharacterDbModel {
        return CharacterDbModel(
            id = 0,
            eyeColor = dto.eyeColor,
            hairColor = dto.hairColor,
            height = dto.height,
            mass = dto.mass,
            name = dto.name
        )
    }

    fun dbModelToDomain(dbModel: CharacterDbModel): Character {
        return Character(
            eyeColor = dbModel.eyeColor,
            hairColor = dbModel.hairColor,
            height = dbModel.height,
            mass = dbModel.mass,
            name = dbModel.name
        )
    }
}