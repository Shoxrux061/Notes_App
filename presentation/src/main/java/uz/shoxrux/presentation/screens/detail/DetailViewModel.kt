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

    private val _isSuccess = MutableStateFlow(false)
    val isSuccess: MutableStateFlow<Boolean> = _isSuccess

    private val _error = MutableStateFlow<String?>(null)
    val error: MutableStateFlow<String?> = _error

    private val _titleText = MutableStateFlow("")
    val titleText: MutableStateFlow<String> = _titleText

    private val _contentText = MutableStateFlow("")
    val contentText: MutableStateFlow<String> = _contentText

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

    fun editNote(noteItem: NoteItem) {
        viewModelScope.launch {
            try {
                repository.editNote(noteItem).collect { isSuccess ->
                    if (isSuccess) {
                        _isSuccess.value = true
                    } else {
                        _error.value = "Unknown error"
                    }
                }
            } catch (e: Exception) {
                _error.value = e.localizedMessage ?: "Unexpected error"
            }
        }
    }


    fun setArgument(id: Int) {
        viewModelScope.launch {

            try {
                repository.getNoteById(id).collect { result ->
                    _titleText.value = result.title
                    _contentText.value = result.content
                }

            } catch (e: Exception) {
                _error.value = e.localizedMessage
            }

        }
    }

    fun clearState() {
        _titleText.value = ""
        _contentText.value = ""
        _isSuccess.value = false
        _error.value = null
    }

    fun onContentNewText(newText: String) {
        _contentText.value = newText
    }

    fun onTitleNewText(newText: String) {
        _titleText.value = newText
    }

}