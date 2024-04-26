package com.floki.noteapp.Database

import androidx.lifecycle.LiveData
import com.floki.noteapp.Model.Note

class NoteRepository(private val noteDao: NoteDao) {

    val allNotes: LiveData<List<Note>> = noteDao.getAllNotes()

    suspend fun insert(note: Note) {
        noteDao.insert(note)
    }

    suspend fun delete(note: Note) {
        noteDao.delete(note)
    }

    suspend fun update(note: Note) {
        noteDao.update(note.id, note.title, note.note, note.date)
    }

    fun getNotesByUserId(userId: String): LiveData<List<Note>> {
        return noteDao.getNotesByUserId(userId)
    }

}