package uz.shoxrux.presentation.screens.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import uz.shoxrux.domain.model.NoteItem
import uz.shoxrux.domain.repository.NoteRepository
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val repository: NoteRepository
) : ViewModel() {

    private val _isSuccess = MutableStateFlow(true)
    val isSuccess: MutableStateFlow<Boolean> = _isSuccess

    private val _error = MutableStateFlow<String?>(null)
    val error: MutableStateFlow<String?> = _error

    fun addNote(noteItem: NoteItem) {

        viewModelScope.launch {

            try {
                repository.addNote(noteItem)
                _isSuccess.value = true
            } catch (e: Exception) {
                _error.value = e.message
            }

        }

    }

}