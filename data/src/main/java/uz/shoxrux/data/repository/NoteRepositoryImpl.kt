package uz.shoxrux.data.repository

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import uz.shoxrux.data.locale.room.NoteDao
import uz.shoxrux.data.mapper.toData
import uz.shoxrux.data.mapper.toDomain
import uz.shoxrux.domain.model.NoteItem
import uz.shoxrux.domain.repository.NoteRepository
import javax.inject.Inject

class NoteRepositoryImpl @Inject constructor(
    private val dao: NoteDao
) : NoteRepository {

    override fun getAllNotes(): Flow<List<NoteItem>> =
        dao.getAllNotes().map { list -> list.map { it.toDomain() } }

    override suspend fun addNote(note: NoteItem) {
        dao.insert(note.toData())
    }

    override suspend fun deleteNote(id: Int) {
        dao.deleteById(id)
    }

}