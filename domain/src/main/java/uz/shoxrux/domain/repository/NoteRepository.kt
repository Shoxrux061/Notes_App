package uz.shoxrux.domain.repository

import kotlinx.coroutines.flow.Flow
import uz.shoxrux.domain.model.NoteItem

interface NoteRepository {

    fun getAllNotes(): Flow<List<NoteItem>>

    suspend fun addNote(note: NoteItem)

    suspend fun deleteNotes(id: List<Int>)

    suspend fun filteredNotes(query: String): Flow<List<NoteItem>>

    suspend fun getNoteById(id: Int): Flow<NoteItem>

    suspend fun editNote(note: NoteItem): Flow<Boolean>

}