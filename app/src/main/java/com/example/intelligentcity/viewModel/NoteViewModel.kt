package com.example.intelligentcity.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.intelligentcity.DAO.NoteDAO
import com.example.intelligentcity.db.NoteDB
import com.example.intelligentcity.db.NoteRepository
import com.example.intelligentcity.entities.Note
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class NoteViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: NoteRepository
    // Using LiveData and caching what getAlphabetizedWords returns has several benefits:
    // - We can put an observer on the data (instead of polling for changes) and only update the
    //   the UI when the data actually changes.
    // - Repository is completely separated from the UI through the ViewModel.
    val allNotes: LiveData<List<Note>>

    init {
        val notesDao = NoteDB.getDatabase(application, viewModelScope).noteDAO()
        repository = NoteRepository(notesDao)
        allNotes = repository.allNotes
    }

    /**
     * Launching a new coroutine to insert the data in a non-blocking way
     */
    fun insert(note: Note) = viewModelScope.launch(Dispatchers.IO) {
        repository.insert(note)
    }
    fun getNoteById(id: Int): LiveData<List<Note>> {
        return repository.getNoteById(id)
    }

    // delete all
    fun deleteAll() = viewModelScope.launch(Dispatchers.IO) {
        repository.deleteAll()
    }

    // delete by Id
    fun deleteById(id: Int) = viewModelScope.launch(Dispatchers.IO) {
        repository.deleteById(id)
    }

    fun updateById(title: String, text: String, id: Int) = viewModelScope.launch {
        repository.updateById(title, text, id)
    }
}