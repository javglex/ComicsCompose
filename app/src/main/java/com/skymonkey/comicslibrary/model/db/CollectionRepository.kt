package com.skymonkey.comicslibrary.model.db

import kotlinx.coroutines.flow.Flow

interface CollectionRepository {
    suspend fun getCharacters(): Flow<List<CharacterEntity>>

    suspend fun getCharacter(characterId: Int): Flow<CharacterEntity>

    suspend fun addCharacter(character: CharacterEntity)

    suspend fun updateCharacter(character: CharacterEntity)

    suspend fun deleteCharacter(character: CharacterEntity)
}