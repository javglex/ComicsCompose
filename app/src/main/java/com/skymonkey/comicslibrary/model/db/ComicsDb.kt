package com.skymonkey.comicslibrary.model.db

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [CharacterEntity::class], version = 1, exportSchema = false)
abstract class CollectionDb: RoomDatabase() {
    abstract fun characterDao(): CharacterDao
}