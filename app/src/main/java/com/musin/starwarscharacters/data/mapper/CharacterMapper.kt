package com.musin.starwarscharacters.data.mapper

import com.musin.starwarscharacters.data.local.CharacterDbModel
import com.musin.starwarscharacters.data.remote.CharactersResponseItemDto
import com.musin.starwarscharacters.domain.entity.Character
import jakarta.inject.Inject

class CharacterMapper @Inject constructor() {

    fun dtoToDomain(dto: CharactersResponseItemDto, id: Int): Character {
        return Character(
            id = id,
            name = dto.name,
            height = dto.height,
            mass = dto.mass,
            hairColor = dto.hairColor,
            skinColor = dto.skinColor,
            eyeColor = dto.eyeColor,
            birthYear = dto.birthYear,
            gender = dto.gender,
            homeworld = dto.homeworld,
            films = dto.films,
            species = dto.species,
            vehicles = dto.vehicles,
            starships = dto.starships,
            url = dto.url
        )
    }

    fun dtoToDbModel(dto: CharactersResponseItemDto, id: Int): CharacterDbModel {
        return CharacterDbModel(
            id = id,
            name = dto.name,
            height = dto.height,
            mass = dto.mass,
            hairColor = dto.hairColor,
            skinColor = dto.skinColor,
            eyeColor = dto.eyeColor,
            birthYear = dto.birthYear,
            gender = dto.gender,
            homeworld = dto.homeworld,
            films = dto.films.joinToString("|"),
            species = dto.species.joinToString("|"),
            vehicles = dto.vehicles.joinToString("|"),
            starships = dto.starships.joinToString("|"),
            url = dto.url
        )
    }

    fun dbModelToDomain(dbModel: CharacterDbModel): Character {
        return Character(
            id = dbModel.id,
            name = dbModel.name,
            height = dbModel.height,
            mass = dbModel.mass,
            hairColor = dbModel.hairColor,
            skinColor = dbModel.skinColor,
            eyeColor = dbModel.eyeColor,
            birthYear = dbModel.birthYear,
            gender = dbModel.gender,
            homeworld = dbModel.homeworld,
            films = dbModel.films.split("|").filter { it.isNotEmpty() },
            species = dbModel.species.split("|").filter { it.isNotEmpty() },
            vehicles = dbModel.vehicles.split("|").filter { it.isNotEmpty() },
            starships = dbModel.starships.split("|").filter { it.isNotEmpty() },
            url = dbModel.url
        )
    }
}