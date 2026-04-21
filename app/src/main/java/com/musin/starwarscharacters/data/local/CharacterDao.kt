package com.musin.starwarscharacters.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy.Companion.IGNORE
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface CharacterDao {

    @Query("SELECT * FROM characters")
    fun getAllCharacters(): Flow<List<CharacterDbModel>>

    @Insert(onConflict = IGNORE)
    fun addCharacter(characterDbModel: CharacterDbModel)

    @Delete
    fun deleteCharacter(characterDbModel: CharacterDbModel)
}