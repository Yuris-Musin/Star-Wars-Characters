package com.musin.starwarscharacters.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "characters")
data class CharacterDbModel(
    @PrimaryKey(autoGenerate = true) val id: Int,
    val eyeColor: String,
    val hairColor: String,
    val height: String,
    val mass: String,
    val name: String
)
