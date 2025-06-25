package uz.shoxrux.presentation.screens.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import uz.shoxrux.domain.model.NoteItem
import uz.shoxrux.domain.repository.NoteRepository
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repository: NoteRepository
) : ViewModel() {

    private val _searchText = MutableStateFlow("")
    val searchText: StateFlow<String> = _searchText

    private val _isSearching = MutableStateFlow(false)
    val isSearching: StateFlow<Boolean> = _isSearching

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error

    private val allNotes = MutableStateFlow<List<NoteItem>>(emptyList())

    private val _selectedNotes = MutableStateFlow<List<NoteItem>>(emptyList())
    val selectedNotes: StateFlow<List<NoteItem>> = _selectedNotes

    val notes: StateFlow<List<NoteItem>> = combine(
        allNotes,
        _searchText,
        _isSearching
    ) { list, query, searching ->
        if (!searching || query.isBlank()) list
        else list.filter { it.title.contains(query, ignoreCase = true) }
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    init {
        getAllNotes()
    }

    private fun getAllNotes() {
        viewModelScope.launch {
            try {
                repository.getAllNotes().collect {
                    allNotes.value = it
                }
            } catch (e: Exception) {
                _error.value = e.localizedMessage
            }
        }
    }

    fun deleteNotesById(ids: List<Int>) {
        viewModelScope.launch {
            try {
                repository.deleteNotes(ids)
            } catch (e: Exception) {
                _error.value = e.localizedMessage
            }
        }
    }

    fun onNewText(newText: String) {
        _searchText.value = newText
    }

    fun setSearching(active: Boolean) {
        _isSearching.value = active
    }

    fun toggleSearchState() {
        _isSearching.value = !_isSearching.value
    }

    fun clearSelected() {
        _selectedNotes.value = emptyList()
    }

    fun toggleNoteSelection(note: NoteItem) {
        _selectedNotes.value = if (_selectedNotes.value.any { it.id == note.id }) {
            _selectedNotes.value.filterNot { it.id == note.id }
        } else {
            _selectedNotes.value + note
        }
    }

    fun selectAll() {
        _selectedNotes.value = allNotes.value
    }

}
