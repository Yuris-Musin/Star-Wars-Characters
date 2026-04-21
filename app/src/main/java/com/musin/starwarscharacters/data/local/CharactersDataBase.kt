package com.musin.starwarscharacters.data.local

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [CharacterDbModel::class],
    version = 1,
    exportSchema = false
)
abstract class CharactersDataBase: RoomDatabase() {

    abstract fun characterDao(): CharacterDao
}