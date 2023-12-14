package com.skymonkey.comicslibrary.model.db

import kotlinx.coroutines.flow.Flow

class CollectionRepositoryDb(private val characterDao: CharacterDao): CollectionRepository {
    override suspend fun getCharacters(): Flow<List<CharacterEntity>> =
        characterDao.getCharacters()

    override suspend fun getCharacter(characterId: Int): Flow<CharacterEntity> =
        characterDao.getCharacter(characterId)

    override suspend fun addCharacter(character: CharacterEntity) =
        characterDao.addCharacter(character)

    override suspend fun updateCharacter(character: CharacterEntity) =
        characterDao.updateCharacter(character)

    override suspend fun deleteCharacter(character: CharacterEntity) =
        characterDao.deleteCharacter(character)
}