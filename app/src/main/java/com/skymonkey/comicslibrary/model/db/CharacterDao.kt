package com.skymonkey.comicslibrary.model.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.skymonkey.comicslibrary.model.db.Constants.CHARACTER_TABLE
import kotlinx.coroutines.flow.Flow

@Dao
interface CharacterDao {
    @Query("SELECT * FROM $CHARACTER_TABLE ORDER BY id ASC")
    fun getCharacters(): Flow<List<CharacterEntity>>

    @Query("SELECT * FROM $CHARACTER_TABLE WHERE id = :characterId")
    fun getCharacter(characterId: Int): Flow<CharacterEntity>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun addCharacter(character: CharacterEntity)

    @Update
    fun updateCharacter(character: CharacterEntity)

    @Delete
    fun deleteCharacter(character: CharacterEntity)
}