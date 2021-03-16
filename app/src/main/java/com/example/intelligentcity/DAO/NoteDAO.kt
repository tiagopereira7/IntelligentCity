package com.example.intelligentcity.DAO

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.intelligentcity.entities.Note

@Dao
interface NoteDAO {

    @Query("SELECT * from note ORDER BY id ASC")
    fun getAllNotes(): LiveData<List<Note>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(note: Note)

    @Query("DELETE FROM note")
    suspend fun deleteAll()

    @Query("DELETE FROM note where id == :id")
    suspend fun deleteByCity(id: String)

    @Query("UPDATE note SET title= :title, text= :text WHERE id == :id")
    suspend fun updateById(title: String, text: String, id: Int)
}