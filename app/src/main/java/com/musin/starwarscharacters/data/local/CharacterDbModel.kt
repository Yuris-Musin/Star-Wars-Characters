package com.musin.starwarscharacters.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "characters")
data class CharacterDbModel(
    @PrimaryKey(autoGenerate = false) val id: Int,
    val name: String,
    val height: String,
    val mass: String,
    val hairColor: String,
    val skinColor: String,
    val eyeColor: String,
    val birthYear: String,
    val gender: String,
    val homeworld: String,
    val films: String,
    val species: String,
    val vehicles: String,
    val starships: String,
    val url: String
)
