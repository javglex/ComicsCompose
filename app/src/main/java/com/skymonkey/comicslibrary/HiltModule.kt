package com.skymonkey.comicslibrary

import android.content.Context
import androidx.room.Room
import com.skymonkey.comicslibrary.model.api.ApiService
import com.skymonkey.comicslibrary.model.api.MarvelApiRepo
import com.skymonkey.comicslibrary.model.db.CharacterDao
import com.skymonkey.comicslibrary.model.db.CollectionDb
import com.skymonkey.comicslibrary.model.db.CollectionRepository
import com.skymonkey.comicslibrary.model.db.CollectionRepositoryDb
import com.skymonkey.comicslibrary.model.db.Constants.DB
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.qualifiers.ApplicationContext

@Module
@InstallIn(ViewModelComponent::class)
class HiltModule {
    @Provides
    fun provideApiRepo() = MarvelApiRepo(ApiService.api)

    @Provides
    fun provideCollectionDb(@ApplicationContext context: Context) =
        Room.databaseBuilder(context, CollectionDb::class.java, DB).build()

    @Provides
    fun provideCharacterDao(collectionDb: CollectionDb) = collectionDb.characterDao()

    @Provides
    fun provideRepoImpl(characterDao: CharacterDao) : CollectionRepository =
        CollectionRepositoryDb(characterDao)

}