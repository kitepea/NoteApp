package com.floki.noteapp.Model

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.floki.noteapp.Database.NoteDatabase
import com.floki.noteapp.Database.NoteRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class NoteViewModel(application: Application) : AndroidViewModel(application) {
    private val noteRepository: NoteRepository
    private val allNotes: LiveData<List<Note>>

    init {
        val dao = NoteDatabase.getDatabase(application).getNoteDao()
        noteRepository = NoteRepository(dao)
        allNotes = noteRepository.allNotes
    }

    /*
    There are different Dispatchers. Dispatchers.IO is used for IO works like database or remote server. Dispatchers.Default is used for tasks that has high CPU usage. Dispatchers.Main is used for tasks that need to update UI.
    */
    fun deleteNode(note: Note) = viewModelScope.launch(Dispatchers.IO) {
        noteRepository.delete(note)
    }

    fun insertNote(note: Note) = viewModelScope.launch(Dispatchers.IO) {
        noteRepository.insert(note)
    }

    fun updateNote(note: Note) = viewModelScope.launch(Dispatchers.IO) {
        noteRepository.update(note)
    }
}
