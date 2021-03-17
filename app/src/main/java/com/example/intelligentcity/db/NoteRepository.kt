package com.example.intelligentcity.db

import androidx.lifecycle.LiveData
import com.example.intelligentcity.DAO.NoteDAO
import com.example.intelligentcity.entities.Note

// Declares the DAO as a private property in the constructor. Pass in the DAO
// instead of the whole database, because you only need access to the DAO
class NoteRepository(private val noteDAO: NoteDAO) {

    // Room executes all queries on a separate thread.
    // Observed LiveData will notify the observer when the data has changed.
    val allNotes: LiveData<List<Note>> = noteDAO.getAllNotes()

    suspend fun insert(note: Note) {
        noteDAO.insert(note)
    }

    suspend fun deleteAll(){
        noteDAO.deleteAll()
    }

    suspend fun deleteById(id: Int){
        noteDAO.deleteById(id)
    }


    suspend fun updateById(title: String, text: String, id: Int){
        noteDAO.updateById(title, text, id)
    }
}