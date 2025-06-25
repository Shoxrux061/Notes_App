package uz.shoxrux.data.repository

import android.util.Log
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
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

    override suspend fun deleteNotes(id: List<Int>) {
        dao.deleteById(id)
    }

    override suspend fun filteredNotes(query: String): Flow<List<NoteItem>> = flow {

        val result = dao.searchNotes(query).map { it.toDomain() }
        emit(result)
    }

    override suspend fun getNoteById(id: Int): Flow<NoteItem> = flow {
        val result = dao.getNoteById(id).toDomain()
        emit(result)
    }

    override suspend fun editNote(note: NoteItem): Flow<Boolean> = flow {
        val result = dao.editNote(note.toData()) != 0
        Log.d("TAGEditDao", "editNote:$result $note")
        emit(result)
    }

}