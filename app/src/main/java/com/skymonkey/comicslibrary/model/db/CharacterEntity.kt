package com.skymonkey.comicslibrary.model.db

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.skymonkey.comicslibrary.comicsToString
import com.skymonkey.comicslibrary.model.CharacterResult
import com.skymonkey.comicslibrary.model.db.Constants.CHARACTER_TABLE

@Entity(tableName = CHARACTER_TABLE)
data class CharacterEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val apiId: Int?,
    val name: String?,
    val thumbnail: String?,
    val comics: String?,
    val description: String?
) {
    companion object {
        fun fromCharacter(character: CharacterResult) =
            CharacterEntity(
                id = 0,
                apiId = character.id,
                name = character.name,
                thumbnail = character.thumbnail?.path + "." + character.thumbnail?.extension,
                // converts list of comics to string
                comics = character.comics?.items?.mapNotNull { it.name }?.comicsToString()
                    ?: "No Comics",
                description = character.description
            )
    }
}
