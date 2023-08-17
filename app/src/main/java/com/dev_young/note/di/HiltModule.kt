package com.dev_young.note.di

import android.app.Application
import androidx.room.Room
import com.dev_young.note.data.data_source.NoteDataBase
import com.dev_young.note.data.repository.RepositoryImpl
import com.dev_young.note.domain.repository.Repository
import com.dev_young.note.domain.usecase.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object HiltModule {

    @Provides
    @Singleton
    fun provideNoteDatabase(app: Application): NoteDataBase {
        return Room.databaseBuilder(
            app,
            NoteDataBase::class.java,
            NoteDataBase.DB_NAME
        ).build()
    }

    @Provides
    @Singleton
    fun provideRepository(noteDataBase: NoteDataBase) : Repository {
        return RepositoryImpl(noteDataBase.dao())
    }

    @Provides
    @Singleton
    fun provideUseCases(repository: Repository) : UseCases {
        return UseCases(
            getNotes = GetNotes(repository),
            getNote = GetNote(repository),
            saveNote = SaveNote(repository),
            deleteNote = DeleteNote(repository)
        )
    }

}