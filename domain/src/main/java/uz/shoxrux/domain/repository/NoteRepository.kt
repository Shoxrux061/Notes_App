package uz.shoxrux.domain.repository

import kotlinx.coroutines.flow.Flow
import uz.shoxrux.domain.model.NoteItem

interface NoteRepository {

    fun getAllNotes(): Flow<List<NoteItem>>

    suspend fun addNote(note: NoteItem)

    suspend fun deleteNote(id: Int)

}