package uz.shoxrux.notesapp.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import uz.shoxrux.data.locale.room.NoteDao
import uz.shoxrux.data.repository.NoteRepositoryImpl
import uz.shoxrux.domain.repository.NoteRepository
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object HomeModule {

    @[Provides Singleton]
    fun provideNoteRepository(dao: NoteDao): NoteRepository {
        return NoteRepositoryImpl(dao)
    }

}