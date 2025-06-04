package uz.shoxrux.presentation.screens.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import uz.shoxrux.domain.model.NoteItem
import uz.shoxrux.domain.repository.NoteRepository
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repository: NoteRepository
) : ViewModel() {

    private val _notes = MutableStateFlow<List<NoteItem>?>(null)
    val notes: StateFlow<List<NoteItem>?> = _notes

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error

    fun getAllNotes() {

        try {

            viewModelScope.launch {
                repository.getAllNotes().collect { result ->
                    _notes.value = result
                }
            }

        } catch (e: Exception) {
            _error.value = e.localizedMessage
        }

    }

    fun addNote(note: NoteItem) {
        try {

            viewModelScope.launch {
                repository.addNote(note)
            }

        } catch (e: Exception) {
            _error.value = e.localizedMessage
        }

    }

    fun deletedNoteById(id: Int) {

        try {

            viewModelScope.launch {
                repository.deleteNote(id)
            }

        } catch (e: Exception) {
            _error.value = e.localizedMessage
        }
    }
}